package org.sheepy.lily.core.cadence.common;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sheepy.lily.core.action.ActionDispatcherThread;
import org.sheepy.lily.core.adapter.IBasicAdapterFactory;
import org.sheepy.lily.core.adapter.ITickDescriptor;
import org.sheepy.lily.core.api.action.context.ActionExecutionContext;
import org.sheepy.lily.core.api.adapter.IAdapterFactoryService;
import org.sheepy.lily.core.api.cadence.ICadencer;
import org.sheepy.lily.core.api.cadence.IMainLoop;
import org.sheepy.lily.core.api.cadence.IStatistics;
import org.sheepy.lily.core.api.cadence.ITicker;
import org.sheepy.lily.core.api.engine.IEngineAdapter;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.service.ServiceManager;
import org.sheepy.lily.core.cadence.execution.CommandStack;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.core.model.application.IEngine;

public class Cadencer implements ICadencer
{
	private static final String CADENCER_TICK = "Cadencer Tick";
	private static final String MODEL_COMMANDS = "Model Commands";

	protected Application application;
	protected List<AbstractTickerWrapper> tickers = new ArrayList<>();

	protected final CommandStack commandStack = new CommandStack();
	protected final ECrossReferenceAdapter crossReferencer = new ECrossReferenceAdapter();

	protected final IBasicAdapterFactory adapterFactory = (IBasicAdapterFactory) IBasicAdapterFactory.INSTANCE;
	protected final IMainLoop mainloop;

	protected IInputManager inputManager = null;
	private Deque<AbstractTickerWrapper> course = new ArrayDeque<>();
	private Deque<AbstractTickerWrapper> nextCourse = new ArrayDeque<>();
	private Long mainThread = null;

	private final AtomicBoolean stop = new AtomicBoolean(false);

	private final IStatistics statistics = ServiceManager.getService(IStatistics.class);

	private ActionDispatcherThread dispatcher;
	private CadenceContentAdater cadenceContentAdapter;

	public Cadencer(Application application, IMainLoop mainloop)
	{
		this.application = application;
		this.mainloop = mainloop;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void deleteObject(EObject eo)
	{
		for (final Setting seting : crossReferencer.getInverseReferences(eo))
		{
			final EObject referencer = seting.getEObject();
			final EStructuralFeature feature = seting.getEStructuralFeature();

			if (feature.isMany())
			{
				final List<EObject> list = (List<EObject>) referencer.eGet(feature);
				list.remove(eo);
			}
			else
			{
				referencer.eSet(feature, null);
			}
		}

		EcoreUtil.remove(eo);
	}

	public void load()
	{
		stop.set(false);
		mainThread = Thread.currentThread().getId();

		dispatcher = new ActionDispatcherThread(commandStack, mainThread);
		addTicker(dispatcher, -1);

		IAdapterFactoryService.INSTANCE.setupRoot(application);

		for (final IEngine engine : application.getEngines())
		{
			inputManager = IEngineAdapter.adapt(engine).getInputManager();
			if (inputManager != null)
			{
				break;
			}
		}

		cadenceContentAdapter = new CadenceContentAdater();
		application.eAdapters().add(cadenceContentAdapter);

		if (mainloop != null)
		{
			mainloop.load(application);
		}
	}

	private void dispose()
	{
		if (mainloop != null)
		{
			mainloop.free(application);
		}

		application.eAdapters().remove(cadenceContentAdapter);
		cadenceContentAdapter = null;

		removeTicker(dispatcher, -1);

		statistics.printTimes();
		statistics.clear();
		// getCommandStack().printStats();

		mainThread = null;

		IAdapterFactoryService.INSTANCE.uninstallRoot(application);
	}

	public void run()
	{
		while (stop.get() == false)
		{
			if (inputManager != null)
			{
				inputManager.pollInputs();
			}

			tick(application.getCadenceInHz());

			if (mainloop != null)
			{
				mainloop.step(application);
			}
		}

		dispose();
	}

	public void stop()
	{
		stop.set(true);
	}

	public void tick(long stepNano)
	{
		final long duration = System.nanoTime();

		// =========
		// Compute tickers to execute
		// =========
		for (int i = 0; i < tickers.size(); i++)
		{
			final AbstractTickerWrapper ticker = tickers.get(i);
			ticker.accumulate(stepNano);

			if (ticker.shouldTick())
			{
				nextCourse.add(ticker);
			}
		}

		long blockingDuration = System.nanoTime();
		commandStack.execute();
		blockingDuration = System.nanoTime() - blockingDuration;

		statistics.addTime(MODEL_COMMANDS, blockingDuration);

		// =========
		// Execute tickers
		// =========
		while (nextCourse.isEmpty() == false)
		{
			final Deque<AbstractTickerWrapper> temp = course;
			course = nextCourse;
			nextCourse = temp;

			while (course.isEmpty() == false)
			{
				final AbstractTickerWrapper ticker = course.poll();

				try
				{
					if (ticker.stop.get())
					{
						tickers.remove(ticker);
					}
					else
					{
						final long d = System.nanoTime();

						ticker.tick();

						statistics.addTime(ticker.getLabel(), System.nanoTime() - d);
					}

				} catch (final Exception e)
				{
					e.printStackTrace();
				}

				if (ticker.shouldTick())
				{
					nextCourse.add(ticker);
				}
			}

			// =========
			// Execute Models Commands
			// =========

			blockingDuration = System.nanoTime();
			commandStack.execute();
			blockingDuration = System.nanoTime() - blockingDuration;

			statistics.addTime(MODEL_COMMANDS, blockingDuration);
		}

		statistics.addTime(CADENCER_TICK, System.nanoTime() - duration);
	}

	@Override
	public void postAction(ActionExecutionContext ec)
	{
		if (dispatcher == null)
		{
			new AssertionError("No action dispatcher. Is cadencer running?");
		}

		dispatcher.postAction(ec);
	}

	@Override
	public void addTicker(ITicker ticker, int frequency)
	{
		final TickerWrapper tw = new TickerWrapper(ticker, frequency);

		tickers.add(tw);
	}

	@Override
	public void removeTicker(ITicker ticker, int frequency)
	{
		for (final AbstractTickerWrapper tickerWrapper : tickers)
		{
			if (tickerWrapper.getFrequency() == frequency && tickerWrapper.getTicker() == ticker)
			{
				tickerWrapper.stop.set(true);
			}
		}
	}

	@Override
	public CommandStack getCommandStack()
	{
		return commandStack;
	}

	@Override
	public long getThreadId()
	{
		return mainThread;
	}

	@Override
	public IInputManager getInputManager()
	{
		return inputManager;
	}

	private class CadenceContentAdater extends EContentAdapter
	{
		@Override
		protected void setTarget(EObject target)
		{
			final var tickDescriptors = adapterFactory.getTickDescriptors(target);

			for (final ITickDescriptor ticker : tickDescriptors)
			{
				final var wrapper = new AdapterTickerWrapper(ticker.getFrequency(), ticker);
				tickers.add(wrapper);
			}

			super.setTarget(target);
		}

		@Override
		protected void unsetTarget(EObject target)
		{
			final var tickDescriptors = adapterFactory.getTickDescriptors(target);

			final var it = tickers.iterator();
			while (it.hasNext())
			{
				final var next = it.next();
				if (tickDescriptors.contains(next.getTicker()))
				{
					next.stop.set(true);
				}
			}

			super.unsetTarget(target);
		}
	};

}

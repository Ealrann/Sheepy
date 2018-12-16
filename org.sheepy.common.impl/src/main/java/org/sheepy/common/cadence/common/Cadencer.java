package org.sheepy.common.cadence.common;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sheepy.common.action.ActionDispatcherThread;
import org.sheepy.common.api.action.context.ExecutionContext;
import org.sheepy.common.api.adapter.IServiceAdapterFactory;
import org.sheepy.common.api.cadence.ICadencer;
import org.sheepy.common.api.cadence.IMainLoop;
import org.sheepy.common.api.cadence.IStatistics;
import org.sheepy.common.api.cadence.ITicker;
import org.sheepy.common.api.input.IInputManager;
import org.sheepy.common.api.resource.IModelExtension;
import org.sheepy.common.api.service.ServiceManager;
import org.sheepy.common.cadence.execution.CommandStack;
import org.sheepy.common.model.application.Application;

public class Cadencer implements ICadencer
{
	protected Application application;
	protected List<TickerWrapper> tickers = new ArrayList<>();

	protected final CommandStack commandStack = new CommandStack();
	protected final ECrossReferenceAdapter crossReferencer = new ECrossReferenceAdapter();

	protected final IServiceAdapterFactory adapterFactory = IServiceAdapterFactory.INSTANCE;
	protected final IInputManager inputManager = IInputManager.INSTANCE;
	protected final IMainLoop mainloop = IMainLoop.INSTANCE;

	private Deque<AbstractCadencedWrapper> course = new ArrayDeque<>();
	private Deque<AbstractCadencedWrapper> nextCourse = new ArrayDeque<>();
	private Long mainThread = null;

	private final AtomicBoolean stop = new AtomicBoolean(false);

	private final IStatistics statistics = ServiceManager.getService(IStatistics.class);

	private ActionDispatcherThread dispatcher;

	public Cadencer(Application application)
	{
		this.application = application;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void deleteObject(EObject eo)
	{
		for (Setting seting : crossReferencer.getInverseReferences(eo))
		{
			EObject referencer = seting.getEObject();
			EStructuralFeature feature = seting.getEStructuralFeature();

			if (feature.isMany())
			{
				List<EObject> list = (List<EObject>) referencer.eGet(feature);
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

		loadEPackages();
		IServiceAdapterFactory.INSTANCE.setupAutoAdapters(application);

		if (mainloop != null)
		{
			mainloop.load(application);
		}
	}
	
	private void dispose()
	{
		removeTicker(dispatcher, -1);

		statistics.printTickersTime();
		statistics.clear();
		getCommandStack().printStats();

		mainThread = null;

		if (mainloop != null)
		{
			mainloop.dispose(application);
		}

		IServiceAdapterFactory.INSTANCE.disposeAutoAdapters(application);
	}

	public void start()
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
				if (mainloop.shouldClose())
				{
					stop.set(true);
				}
			}
		}

		dispose();
	}

	private static void loadEPackages()
	{
		for (IModelExtension extension : IModelExtension.EXTENSIONS)
		{
			for (EPackage ePackage : extension.getEPackages())
			{
				// Load factories
				ePackage.eClass();
				System.out.println("\tLoad EPackage: " + ePackage.getName());
			}
		}
	}

	public void stop()
	{
		stop.set(true);
	}

	public void tick(long stepNano)
	{
		long duration = System.nanoTime();

		// =========
		// Compute tickers to execute
		// =========
		for (int i = 0; i < tickers.size(); i++)
		{
			AbstractCadencedWrapper ticker = tickers.get(i);
			ticker.accumulate(stepNano);

			if (ticker.shouldTick())
			{
				nextCourse.add(ticker);
			}
		}

		// =========
		// Compute processors to execute
		// =========

		// for (int i = 0; i < cadencedProcessors.size(); i++)
		// {
		// ProcessorTickerWrapper wrapper = cadencedProcessors.get(i);
		// wrapper.accumulate(stepNano);
		//
		// if (wrapper.shouldTick())
		// {
		// nextCourse.add(wrapper);
		// }
		// }

		long blockingDuration = System.nanoTime();
		commandStack.execute();
		blockingDuration = System.nanoTime() - blockingDuration;

		statistics.addAccumulatedDuration("Model Commands", blockingDuration);

		// =========
		// Execute tickers
		// =========
		while (nextCourse.isEmpty() == false)
		{
			Deque<AbstractCadencedWrapper> temp = course;
			course = nextCourse;
			nextCourse = temp;

			while (course.isEmpty() == false)
			{
				AbstractCadencedWrapper ticker = course.poll();

				try
				{
					if (ticker.stop.get())
					{
						tickers.remove(ticker);
					}
					else
					{
						long d = System.nanoTime();

						ticker.tick();

						statistics.addTickerTime(ticker.getLabel(), System.nanoTime() - d);
					}

				} catch (Exception e)
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

			statistics.addAccumulatedDuration("Model Commands", blockingDuration);
		}

		statistics.addAccumulatedDuration("Cadencer Tick", System.nanoTime() - duration);
		statistics.update();
	}

	@Override
	public void postAction(ExecutionContext ec)
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
		TickerWrapper tw = new TickerWrapper(ticker, frequency);

		tickers.add(tw);
	}

	@Override
	public void removeTicker(ITicker ticker, int frequency)
	{
		for (TickerWrapper tickerWrapper : tickers)
		{
			if (tickerWrapper.getFrequency() == frequency && tickerWrapper.ticker == ticker)
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
}

package org.sheepy.lily.core.allocation;

import org.sheepy.lily.core.allocation.children.AllocationChildrenManager;
import org.sheepy.lily.core.allocation.description.AllocationDescriptor;
import org.sheepy.lily.core.allocation.util.AllocationManager;
import org.sheepy.lily.core.api.allocation.IAllocationContext;
import org.sheepy.lily.core.api.allocation.IAllocationHandle;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.model.ILilyEObject;
import org.sheepy.lily.core.api.notification.IEMFNotifier;
import org.sheepy.lily.core.api.notification.observatory.IObservatory;
import org.sheepy.lily.core.api.notification.observatory.IObservatoryBuilder;
import org.sheepy.lily.core.api.notification.util.ListenerList;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public final class AllocationHandle<Allocation extends IExtender> implements IAllocationHandle<Allocation>
{
	private final ILilyEObject target;
	private final AllocationDescriptor<Allocation> descriptor;
	private final ListenerList<ExtenderListener<Allocation>> listeners = new ListenerList<>();
	private final ListenerList<Runnable> dirtyListeners = new ListenerList<>();
	private final IObservatory observatory;
	private final AllocationChildrenManager preChildrenManager;
	private final AllocationChildrenManager postChildrenManager;
	private final AllocationManager<Allocation> allocationManager;

	private boolean allocated = false;
	private boolean dirty = true;

	public AllocationHandle(ILilyEObject target, AllocationDescriptor<Allocation> descriptor)
	{
		final var observatoryBuilder = IObservatoryBuilder.newObservatoryBuilder();
		this.target = target;
		this.descriptor = descriptor;
		final var resolvers = descriptor.createResolvers(observatoryBuilder, target);
		final var childrenManagerBuilder = new AllocationChildrenManager.Builder(descriptor.getChildrenAnnotations(),
																				 target);
		postChildrenManager = childrenManagerBuilder.build(observatoryBuilder, this::setDirty, false);
		preChildrenManager = childrenManagerBuilder.build(observatoryBuilder, this::setDirty, true);
		final var instanceBuilder = new AllocationInstance.Builder<>(descriptor,
																	 target,
																	 resolvers,
																	 postChildrenManager);
		allocationManager = new AllocationManager<>(target, instanceBuilder, this::setDirty, this::onAllocationChange);
		this.observatory = observatoryBuilder.build();
	}

	@Override
	public void load(final ILilyEObject target, final IEMFNotifier notifier)
	{
		observatory.observe(target);
	}

	@Override
	public void dispose(final ILilyEObject target, final IEMFNotifier notifier)
	{
		observatory.shut(target);
	}

	public void cleanup(final IAllocationContext context, boolean freeEverything)
	{
		if (allocated)
		{
			if (freeEverything || postChildrenManager.isDirty())
			{
				final var childContext = descriptor.isProvidingContext() ? allocationManager.getProvidedContext() : context;
				postChildrenManager.cleanup(target, childContext, freeEverything);
			}
			allocationManager.cleanup(context, freeEverything);
			if (freeEverything || postChildrenManager.isDirty())
			{
				preChildrenManager.cleanup(target, context, freeEverything);
			}
			if (freeEverything) allocated = false;
		}
	}

	public void ensureAllocation(final IAllocationContext context)
	{
		if (preChildrenManager.isDirty())
		{
			preChildrenManager.update(target, context);
		}
		allocationManager.update(context);
		if (postChildrenManager.isDirty())
		{
			final var providedContext = descriptor.isProvidingContext() ? allocationManager.getProvidedContext() : null;
			if (providedContext != null)
			{
				providedContext.beforeChildrenAllocation();
				postChildrenManager.update(target, providedContext);
				providedContext.afterChildrenAllocation();
			}
			else
			{
				postChildrenManager.update(target, context);
			}
			allocationManager.injectChildren();
		}
		dirty = false;
		allocated = true;
	}

	@Override
	public <A extends Annotation> Stream<AnnotatedHandle<A>> allAnnotatedHandles(final Class<A> annotationClass)
	{
		return allocationManager.allAnnotatedHandles(annotationClass);
	}

	private void setDirty()
	{
		if (dirty == false)
		{
			dirty = true;
			dirtyListeners.notify(Runnable::run);
		}
	}

	@Override
	public Class<Allocation> getExtenderClass()
	{
		return descriptor.getExtenderDescriptor().extenderClass();
	}

	@Override
	public Allocation getExtender()
	{
		return allocationManager.getExtender();
	}

	private void onAllocationChange(Allocation oldAllocation, Allocation newAllocation)
	{
		listeners.notify(listener -> listener.accept(oldAllocation, newAllocation));
	}

	public AllocationInstance<Allocation> getMainAllocation()
	{
		return allocationManager.getMainAllocation();
	}

	@Override
	public void listen(final ExtenderListener<Allocation> listener)
	{
		listeners.listen(listener);
	}

	@Override
	public void listenNoParam(final Runnable listener)
	{
		listeners.listenNoParam(listener);
	}

	@Override
	public void sulk(final ExtenderListener<Allocation> listener)
	{
		listeners.sulk(listener);
	}

	@Override
	public void sulkNoParam(final Runnable listener)
	{
		listeners.sulkNoParam(listener);
	}

	public void listenDirty(final Runnable listener)
	{
		dirtyListeners.listen(listener);
	}

	public void sulkDirty(final Runnable listener)
	{
		dirtyListeners.sulk(listener);
	}
}

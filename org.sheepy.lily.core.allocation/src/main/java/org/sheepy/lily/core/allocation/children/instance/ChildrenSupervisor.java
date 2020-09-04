package org.sheepy.lily.core.allocation.children.instance;

import org.sheepy.lily.core.allocation.AllocationHandle;
import org.sheepy.lily.core.allocation.children.manager.ChildrenInjector;
import org.sheepy.lily.core.allocation.description.AllocationDescriptor;
import org.sheepy.lily.core.allocation.util.StructureObserver;
import org.sheepy.lily.core.api.model.ILilyEObject;
import org.sheepy.lily.core.api.notification.observatory.IObservatoryBuilder;
import org.sheepy.lily.core.api.util.IModelExplorer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ChildrenSupervisor
{
	private final Runnable whenBranchDirty;
	private final List<ChildDescriptorAllocator> childrenAllocators = new ArrayList<>();
	private final IModelExplorer modelExplorer;
	private final List<ChildrenInjector> injectors;

	private boolean addedElements = true;

	private ChildrenSupervisor(final IModelExplorer modelExplorer,
							   final Runnable whenBranchDirty,
							   final List<ChildrenInjector> injectors)
	{
		this.modelExplorer = modelExplorer;
		this.injectors = List.copyOf(injectors);
		this.whenBranchDirty = whenBranchDirty;
	}

	public void update(final ILilyEObject source)
	{
		if (addedElements)
		{
			addedElements = false;

			final var children = modelExplorer.stream(source).collect(Collectors.toUnmodifiableList());
			for (final var childrenAllocator : childrenAllocators)
			{
				childrenAllocator.reload(children);
			}
		}
	}

	private void add(List<ILilyEObject> children)
	{
		reloadDescriptors(children);
		if (addedElements == false)
		{
			whenBranchDirty.run();
			addedElements = true;
		}
	}

	private void remove(List<ILilyEObject> removedChildren)
	{
		for (final var childrenAllocator : childrenAllocators)
		{
			childrenAllocator.removeChildren(removedChildren);
		}
		whenBranchDirty.run();
	}

	private void reloadDescriptors(final List<ILilyEObject> children)
	{
		children.stream()
				.flatMap(c -> c.extenders().adaptHandlesOfType(AllocationHandle.class))
				.map(handle -> (AllocationHandle<?>) handle)
				.map(AllocationHandle::getDescriptor)
				.distinct()
				.filter(descriptor -> childrenAllocators.stream().noneMatch(a -> a.descriptor() == descriptor))
				.map(this::buildAllocator)
				.collect(Collectors.toCollection(() -> childrenAllocators));
	}

	private ChildDescriptorAllocator buildAllocator(final AllocationDescriptor<?> descriptor)
	{
		final var filteredInjectors = injectors.stream()
											   .filter(i -> i.match(descriptor))
											   .collect(Collectors.toUnmodifiableList());
		return new ChildDescriptorAllocator(descriptor, filteredInjectors, whenBranchDirty);
	}

	public List<ChildDescriptorAllocator> getDescriptorAllocators()
	{
		return childrenAllocators;
	}

	public static record Builder(StructureObserver structureObservatory, List<ChildrenInjector> childrenInjectors)
	{
		public ChildrenSupervisor build(final Runnable whenBranchDirty,
										final IObservatoryBuilder observatoryBuilder,
										final ILilyEObject target)
		{

			final var childrenSupervisor = new ChildrenSupervisor(structureObservatory.getExplorer(),
																  whenBranchDirty,
																  childrenInjectors);
			structureObservatory.installGatherer(target,
												 observatoryBuilder,
												 childrenSupervisor::add,
												 childrenSupervisor::remove);

			return childrenSupervisor;
		}
	}
}
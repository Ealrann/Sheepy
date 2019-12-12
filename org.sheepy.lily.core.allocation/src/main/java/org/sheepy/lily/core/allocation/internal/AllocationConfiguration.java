package org.sheepy.lily.core.allocation.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.sheepy.lily.core.allocation.AllocationManager;
import org.sheepy.lily.core.allocation.EAllocationStatus;
import org.sheepy.lily.core.api.allocation.IAllocationContext;

public final class AllocationConfiguration<T extends IAllocationContext>
{
	public final List<AllocationManager<?>> children = new ArrayList<>();
	public final List<AllocationManager<?>> dependencies = new ArrayList<>();
	public final T context;

	private IAllocationContext childrenContext = null;
	private Predicate<IAllocationContext> allocationCondition;

	public AllocationConfiguration(T context)
	{
		this.context = context;
	}

	public boolean isAllocable()
	{
		if (allocationCondition != null)
		{
			return allocationCondition.test(context);
		}
		else
		{
			return true;
		}
	}

	public boolean areDependenciesAllocated()
	{
		boolean res = true;
		for (int i = 0; i < dependencies.size(); i++)
		{
			final var dep = dependencies.get(i);
			if (dep.getStatus() != EAllocationStatus.Allocated)
			{
				res &= false;
				break;
			}
		}
		return res;
	}

	public void allocateChildren()
	{
		for (int i = 0; i < children.size(); i++)
		{
			final var allocationManager = children.get(i);
			allocationManager.allocate();
		}
	}

	public void freeChildren(boolean dirtyOnly)
	{
		for (int i = children.size() - 1; i >= 0; i--)
		{
			final var allocationManager = children.get(i);
			allocationManager.free(dirtyOnly);
		}
	}

	public IAllocationContext childContext()
	{
		final IAllocationContext childContext = childrenContext != null ? childrenContext : context;
		return childContext;
	}

	public IAllocationContext getChildrenContext()
	{
		return childrenContext;
	}

	public void setChildrenContext(IAllocationContext childrenContext)
	{
		this.childrenContext = childrenContext;
	}

	public void setAllocationCondition(Predicate<IAllocationContext> allocationCondition)
	{
		this.allocationCondition = allocationCondition;
	}

	public void appendInfo(StringBuilder appendTo, int depth)
	{
		if (dependencies.isEmpty() == false)
		{
			appendTo.append("Dependencies: ");
			for (final var dep : dependencies)
			{
				appendTo.append(dep.allocable.getClass().getSimpleName());
			}
		}

		for (final var child : children)
		{
			child.appendInfo(appendTo, depth + 1);
		}
	}
}
package org.sheepy.lily.core.allocation.operation;

import org.sheepy.lily.core.allocation.instance.AllocationInstance;
import org.sheepy.lily.core.allocation.spliterator.CleanupTreeIterator;
import org.sheepy.lily.core.api.allocation.IAllocationContext;
import org.sheepy.lily.core.api.model.ILilyEObject;

import java.util.Optional;

public final class FreeOperation implements IOperation<CleanupTreeIterator>
{
	private final ILilyEObject target;
	private final AllocationInstance<?> allocation;

	public FreeOperation(final ILilyEObject target, final AllocationInstance<?> allocation)
	{
		this.allocation = allocation;
		this.target = target;
	}

	@Override
	public void loadPreChildrenIterator(final CleanupTreeIterator iterator)
	{
		iterator.load(allocation.getPreChildrenManager(), true);
	}

	@Override
	public void operate(final IAllocationContext context)
	{
		allocation.free(target, context);
	}

	@Override
	public void loadPostChildrenIterator(final CleanupTreeIterator iterator)
	{
		iterator.load(allocation.getPostChildrenManager(), true);
	}

	@Override
	public Optional<IAllocationContext> providedContext()
	{
		return allocation.getPostChildrenManager().getProvidedContext();
	}
}
package org.sheepy.lily.core.allocation.parameter;

import org.sheepy.lily.core.allocation.instance.AllocationState;
import org.sheepy.lily.core.api.allocation.IAllocationState;
import org.sheepy.lily.core.api.extender.parameter.IParameterResolver;
import org.sheepy.lily.core.api.model.ILilyEObject;

import java.lang.annotation.Annotation;

public final class StateParameterBuilder implements IParameterResolver
{
	private final AllocationState state;

	public StateParameterBuilder(AllocationState state)
	{
		this.state = state;
	}

	@Override
	public boolean isApplicable(final Class<?> parameterClass, final Annotation parameterAnnotation)
	{
		return parameterClass == IAllocationState.class;
	}

	@Override
	public Object resolve(ILilyEObject target, Class<?> paramClass)
	{
		return state;
	}
}
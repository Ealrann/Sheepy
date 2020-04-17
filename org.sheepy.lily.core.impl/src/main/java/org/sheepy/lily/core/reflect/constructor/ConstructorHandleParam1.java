package org.sheepy.lily.core.reflect.constructor;

import org.sheepy.lily.core.reflect.util.ReflectionUtil;
import org.sheepy.lily.core.api.reflect.ConstructorHandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.util.function.Function;

public final class ConstructorHandleParam1<T> implements ConstructorHandle<T>
{
	private final Function<Object, T> function;
	private final Constructor<T> constructor;

	private ConstructorHandleParam1(Function<Object, T> function, Constructor<T> constructor)
	{
		this.function = function;
		this.constructor = constructor;
	}

	@Override
	public T newInstance(Object... parameters)
	{
		return function.apply(parameters[0]);
	}

	@Override
	public Constructor<T> getJavaConstructor()
	{
		return constructor;
	}

	public static final class Builder<T> extends ConstructorHandleBuilder<T>
	{
		private final ConstructorHandle<T> handle;

		public Builder(Lookup lookup, MethodHandle methodHandle, Constructor<T> constructor) throws Throwable
		{
			final Function<Object, T> function = ReflectionUtil.createFunction(lookup, methodHandle);
			handle = new ConstructorHandleParam1<>(function, constructor);
		}

		@Override
		public ConstructorHandle<T> build()
		{
			return handle;
		}
	}
}
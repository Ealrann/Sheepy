package org.sheepy.lily.core.reflect.constructor;

import org.sheepy.lily.core.api.reflect.ConstructorHandle;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;

public final class ConstructorHandleParamN<T> implements ConstructorHandle<T>
{
	private final MethodHandle methodHandle;
	private final Constructor<T> constructor;

	private ConstructorHandleParamN(MethodHandle methodHandle, Constructor<T> constructor)
	{
		this.methodHandle = methodHandle;
		this.constructor = constructor;
	}

	@Override
	public T newInstance(Object... parameters)
	{
		try
		{
			@SuppressWarnings("unchecked") final T res = (T) methodHandle.invokeWithArguments(parameters);
			return res;
		}
		catch (Throwable throwable)
		{
			throwable.printStackTrace();
			return null;
		}
	}

	@Override
	public Constructor<T> getJavaConstructor()
	{
		return constructor;
	}

	public static final class Builder<T> extends ConstructorHandleBuilder<T>
	{
		private final ConstructorHandle<T> handle;

		public Builder(MethodHandle methodHandle, Constructor<T> constructor)
		{
			handle = new ConstructorHandleParamN<>(methodHandle, constructor);
		}

		@Override
		public ConstructorHandle<T> build()
		{
			return handle;
		}
	}
}

package org.sheepy.lily.core.reflect.supplier;

import org.sheepy.lily.core.api.reflect.SupplierHandle;

import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.function.BooleanSupplier;

public final class BooleanSupplierHandle implements SupplierHandle
{
	private final BooleanSupplier supplier;

	private BooleanSupplierHandle(BooleanSupplier supplier)
	{
		this.supplier = supplier;
	}

	@Override
	public Object invoke()
	{
		return supplier.getAsBoolean();
	}

	@Override
	public Object getLambdaFunction()
	{
		return supplier;
	}

	public static final class Builder extends SupplierHandleBuilder
	{
		private final MethodHandle factory;

		public Builder(Lookup lookup, MethodHandle methodHandle, Class<?> type) throws LambdaConversionException
		{
			final var factoryType = MethodType.methodType(BooleanSupplier.class, type);
			final var targetType = methodHandle.type().dropParameterTypes(0, 1);
			final var site = LambdaMetafactory.metafactory(lookup,
														   "getAsBoolean",
														   factoryType,
														   MethodType.methodType(Boolean.TYPE),
														   methodHandle,
														   targetType);
			factory = site.getTarget();
		}

		@Override
		public BooleanSupplierHandle build(Object target)
		{
			try
			{
				final var supplier = (BooleanSupplier) factory.invoke(target);
				return new BooleanSupplierHandle(supplier);

			}
			catch (final Throwable e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}

	public static final class StaticBuilder extends SupplierHandleBuilder
	{
		private final BooleanSupplierHandle handle;

		public StaticBuilder(Lookup lookup, MethodHandle methodHandle) throws Throwable
		{
			final var site = LambdaMetafactory.metafactory(lookup,
														   "getAsBoolean",
														   MethodType.methodType(BooleanSupplier.class),
														   MethodType.methodType(Boolean.TYPE),
														   methodHandle,
														   methodHandle.type());

			final var supplier = (BooleanSupplier) site.getTarget().invoke();
			handle = new BooleanSupplierHandle(supplier);
		}

		@Override
		public BooleanSupplierHandle build(Object target)
		{
			return handle;
		}
	}
}

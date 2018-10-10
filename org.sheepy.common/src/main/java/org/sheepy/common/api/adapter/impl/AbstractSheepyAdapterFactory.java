package org.sheepy.common.api.adapter.impl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.sheepy.common.api.adapter.ISheepyAdapter;
import org.sheepy.common.api.adapter.ISheepyAdapterFactory;
import org.sheepy.common.api.adapter.ISheepyAdapterRegistry;
import org.sheepy.common.api.adapter.ISheepyAdapterWrapper;

/**
 * Extensible Mapped AdapterFactory.
 * 
 * @author Ealrann
 *
 */
public abstract class AbstractSheepyAdapterFactory implements ISheepyAdapterFactory
{

	private ISheepyAdapterRegistry registry = null;

	private void load()
	{
		if (registry == null)
		{
			registry = getRegistry();
		}
	}

	public boolean isFactoryForType(Object object)
	{
		load();
		for (ISheepyAdapterWrapper extension : registry.getRegisteredAdapters())
		{
			if (extension.isAdapterForType(object))
			{
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Adapter adapt(Notifier target, Object type)
	{
		return adapt((EObject) target, (Class<ISheepyAdapter>) type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ISheepyAdapter> T adapt(EObject target, Class<T> type)
	{
		load();
		ISheepyAdapterWrapper extension = registry.getAdapterFor(target, type);

		if (extension == null)
		{
			logUnregisteredAdapter(target, type);
		}

		T res = (T) extension.adapt(target, this);

		if (res == null)
		{
			logInvalidExtension(extension, type);
		}

		return res;
	}

	private void logUnregisteredAdapter(EObject target, Class<?> type)
	{
		if (type instanceof Class) System.err.println("Error : Can't adapt "
				+ ((EObject) target).eClass().getName()
				+ " to "
				+ ((Class<?>) type).getSimpleName());
		else System.err.println("Error : Can't adapt "
				+ ((EObject) target).eClass().getName()
				+ " to "
				+ type.getClass().getSimpleName());
	}

	private void logInvalidExtension(ISheepyAdapterWrapper extension, Class<?> type)
	{
		System.err.println("The adapter extension ["
				+ extension.getClass().getSimpleName()
				+ "] is invalid: returned NULL adapter for type ["
				+ type.getSimpleName()
				+ "].");
	}

	protected abstract ISheepyAdapterRegistry getRegistry();
}

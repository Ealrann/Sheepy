package org.sheepy.lily.core.adapter.impl;

import org.eclipse.emf.ecore.EObject;
import org.sheepy.lily.core.adapter.ITickDescriptor;
import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.adapter.IAdapterManager;
import org.sheepy.lily.core.api.adapter.LilyEObject;
import org.sheepy.lily.core.api.notification.INotificationListener;

import java.util.ArrayList;
import java.util.List;

public final class AdapterManager implements IAdapterManager
{
	public final List<ITickDescriptor> tickers = new ArrayList<>();

	private final List<AdapterHandle<?>> adapterHandles = new ArrayList<>();
	private final AdapterManagerDeployer deployer;

	private boolean loaded = false;

	public AdapterManager(EObject target)
	{
		deployer = new AdapterManagerDeployer(target);
		deployer.buildAutoAdapters(adapterHandles);
		deployer.foreachChild(LilyEObject::setupAdapterManager);
	}

	@Override
	public void addListener(INotificationListener listener, int... features)
	{
		deployer.addListener(listener, features);
	}

	@Override
	public void removeListener(INotificationListener listener, int... features)
	{
		deployer.removeListener(listener, features);
	}

	@Override
	public <T extends IAdapter> T adapt(Class<T> type)
	{
		final T res = findAdapter(type);

		if (res != null)
		{
			return res;
		}
		else
		{
			return createAdapter(type);
		}
	}

	private <T extends IAdapter> T findAdapter(Class<T> type)
	{
		for (int i = 0; i < adapterHandles.size(); i++)
		{
			final var adapterHandle = adapterHandles.get(i);
			if (adapterHandle.domain.isAdapterForType(type))
			{
				return type.cast(adapterHandle.adapter);
			}
		}
		return null;
	}

	private <T extends IAdapter> T createAdapter(Class<T> type)
	{
		final var handle = deployer.createAdapter(type);
		if (handle != null)
		{
			adapterHandles.add(handle);
			if (loaded)
			{
				loadHandles(handle);
			}
			return handle.adapter;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void load()
	{
		if (loaded == false)
		{
			loaded = true;
			deployer.setAutoLoad(true);
			for (int i = 0; i < adapterHandles.size(); i++)
			{
				final var adapterHandle = adapterHandles.get(i);
				loadHandles(adapterHandle);
			}
		}

		deployer.foreachChild(LilyEObject::loadAdapterManager);
	}

	@Override
	public void dispose()
	{
		if (loaded)
		{
			deployer.foreachChild(LilyEObject::disposeAdapterManager);
		}

		loaded = false;
		deployer.setAutoLoad(false);

		for (int i = 0; i < adapterHandles.size(); i++)
		{
			final var adapterHandle = adapterHandles.get(i);
			adapterHandle.dispose();
		}
	}

	public <T extends IAdapter> void loadHandles(final AdapterHandle<T> adapterHandle)
	{
		addHandleListener(adapterHandle);
		adapterHandle.load();
		tickers.addAll(adapterHandle.tickHandles);
	}

	private void addHandleListener(AdapterHandle<?> adapterHandle)
	{
		final var notifyHandles = adapterHandle.notifyHandles;
		for (int i = 0; i < notifyHandles.size(); i++)
		{
			final var notifyHandle = notifyHandles.get(i);
			final var featureIds = notifyHandle.featureIds;

			addListener(notifyHandle, featureIds);
		}
	}
}

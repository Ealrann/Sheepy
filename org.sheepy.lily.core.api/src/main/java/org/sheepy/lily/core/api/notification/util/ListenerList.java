package org.sheepy.lily.core.api.notification.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class ListenerList<Type>
{
	private final List<Object> listeners = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public void notify(final Consumer<Type> listenerExecution)
	{
		for (int i = 0; i < listeners.size(); i++)
		{
			final var listener = listeners.get(i);
			if (listener instanceof Runnable)
			{
				((Runnable) listener).run();
			}
			else
			{
				listenerExecution.accept((Type) listener);
			}
		}
	}

	public void listen(Type listener)
	{
		listeners.add(listener);
	}

	public void listenNoParam(Runnable listener)
	{
		listeners.add(listener);
	}

	public void sulk(Type listener)
	{
		listeners.remove(listener);
	}

	public void sulkNoParam(Runnable listener)
	{
		listeners.remove(listener);
	}
}
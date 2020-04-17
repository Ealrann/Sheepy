package org.sheepy.lily.core.api.notification.observatory.internal.notifier;

import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.notification.Feature;
import org.sheepy.lily.core.api.notification.IFeatures;
import org.sheepy.lily.core.api.notification.INotifier;
import org.sheepy.lily.core.api.notification.observatory.INotifierAdapterObservatoryBuilder;
import org.sheepy.lily.core.api.notification.observatory.IObservatory;
import org.sheepy.lily.core.api.notification.observatory.internal.InternalObservatoryBuilder;
import org.sheepy.lily.core.api.notification.observatory.internal.allocation.AdapterObservatory;
import org.sheepy.lily.core.api.notification.observatory.internal.allocation.IAdapterPOI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class NotifierAdapterObservatory<Type extends IFeatures<Type>, Notifier extends IExtender & INotifier<Type>> extends
																														  AdapterObservatory<Notifier> implements
																																					   IObservatory
{
	private final List<INotifierPOI<Type>> observationPoints;

	public NotifierAdapterObservatory(Class<Notifier> notifierAdapterClass,
									  List<IAdapterPOI<Notifier>> listeners,
									  List<INotifierPOI<Type>> observationPoints,
									  List<Consumer<Notifier>> addListeners,
									  List<Consumer<Notifier>> removeListeners)
	{
		super(notifierAdapterClass, listeners, addListeners, removeListeners);
		this.observationPoints = List.copyOf(observationPoints);
	}

	@Override
	protected void onAdapterUpdate(final Notifier oldAdapter, final Notifier newAdapter)
	{
		super.onAdapterUpdate(oldAdapter, newAdapter);
		for (final var point : observationPoints)
		{
			if (oldAdapter != null) point.sulk(oldAdapter);
			if (newAdapter != null) point.listen(newAdapter);
		}
	}

	public static final class Builder<Type extends IFeatures<Type>, Notifier extends IExtender & INotifier<Type>> extends
																												  AdapterObservatory.Builder<Notifier> implements
																																					   INotifierAdapterObservatoryBuilder<Type, Notifier>,
																																					   InternalObservatoryBuilder
	{
		private final List<INotifierPOI<Type>> observationPoints = new ArrayList<>();

		public Builder(Class<Notifier> notifierAdapterClass)
		{
			super(notifierAdapterClass);
		}

		@Override
		public <Listener> INotifierAdapterObservatoryBuilder<Type, Notifier> listen(Listener listener,
																					Feature<? super Listener, Type> feature)
		{
			observationPoints.add(new NotifierPOI<>(listener, feature));
			return this;
		}

		@Override
		public INotifierAdapterObservatoryBuilder<Type, Notifier> listenNoParam(final Runnable listener,
																				final Feature<?, Type> feature)
		{
			observationPoints.add(new NoParamNotifierPOI<>(listener, feature));
			return this;
		}

		@Override
		public IObservatory build()
		{
			return new NotifierAdapterObservatory<>(adapterClass,
													listeners,
													observationPoints,
													addListeners,
													removeListeners);
		}
	}
}

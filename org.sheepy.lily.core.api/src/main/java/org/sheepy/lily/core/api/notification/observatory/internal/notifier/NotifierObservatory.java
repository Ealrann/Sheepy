package org.sheepy.lily.core.api.notification.observatory.internal.notifier;

import org.sheepy.lily.core.api.adapter.ILilyEObject;
import org.sheepy.lily.core.api.notification.IFeature;
import org.sheepy.lily.core.api.notification.INotifier;
import org.sheepy.lily.core.api.notification.observatory.INotifierObservatoryBuilder;
import org.sheepy.lily.core.api.notification.observatory.IObservatory;

import java.util.ArrayList;
import java.util.List;

public final class NotifierObservatory<Type extends IFeature<?, ?>> implements IObservatory
{
	private final INotifier<Type> notifier;
	private final List<INotifierPOI<Type>> observationPoints;

	public NotifierObservatory(INotifier<Type> notifier, List<INotifierPOI<Type>> observationPoints)
	{
		this.notifier = notifier;
		this.observationPoints = List.copyOf(observationPoints);
	}

	@Override
	public void observe(ILilyEObject object)
	{
		for (final var point : observationPoints)
		{
			point.listen(notifier);
		}
	}

	@Override
	public void shut(ILilyEObject object)
	{
		for (final var point : observationPoints)
		{
			point.sulk(notifier);
		}
	}

	public static final class Builder<Type extends IFeature<?, ?>> implements INotifierObservatoryBuilder<Type>
	{
		private final INotifier<Type> notifier;
		private final List<INotifierPOI<Type>> observationPoints = new ArrayList<>();

		public Builder(INotifier<Type> notifier)
		{
			this.notifier = notifier;
		}

		@Override
		public <Listener> INotifierObservatoryBuilder<Type> listen(Listener listener,
																   IFeature<? super Listener, Type> feature)
		{
			observationPoints.add(new NotifierPOI<>(listener, feature));
			return this;
		}

		@Override
		public INotifierObservatoryBuilder<Type> listenNoParam(final Runnable listener, final IFeature<?, Type> feature)
		{
			observationPoints.add(new NoParamNotifierPOI<>(listener, feature));
			return this;
		}

		@Override
		public IObservatory build()
		{
			return new NotifierObservatory<>(notifier, observationPoints);
		}
	}
}
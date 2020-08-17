package org.sheepy.lily.core.api.extender;

import org.sheepy.lily.core.api.model.ILilyEObject;
import org.sheepy.lily.core.api.notification.IEMFNotifier;
import org.sheepy.lily.core.api.reflect.ExecutionHandle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public interface IExtenderHandle<T extends IExtender>
{
	Class<T> getExtenderClass();
	T getExtender();
	void load(ILilyEObject target, IEMFNotifier notifier);
	void dispose(ILilyEObject target, IEMFNotifier notifier);
	<A extends Annotation> Stream<AnnotatedHandle<A>> annotatedHandles(Class<A> annotationClass);

	void listen(ExtenderListener<T> extenderUpdateListener);
	void listenNoParam(Runnable extenderUpdateListener);
	void sulk(ExtenderListener<T> extenderUpdateListener);
	void sulkNoParam(Runnable extenderUpdateListener);

	final class AnnotatedHandle<T extends Annotation>
	{
		private final T annotation;
		private final Method method;
		private final ExecutionHandle executionHandle;

		public AnnotatedHandle(T annotation, Method method, ExecutionHandle executionHandle)
		{
			this.annotation = annotation;
			this.method = method;
			this.executionHandle = executionHandle;
		}

		public T annotation()
		{
			return annotation;
		}

		public Method method()
		{
			return method;
		}

		public ExecutionHandle executionHandle()
		{
			return executionHandle;
		}
	}

	@FunctionalInterface
	interface ExtenderListener<T extends IExtender> extends BiConsumer<T, T>
	{
		void update(T oldExtender, T newExtender);

		@Override
		default void accept(T oldExtender, T newExtender)
		{
			update(oldExtender, newExtender);
		}
	}
}

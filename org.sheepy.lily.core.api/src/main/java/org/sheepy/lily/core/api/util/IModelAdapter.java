package org.sheepy.lily.core.api.util;

import java.util.EventListener;

import org.eclipse.emf.ecore.EObject;
import org.sheepy.lily.core.api.adapter.IAdapter;

public interface IModelAdapter extends IAdapter
{

	<T extends EObject> void addListener(Class<T> classifier, IModelListener<T> listener);
	<T extends EObject> void removeListener(Class<T> classifier, IModelListener<T> listener);

	static interface IModelListener<T extends EObject> extends EventListener
	{
		void onObjectAdded(T object);
		void onObjectRemoved(T object);
	}
}

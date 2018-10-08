package org.sheepy.common.api.adapter.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.sheepy.common.api.adapter.ISheepyAdapter;
import org.sheepy.common.api.adapter.ISheepyAdapterFactory;
import org.sheepy.common.api.adapter.ISheepyAdapterWrapper;
import org.sheepy.common.api.util.ClassHierarchyIterator;
import org.sheepy.common.api.util.SingletonUtil;

public class SheepyAdapterWrapper implements ISheepyAdapterWrapper
{
	private final Class<? extends ISheepyAdapter> classifier;
	private EClass targetEClass;
	private final boolean singleton;

	private ISheepyAdapter reference = null;
	private HashMap<EObject, ISheepyAdapter> adapterMap = new HashMap<>();

	public SheepyAdapterWrapper(Class<? extends ISheepyAdapter> classifier, EClass targetEClass)
	{
		this.classifier = classifier;
		this.targetEClass = targetEClass;
		singleton = SingletonUtil.isSingleton(classifier);
	}

	@Override
	public ISheepyAdapter adapt(EObject target, ISheepyAdapterFactory adapterFactory)
	{
		if (singleton)
		{
			if (reference == null)
			{
				reference = instantiateNew(target, adapterFactory);
			}
			reference.setTarget(target);
			return reference;
		}
		else
		{
			ISheepyAdapter res = adapterMap.get(target);
			if (res == null)
			{
				res = instantiateNew(target, adapterFactory);
				adapterMap.put(target, res);
				res.setTarget(target);
			}
			return res;
		}
	}

	protected ISheepyAdapter instantiateNew(EObject target, ISheepyAdapterFactory adapterFactory)
	{
		ISheepyAdapter res = null;

		try
		{
			Constructor<? extends ISheepyAdapter> constructor = classifier.getConstructor();
			res = constructor.newInstance();
			res.setAdapterFactory(adapterFactory);
			target.eAdapters().add(res);

		} catch (NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public boolean isAdapterForType(Class<? extends ISheepyAdapter> type)
	{
		ClassHierarchyIterator iterator = new ClassHierarchyIterator(classifier);
		while (iterator.hasNext())
		{
			Class<?> current = iterator.next();
			if (current == type)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isDefaultAdapter()
	{
		return false;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return targetEClass == eClass;
	}
}

package org.sheepy.common.api.util;

import java.lang.reflect.Constructor;

import org.sheepy.common.api.adapter.ISingletonAdapter;

public class ReflectivityUtils
{
	private static final Class<?>[] NO_TYPES = new Class<?>[0];
	private static final Object[] NO_OBJECTS = new Object[0];

	public static <T extends ISingletonAdapter> T constructNew(Class<T> classifier)
	{
		T res = null;
		try
		{
			Constructor<T> constructor = classifier.getConstructor(NO_TYPES);
			res = constructor.newInstance(NO_OBJECTS);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}
}
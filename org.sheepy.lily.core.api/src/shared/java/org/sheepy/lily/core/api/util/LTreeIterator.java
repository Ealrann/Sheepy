package org.sheepy.lily.core.api.util;

import java.util.Iterator;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.sheepy.lily.core.model.root.LObject;

public class LTreeIterator extends AbstractTreeIterator<LObject>
{
	private static final long serialVersionUID = 1L;
	
	public LTreeIterator(LObject object)
	{
		super(object);
	}

	@Override
	public Iterator<LObject> getChildren(Object object)
	{
		return ((LObject) object).lContents().iterator();
	}
}
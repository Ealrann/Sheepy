package org.sheepy.lily.core.api.util;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.sheepy.lily.core.model.root.LObject;
import org.sheepy.lily.core.model.root.RootPackage;

public class LContentAdapter extends EContentAdapter
{
	@Override
	protected void addAdapter(Notifier notifier)
	{
		if (notifier instanceof LObject)
		{
			super.addAdapter(notifier);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void notifyChanged(Notification notification)
	{
		super.notifyChanged(notification);
		var feature = notification.getFeature();

		if (feature instanceof EReference)
		{
			var eReference = (EReference) feature;
			if (eReference.isContainment()
					&& RootPackage.Literals.LOBJECT.isSuperTypeOf(eReference.getEReferenceType()))
			{
				switch (notification.getEventType())
				{
				case Notification.ADD:
					unitAddedInternal((LObject) notification.getNewValue());
					break;
				case Notification.ADD_MANY:
					for (EObject eo : (List<EObject>) notification.getNewValue())
					{
						unitAddedInternal((LObject) eo);
					}
					break;
				case Notification.REMOVE:
					unitRemovedInternal(notification.getNotifier(),
							(LObject) notification.getOldValue());
					break;
				case Notification.REMOVE_MANY:
					for (EObject eo : (List<EObject>) notification.getOldValue())
					{
						unitRemovedInternal((LObject) notification.getNotifier(), (LObject) eo);
					}
					break;
				}
			}
		}
	}

	private void unitAddedInternal(LObject unit)
	{
		unitAdded(unit);

		TreeIterator<LObject> it = unit.lAllContents();
		while (it.hasNext())
		{
			unitAdded(it.next());
		}
	}

	private void unitRemovedInternal(Object container, LObject removedUnit)
	{
		LinkedList<LObject> reverseOrder = new LinkedList<>();

		TreeIterator<LObject> it = removedUnit.lAllContents();
		while (it.hasNext())
		{
			reverseOrder.addFirst(it.next());
		}
		reverseOrder.add(removedUnit);

		for (LObject lUnit : reverseOrder)
		{
			if (lUnit != removedUnit)
			{
				unitRemoved(lUnit.eContainer(), lUnit);
			}
			else
			{
				unitRemoved(container, removedUnit);
			}
		}
	}

	protected void unitAdded(@SuppressWarnings("unused") LObject unit)
	{
		// Nothing by default
	}

	protected void unitRemoved(	@SuppressWarnings("unused") Object oldContainer,
								@SuppressWarnings("unused") LObject removedUnit)
	{
		// Nothing by default
	}
}

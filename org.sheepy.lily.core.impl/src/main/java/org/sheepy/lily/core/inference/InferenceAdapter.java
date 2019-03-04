package org.sheepy.lily.core.inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sheepy.lily.core.api.action.context.ActionExecutionContext;
import org.sheepy.lily.core.api.adapter.impl.AbstractStatefullAdapter;
import org.sheepy.lily.core.api.application.IApplicationAdapter;
import org.sheepy.lily.core.api.inference.IInferenceAdapter;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.core.model.inference.AbstractNotification;
import org.sheepy.lily.core.model.inference.Condition;
import org.sheepy.lily.core.model.inference.Inferer;
import org.sheepy.lily.core.model.inference.LNotification;
import org.sheepy.lily.core.model.inference.LRule;
import org.sheepy.lily.core.model.inference.ParameteredNotification;
import org.sheepy.lily.core.model.root.LObject;
import org.sheepy.lily.core.model.types.Parameter;

public class InferenceAdapter extends AbstractStatefullAdapter implements IInferenceAdapter
{
	public InferenceGraph ruleGraph = new InferenceGraph();
	private final Map<EClass, List<INotificationListener>> listeners = new HashMap<>();

	@Override
	public void addInferer(Inferer inferer)
	{
		ruleGraph.registerUnit(inferer);
	}

	@Override
	public void removeInferer(Inferer inferer)
	{
		ruleGraph.unregisterUnit(inferer);
	}

	@Override
	public void postNotification(LObject adaptedEntity, LNotification notification)
	{
		postNotificationInternal(adaptedEntity, notification, null);

		if (listeners.containsKey(notification.eClass()))
		{
			for (INotificationListener listener : listeners.get(notification.eClass()))
			{
				listener.onNotification(adaptedEntity, notification);
			}
		}
	}

	@Override
	public <T extends Parameter> void postNotification(	LObject adaptedEntity,
														ParameteredNotification<T> notification,
														T parameter)
	{
		postNotificationInternal(adaptedEntity, notification, parameter);

		if (listeners.containsKey(notification.eClass()))
		{
			for (INotificationListener listener : listeners.get(notification.eClass()))
			{
				listener.onNotification(adaptedEntity, notification, parameter);
			}
		}
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	private void postNotificationInternal(	LObject adaptedEntity,
											AbstractNotification notification,
											Parameter parameter)
	{
		for (LRule rule : ruleGraph.getLinkedRules(notification))
		{
			if (rule.getNotification().match(notification))
			{
				boolean match = true;

				for (Condition c : rule.getConditions())
				{
					if (c.getType().isInstance(parameter) == false || c.match(parameter) == false)
					{
						match = false;
						break;
					}
				}

				if (match)
				{
					ActionExecutionContext ec = new ActionExecutionContext(
							(LObject) ((Inferer) rule.eContainer()).lExecutor(), rule.getAction(),
							parameter);

					// TODO si actionDispatcher est null, il faut mettre de coté
					// les notif pour
					// les executer plus tard

					var application = (Application) EcoreUtil.getRootContainer(adaptedEntity);
					var adapter = IApplicationAdapter.adapt(application);
					adapter.getCadencer().postAction(ec);
				}
			}
		}
	}

	@Override
	public void addNotificationListener(EClass clazz, INotificationListener listener)
	{
		List<INotificationListener> list = listeners.get(clazz);
		if (list == null)
		{
			list = new ArrayList<>();
			listeners.put(clazz, list);
		}
		list.add(listener);
	}

	@Override
	public void removeNotificationListener(EClass clazz, INotificationListener listener)
	{
		listeners.remove(clazz, listener);
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return true;
	}
}
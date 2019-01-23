/**
 */
package org.sheepy.lily.core.model.ui.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ChildCreationExtenderManager;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.sheepy.lily.core.model.presentation.PresentationPackage;
import org.sheepy.lily.core.model.presentation.UIPage;
import org.sheepy.lily.core.model.presentation.util.PresentationSwitch;
import org.sheepy.lily.core.model.ui.UiFactory;
import org.sheepy.lily.core.model.ui.UiPackage;

import org.sheepy.lily.core.model.ui.util.UiAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class UiItemProviderAdapterFactory extends UiAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender
{
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This helps manage the child creation extenders.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(UiEditPlugin.INSTANCE, UiPackage.eNS_URI);

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UiItemProviderAdapterFactory()
	{
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.Panel} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PanelItemProvider panelItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.Panel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPanelAdapter()
	{
		if (panelItemProvider == null)
		{
			panelItemProvider = new PanelItemProvider(this);
		}

		return panelItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.DynamicRowLayout} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DynamicRowLayoutItemProvider dynamicRowLayoutItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.DynamicRowLayout}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createDynamicRowLayoutAdapter()
	{
		if (dynamicRowLayoutItemProvider == null)
		{
			dynamicRowLayoutItemProvider = new DynamicRowLayoutItemProvider(this);
		}

		return dynamicRowLayoutItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.VariableLabel} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableLabelItemProvider variableLabelItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.VariableLabel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createVariableLabelAdapter()
	{
		if (variableLabelItemProvider == null)
		{
			variableLabelItemProvider = new VariableLabelItemProvider(this);
		}

		return variableLabelItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.Label} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LabelItemProvider labelItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.Label}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createLabelAdapter()
	{
		if (labelItemProvider == null)
		{
			labelItemProvider = new LabelItemProvider(this);
		}

		return labelItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.Slider} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SliderItemProvider sliderItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.Slider}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSliderAdapter()
	{
		if (sliderItemProvider == null)
		{
			sliderItemProvider = new SliderItemProvider(this);
		}

		return sliderItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.TextField} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextFieldItemProvider textFieldItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.TextField}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createTextFieldAdapter()
	{
		if (textFieldItemProvider == null)
		{
			textFieldItemProvider = new TextFieldItemProvider(this);
		}

		return textFieldItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.Button} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ButtonItemProvider buttonItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.Button}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createButtonAdapter()
	{
		if (buttonItemProvider == null)
		{
			buttonItemProvider = new ButtonItemProvider(this);
		}

		return buttonItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.BooleanButton} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BooleanButtonItemProvider booleanButtonItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.BooleanButton}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createBooleanButtonAdapter()
	{
		if (booleanButtonItemProvider == null)
		{
			booleanButtonItemProvider = new BooleanButtonItemProvider(this);
		}

		return booleanButtonItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.lily.core.model.ui.BooleanActionButton} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BooleanActionButtonItemProvider booleanActionButtonItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.lily.core.model.ui.BooleanActionButton}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createBooleanActionButtonAdapter()
	{
		if (booleanActionButtonItemProvider == null)
		{
			booleanActionButtonItemProvider = new BooleanActionButtonItemProvider(this);
		}

		return booleanActionButtonItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComposeableAdapterFactory getRootAdapterFactory()
	{
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory)
	{
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type)
	{
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type)
	{
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type)
	{
		if (isFactoryForType(type))
		{
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter)))
			{
				return adapter;
			}
		}

		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<IChildCreationExtender> getChildCreationExtenders()
	{
		return childCreationExtenderManager.getChildCreationExtenders();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain)
	{
		return childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceLocator getResourceLocator()
	{
		return childCreationExtenderManager;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void addListener(INotifyChangedListener notifyChangedListener)
	{
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener)
	{
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void fireNotifyChanged(Notification notification)
	{
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null)
		{
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void dispose()
	{
		if (panelItemProvider != null) panelItemProvider.dispose();
		if (dynamicRowLayoutItemProvider != null) dynamicRowLayoutItemProvider.dispose();
		if (variableLabelItemProvider != null) variableLabelItemProvider.dispose();
		if (labelItemProvider != null) labelItemProvider.dispose();
		if (sliderItemProvider != null) sliderItemProvider.dispose();
		if (textFieldItemProvider != null) textFieldItemProvider.dispose();
		if (buttonItemProvider != null) buttonItemProvider.dispose();
		if (booleanButtonItemProvider != null) booleanButtonItemProvider.dispose();
		if (booleanActionButtonItemProvider != null) booleanActionButtonItemProvider.dispose();
	}

	/**
	 * A child creation extender for the {@link PresentationPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class PresentationChildCreationExtender implements IChildCreationExtender
	{
		/**
		 * The switch for creating child descriptors specific to each extended class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected static class CreationSwitch extends PresentationSwitch<Object>
		{
			/**
			 * The child descriptors being populated.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected List<Object> newChildDescriptors;

			/**
			 * The domain in which to create the children.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected EditingDomain editingDomain;

			/**
			 * Creates the a switch for populating child descriptors in the given domain.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain) 
			{
				this.newChildDescriptors = newChildDescriptors;
				this.editingDomain = editingDomain;
			}
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			@Override
			public Object caseUIPage(UIPage object)
			{
				newChildDescriptors.add
					(createChildParameter
						(PresentationPackage.Literals.UI_PAGE__PANELS,
						 UiFactory.eINSTANCE.createPanel()));

				return null;
			}
 
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected CommandParameter createChildParameter(Object feature, Object child)
			{
				return new CommandParameter(null, feature, child);
			}

		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain)
		{
			ArrayList<Object> result = new ArrayList<Object>();
			new CreationSwitch(result, editingDomain).doSwitch((EObject)object);
			return result;
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public ResourceLocator getResourceLocator()
		{
			return UiEditPlugin.INSTANCE;
		}
	}

}

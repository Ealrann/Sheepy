/**
 */
package org.sheepy.lily.core.model.resource.impl;

import org.eclipse.emf.ecore.EClass;

import org.sheepy.lily.core.model.resource.AbstractModuleResource;
import org.sheepy.lily.core.model.resource.ResourcePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Module Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AbstractModuleResourceImpl extends FileResourceImpl implements AbstractModuleResource
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractModuleResourceImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return ResourcePackage.Literals.ABSTRACT_MODULE_RESOURCE;
	}

} //AbstractModuleResourceImpl

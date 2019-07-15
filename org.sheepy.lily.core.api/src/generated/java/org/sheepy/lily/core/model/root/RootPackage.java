/**
 */
package org.sheepy.lily.core.model.root;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

import org.sheepy.lily.core.model.action.ActionPackage;

import org.sheepy.lily.core.model.inference.InferencePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sheepy.lily.core.model.root.RootFactory
 * @model kind="package"
 * @generated
 */
public interface RootPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "root";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.sheepy.lily.core.model.root";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "root";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RootPackage eINSTANCE = org.sheepy.lily.core.model.root.impl.RootPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sheepy.lily.core.model.root.LObject <em>LObject</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.lily.core.model.root.LObject
	 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getLObject()
	 * @generated
	 */
	int LOBJECT = 0;

	/**
	 * The feature id for the '<em><b>Content Objects</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOBJECT__CONTENT_OBJECTS = InferencePackage.IINFERENCE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>LObject</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOBJECT_FEATURE_COUNT = InferencePackage.IINFERENCE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sheepy.lily.core.model.root.impl.XActionImpl <em>XAction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.lily.core.model.root.impl.XActionImpl
	 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getXAction()
	 * @generated
	 */
	int XACTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XACTION__NAME = ActionPackage.ACTION__NAME;

	/**
	 * The number of structural features of the '<em>XAction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XACTION_FEATURE_COUNT = ActionPackage.ACTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '<em>LContent List</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.EList
	 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getLContentList()
	 * @generated
	 */
	int LCONTENT_LIST = 2;

	/**
	 * The meta object id for the '<em>LTree Iterator</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.lily.core.api.util.LTreeIterator
	 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getLTreeIterator()
	 * @generated
	 */
	int LTREE_ITERATOR = 3;

	/**
	 * The meta object id for the '<em>LCollection</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Collection
	 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getLCollection()
	 * @generated
	 */
	int LCOLLECTION = 4;

	/**
	 * Returns the meta object for class '{@link org.sheepy.lily.core.model.root.LObject <em>LObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>LObject</em>'.
	 * @see org.sheepy.lily.core.model.root.LObject
	 * @generated
	 */
	EClass getLObject();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.lily.core.model.root.LObject#getContentObjects <em>Content Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content Objects</em>'.
	 * @see org.sheepy.lily.core.model.root.LObject#getContentObjects()
	 * @see #getLObject()
	 * @generated
	 */
	EAttribute getLObject_ContentObjects();

	/**
	 * Returns the meta object for class '{@link org.sheepy.lily.core.model.root.XAction <em>XAction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>XAction</em>'.
	 * @see org.sheepy.lily.core.model.root.XAction
	 * @generated
	 */
	EClass getXAction();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.EList <em>LContent List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>LContent List</em>'.
	 * @see org.eclipse.emf.common.util.EList
	 * @model instanceClass="org.eclipse.emf.common.util.EList&lt;org.sheepy.lily.core.model.root.LObject&gt;"
	 * @generated
	 */
	EDataType getLContentList();

	/**
	 * Returns the meta object for data type '{@link org.sheepy.lily.core.api.util.LTreeIterator <em>LTree Iterator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>LTree Iterator</em>'.
	 * @see org.sheepy.lily.core.api.util.LTreeIterator
	 * @model instanceClass="org.sheepy.lily.core.api.util.LTreeIterator"
	 * @generated
	 */
	EDataType getLTreeIterator();

	/**
	 * Returns the meta object for data type '{@link java.util.Collection <em>LCollection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>LCollection</em>'.
	 * @see java.util.Collection
	 * @model instanceClass="java.util.Collection&lt;org.sheepy.lily.core.model.root.LObject&gt;"
	 * @generated
	 */
	EDataType getLCollection();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RootFactory getRootFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals
	{
		/**
		 * The meta object literal for the '{@link org.sheepy.lily.core.model.root.LObject <em>LObject</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.lily.core.model.root.LObject
		 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getLObject()
		 * @generated
		 */
		EClass LOBJECT = eINSTANCE.getLObject();

		/**
		 * The meta object literal for the '<em><b>Content Objects</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOBJECT__CONTENT_OBJECTS = eINSTANCE.getLObject_ContentObjects();

		/**
		 * The meta object literal for the '{@link org.sheepy.lily.core.model.root.impl.XActionImpl <em>XAction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.lily.core.model.root.impl.XActionImpl
		 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getXAction()
		 * @generated
		 */
		EClass XACTION = eINSTANCE.getXAction();

		/**
		 * The meta object literal for the '<em>LContent List</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.EList
		 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getLContentList()
		 * @generated
		 */
		EDataType LCONTENT_LIST = eINSTANCE.getLContentList();

		/**
		 * The meta object literal for the '<em>LTree Iterator</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.lily.core.api.util.LTreeIterator
		 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getLTreeIterator()
		 * @generated
		 */
		EDataType LTREE_ITERATOR = eINSTANCE.getLTreeIterator();

		/**
		 * The meta object literal for the '<em>LCollection</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Collection
		 * @see org.sheepy.lily.core.model.root.impl.RootPackageImpl#getLCollection()
		 * @generated
		 */
		EDataType LCOLLECTION = eINSTANCE.getLCollection();

	}

} //RootPackage

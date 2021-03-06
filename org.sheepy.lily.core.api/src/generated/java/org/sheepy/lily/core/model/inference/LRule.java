/**
 */
package org.sheepy.lily.core.model.inference;

import org.eclipse.emf.common.util.EList;
import org.sheepy.lily.core.api.model.ILilyEObject;
import org.sheepy.lily.core.model.action.Action;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>LRule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.lily.core.model.inference.LRule#getNotification <em>Notification</em>}</li>
 *   <li>{@link org.sheepy.lily.core.model.inference.LRule#getAction <em>Action</em>}</li>
 *   <li>{@link org.sheepy.lily.core.model.inference.LRule#getConditions <em>Conditions</em>}</li>
 * </ul>
 *
 * @see org.sheepy.lily.core.model.inference.InferencePackage#getLRule()
 * @model
 * @extends ILilyEObject
 * @generated
 */
public interface LRule extends ILilyEObject
{
	/**
	 * Returns the value of the '<em><b>Notification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notification</em>' containment reference.
	 * @see #setNotification(AbstractNotification)
	 * @see org.sheepy.lily.core.model.inference.InferencePackage#getLRule_Notification()
	 * @model containment="true"
	 * @generated
	 */
	AbstractNotification getNotification();

	/**
	 * Sets the value of the '{@link org.sheepy.lily.core.model.inference.LRule#getNotification <em>Notification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notification</em>' containment reference.
	 * @see #getNotification()
	 * @generated
	 */
	void setNotification(AbstractNotification value);

	/**
	 * Returns the value of the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' reference.
	 * @see #setAction(Action)
	 * @see org.sheepy.lily.core.model.inference.InferencePackage#getLRule_Action()
	 * @model
	 * @generated
	 */
	Action getAction();

	/**
	 * Sets the value of the '{@link org.sheepy.lily.core.model.inference.LRule#getAction <em>Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(Action value);

	/**
	 * Returns the value of the '<em><b>Conditions</b></em>' containment reference list.
	 * The list contents are of type {@link org.sheepy.lily.core.model.inference.Condition}<code>&lt;?&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conditions</em>' containment reference list.
	 * @see org.sheepy.lily.core.model.inference.InferencePackage#getLRule_Conditions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Condition<?>> getConditions();

} // LRule

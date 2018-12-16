/**
 */
package org.sheepy.common.model.presentation;

import org.eclipse.emf.common.util.EList;

import org.sheepy.common.model.application.IView;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IUI View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.common.model.presentation.IUIView#getUiPages <em>Ui Pages</em>}</li>
 *   <li>{@link org.sheepy.common.model.presentation.IUIView#getCurrentUIPage <em>Current UI Page</em>}</li>
 * </ul>
 *
 * @see org.sheepy.common.model.presentation.PresentationPackage#getIUIView()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IUIView extends IView
{
	/**
	 * Returns the value of the '<em><b>Ui Pages</b></em>' containment reference list.
	 * The list contents are of type {@link org.sheepy.common.model.presentation.UIPage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ui Pages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ui Pages</em>' containment reference list.
	 * @see org.sheepy.common.model.presentation.PresentationPackage#getIUIView_UiPages()
	 * @model containment="true"
	 * @generated
	 */
	EList<UIPage> getUiPages();

	/**
	 * Returns the value of the '<em><b>Current UI Page</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current UI Page</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current UI Page</em>' reference.
	 * @see #setCurrentUIPage(UIPage)
	 * @see org.sheepy.common.model.presentation.PresentationPackage#getIUIView_CurrentUIPage()
	 * @model
	 * @generated
	 */
	UIPage getCurrentUIPage();

	/**
	 * Sets the value of the '{@link org.sheepy.common.model.presentation.IUIView#getCurrentUIPage <em>Current UI Page</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current UI Page</em>' reference.
	 * @see #getCurrentUIPage()
	 * @generated
	 */
	void setCurrentUIPage(UIPage value);

} // IUIView
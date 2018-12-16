/**
 */
package org.sheepy.common.model.ui;

import org.sheepy.common.model.presentation.Control;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Button</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.common.model.ui.AbstractButton#getText <em>Text</em>}</li>
 * </ul>
 *
 * @see org.sheepy.common.model.ui.UiPackage#getAbstractButton()
 * @model abstract="true"
 * @generated
 */
public interface AbstractButton extends Control
{
	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see org.sheepy.common.model.ui.UiPackage#getAbstractButton_Text()
	 * @model default="" unique="false"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link org.sheepy.common.model.ui.AbstractButton#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

} // AbstractButton
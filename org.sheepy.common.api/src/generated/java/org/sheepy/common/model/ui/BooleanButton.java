/**
 */
package org.sheepy.common.model.ui;

import org.sheepy.common.model.variable.AbstractVariableResolver;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boolean Button</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.common.model.ui.BooleanButton#getVariableResolver <em>Variable Resolver</em>}</li>
 * </ul>
 *
 * @see org.sheepy.common.model.ui.UiPackage#getBooleanButton()
 * @model
 * @generated
 */
public interface BooleanButton extends AbstractBooleanButton
{
	/**
	 * Returns the value of the '<em><b>Variable Resolver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable Resolver</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable Resolver</em>' containment reference.
	 * @see #setVariableResolver(AbstractVariableResolver)
	 * @see org.sheepy.common.model.ui.UiPackage#getBooleanButton_VariableResolver()
	 * @model containment="true"
	 * @generated
	 */
	AbstractVariableResolver getVariableResolver();

	/**
	 * Sets the value of the '{@link org.sheepy.common.model.ui.BooleanButton#getVariableResolver <em>Variable Resolver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable Resolver</em>' containment reference.
	 * @see #getVariableResolver()
	 * @generated
	 */
	void setVariableResolver(AbstractVariableResolver value);

} // BooleanButton
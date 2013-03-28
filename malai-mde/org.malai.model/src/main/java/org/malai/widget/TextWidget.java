/**
 */
package org.malai.widget;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Widget</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.widget.TextWidget#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.widget.WidgetPackage#getTextWidget()
 * @model abstract="true"
 * @generated
 */
public interface TextWidget extends Widget {

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see org.malai.widget.WidgetPackage#getTextWidget_Text()
	 * @model
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link org.malai.widget.TextWidget#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);
} // TextWidget

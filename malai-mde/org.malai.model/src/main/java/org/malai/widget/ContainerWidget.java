/**
 */
package org.malai.widget;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container Widget</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.widget.ContainerWidget#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.widget.WidgetPackage#getContainerWidget()
 * @model abstract="true"
 * @generated
 */
public interface ContainerWidget extends Widget {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.malai.widget.Widget}.
	 * It is bidirectional and its opposite is '{@link org.malai.widget.Widget#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.malai.widget.WidgetPackage#getContainerWidget_Elements()
	 * @see org.malai.widget.Widget#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<Widget> getElements();

} // ContainerWidget

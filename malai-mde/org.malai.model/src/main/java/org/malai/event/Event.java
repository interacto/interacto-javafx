/**
 */
package org.malai.event;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.event.Event#getClazz <em>Clazz</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.event.EventPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends EObject {
	/**
	 * Returns the value of the '<em><b>Clazz</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clazz</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clazz</em>' containment reference.
	 * @see #setClazz(EClass)
	 * @see org.malai.event.EventPackage#getEvent_Clazz()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EClass getClazz();

	/**
	 * Sets the value of the '{@link org.malai.event.Event#getClazz <em>Clazz</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clazz</em>' containment reference.
	 * @see #getClazz()
	 * @generated
	 */
	void setClazz(EClass value);

} // Event

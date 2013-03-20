/**
 */
package org.malai.event;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.event.EventModel#getEvents <em>Events</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.event.EventPackage#getEventModel()
 * @model
 * @generated
 */
public interface EventModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link org.malai.event.Event}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see org.malai.event.EventPackage#getEventModel_Events()
	 * @model containment="true"
	 * @generated
	 */
	EList<Event> getEvents();

} // EventModel

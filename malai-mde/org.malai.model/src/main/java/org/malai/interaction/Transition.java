/**
 */
package org.malai.interaction;

import org.eclipse.emf.ecore.EObject;

import org.malai.event.Event;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.interaction.Transition#getInputState <em>Input State</em>}</li>
 *   <li>{@link org.malai.interaction.Transition#getOutputState <em>Output State</em>}</li>
 *   <li>{@link org.malai.interaction.Transition#getDescription <em>Description</em>}</li>
 *   <li>{@link org.malai.interaction.Transition#getEvent <em>Event</em>}</li>
 *   <li>{@link org.malai.interaction.Transition#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.malai.interaction.Transition#getActions <em>Actions</em>}</li>
 *   <li>{@link org.malai.interaction.Transition#getName <em>Name</em>}</li>
 *   <li>{@link org.malai.interaction.Transition#getHid <em>Hid</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.interaction.InteractionPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends EObject {
	/**
	 * Returns the value of the '<em><b>Input State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input State</em>' reference.
	 * @see #setInputState(State)
	 * @see org.malai.interaction.InteractionPackage#getTransition_InputState()
	 * @model required="true"
	 * @generated
	 */
	State getInputState();

	/**
	 * Sets the value of the '{@link org.malai.interaction.Transition#getInputState <em>Input State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input State</em>' reference.
	 * @see #getInputState()
	 * @generated
	 */
	void setInputState(State value);

	/**
	 * Returns the value of the '<em><b>Output State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output State</em>' reference.
	 * @see #setOutputState(State)
	 * @see org.malai.interaction.InteractionPackage#getTransition_OutputState()
	 * @model required="true"
	 * @generated
	 */
	State getOutputState();

	/**
	 * Sets the value of the '{@link org.malai.interaction.Transition#getOutputState <em>Output State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output State</em>' reference.
	 * @see #getOutputState()
	 * @generated
	 */
	void setOutputState(State value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.malai.interaction.InteractionPackage#getTransition_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.malai.interaction.Transition#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event</em>' reference.
	 * @see #setEvent(Event)
	 * @see org.malai.interaction.InteractionPackage#getTransition_Event()
	 * @model required="true"
	 * @generated
	 */
	Event getEvent();

	/**
	 * Sets the value of the '{@link org.malai.interaction.Transition#getEvent <em>Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event</em>' reference.
	 * @see #getEvent()
	 * @generated
	 */
	void setEvent(Event value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' attribute.
	 * @see #setCondition(String)
	 * @see org.malai.interaction.InteractionPackage#getTransition_Condition()
	 * @model
	 * @generated
	 */
	String getCondition();

	/**
	 * Sets the value of the '{@link org.malai.interaction.Transition#getCondition <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' attribute.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(String value);

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' attribute.
	 * @see #setActions(String)
	 * @see org.malai.interaction.InteractionPackage#getTransition_Actions()
	 * @model
	 * @generated
	 */
	String getActions();

	/**
	 * Sets the value of the '{@link org.malai.interaction.Transition#getActions <em>Actions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actions</em>' attribute.
	 * @see #getActions()
	 * @generated
	 */
	void setActions(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.malai.interaction.InteractionPackage#getTransition_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.malai.interaction.Transition#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Hid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hid</em>' attribute.
	 * @see #setHid(int)
	 * @see org.malai.interaction.InteractionPackage#getTransition_Hid()
	 * @model
	 * @generated
	 */
	int getHid();

	/**
	 * Sets the value of the '{@link org.malai.interaction.Transition#getHid <em>Hid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hid</em>' attribute.
	 * @see #getHid()
	 * @generated
	 */
	void setHid(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void action();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isGuardRespected();

} // Transition

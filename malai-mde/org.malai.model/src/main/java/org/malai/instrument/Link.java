/**
 */
package org.malai.instrument;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.malai.action.Action;

import org.malai.interaction.Interaction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.instrument.Link#getInteraction <em>Interaction</em>}</li>
 *   <li>{@link org.malai.instrument.Link#getAction <em>Action</em>}</li>
 *   <li>{@link org.malai.instrument.Link#getDescription <em>Description</em>}</li>
 *   <li>{@link org.malai.instrument.Link#getInterimFeedback <em>Interim Feedback</em>}</li>
 *   <li>{@link org.malai.instrument.Link#getIsConditionRespected <em>Is Condition Respected</em>}</li>
 *   <li>{@link org.malai.instrument.Link#getUpdateAction <em>Update Action</em>}</li>
 *   <li>{@link org.malai.instrument.Link#isExecuteOnUpdate <em>Execute On Update</em>}</li>
 *   <li>{@link org.malai.instrument.Link#getInitialiseAction <em>Initialise Action</em>}</li>
 *   <li>{@link org.malai.instrument.Link#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.malai.instrument.Link#getInstrument <em>Instrument</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.instrument.InstrumentPackage#getLink()
 * @model
 * @generated
 */
public interface Link extends EObject {
	/**
	 * Returns the value of the '<em><b>Interaction</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interaction</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interaction</em>' reference.
	 * @see #setInteraction(Interaction)
	 * @see org.malai.instrument.InstrumentPackage#getLink_Interaction()
	 * @model required="true"
	 * @generated
	 */
	Interaction getInteraction();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getInteraction <em>Interaction</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interaction</em>' reference.
	 * @see #getInteraction()
	 * @generated
	 */
	void setInteraction(Interaction value);

	/**
	 * Returns the value of the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' reference.
	 * @see #setAction(Action)
	 * @see org.malai.instrument.InstrumentPackage#getLink_Action()
	 * @model required="true"
	 * @generated
	 */
	Action getAction();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getAction <em>Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(Action value);

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
	 * @see org.malai.instrument.InstrumentPackage#getLink_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Interim Feedback</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interim Feedback</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interim Feedback</em>' attribute.
	 * @see #setInterimFeedback(String)
	 * @see org.malai.instrument.InstrumentPackage#getLink_InterimFeedback()
	 * @model
	 * @generated
	 */
	String getInterimFeedback();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getInterimFeedback <em>Interim Feedback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interim Feedback</em>' attribute.
	 * @see #getInterimFeedback()
	 * @generated
	 */
	void setInterimFeedback(String value);

	/**
	 * Returns the value of the '<em><b>Is Condition Respected</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Condition Respected</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Condition Respected</em>' attribute.
	 * @see #setIsConditionRespected(String)
	 * @see org.malai.instrument.InstrumentPackage#getLink_IsConditionRespected()
	 * @model
	 * @generated
	 */
	String getIsConditionRespected();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getIsConditionRespected <em>Is Condition Respected</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Condition Respected</em>' attribute.
	 * @see #getIsConditionRespected()
	 * @generated
	 */
	void setIsConditionRespected(String value);

	/**
	 * Returns the value of the '<em><b>Update Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Action</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Action</em>' attribute.
	 * @see #setUpdateAction(String)
	 * @see org.malai.instrument.InstrumentPackage#getLink_UpdateAction()
	 * @model
	 * @generated
	 */
	String getUpdateAction();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getUpdateAction <em>Update Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Action</em>' attribute.
	 * @see #getUpdateAction()
	 * @generated
	 */
	void setUpdateAction(String value);

	/**
	 * Returns the value of the '<em><b>Execute On Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execute On Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Execute On Update</em>' attribute.
	 * @see #setExecuteOnUpdate(boolean)
	 * @see org.malai.instrument.InstrumentPackage#getLink_ExecuteOnUpdate()
	 * @model required="true"
	 * @generated
	 */
	boolean isExecuteOnUpdate();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#isExecuteOnUpdate <em>Execute On Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execute On Update</em>' attribute.
	 * @see #isExecuteOnUpdate()
	 * @generated
	 */
	void setExecuteOnUpdate(boolean value);

	/**
	 * Returns the value of the '<em><b>Initialise Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initialise Action</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initialise Action</em>' attribute.
	 * @see #setInitialiseAction(String)
	 * @see org.malai.instrument.InstrumentPackage#getLink_InitialiseAction()
	 * @model
	 * @generated
	 */
	String getInitialiseAction();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getInitialiseAction <em>Initialise Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initialise Action</em>' attribute.
	 * @see #getInitialiseAction()
	 * @generated
	 */
	void setInitialiseAction(String value);

	/**
	 * Returns the value of the '<em><b>Clazz</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clazz</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clazz</em>' containment reference.
	 * @see #setClazz(EClass)
	 * @see org.malai.instrument.InstrumentPackage#getLink_Clazz()
	 * @model containment="true"
	 * @generated
	 */
	EClass getClazz();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getClazz <em>Clazz</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clazz</em>' containment reference.
	 * @see #getClazz()
	 * @generated
	 */
	void setClazz(EClass value);

	/**
	 * Returns the value of the '<em><b>Instrument</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.malai.instrument.Instrument#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instrument</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instrument</em>' container reference.
	 * @see #setInstrument(Instrument)
	 * @see org.malai.instrument.InstrumentPackage#getLink_Instrument()
	 * @see org.malai.instrument.Instrument#getLinks
	 * @model opposite="links" transient="false"
	 * @generated
	 */
	Instrument getInstrument();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Link#getInstrument <em>Instrument</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instrument</em>' container reference.
	 * @see #getInstrument()
	 * @generated
	 */
	void setInstrument(Instrument value);

} // Link

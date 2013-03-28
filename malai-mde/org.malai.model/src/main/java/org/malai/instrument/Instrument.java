/**
 */
package org.malai.instrument;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.malai.widget.Widget;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instrument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.instrument.Instrument#getLinks <em>Links</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getHelpers <em>Helpers</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getDescription <em>Description</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getVersion <em>Version</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getDateCreation <em>Date Creation</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getInterimFeedback <em>Interim Feedback</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#isInitiallyActivated <em>Initially Activated</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getProvidedWidgets <em>Provided Widgets</em>}</li>
 *   <li>{@link org.malai.instrument.Instrument#getSubscribedWidgets <em>Subscribed Widgets</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.instrument.InstrumentPackage#getInstrument()
 * @model
 * @generated
 */
public interface Instrument extends EObject {
	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link org.malai.instrument.Link}.
	 * It is bidirectional and its opposite is '{@link org.malai.instrument.Link#getInstrument <em>Instrument</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_Links()
	 * @see org.malai.instrument.Link#getInstrument
	 * @model opposite="instrument" containment="true"
	 * @generated
	 */
	EList<Link> getLinks();

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
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_Clazz()
	 * @model containment="true"
	 * @generated
	 */
	EClass getClazz();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Instrument#getClazz <em>Clazz</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clazz</em>' containment reference.
	 * @see #getClazz()
	 * @generated
	 */
	void setClazz(EClass value);

	/**
	 * Returns the value of the '<em><b>Helpers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EClassifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Helpers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Helpers</em>' containment reference list.
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_Helpers()
	 * @model containment="true"
	 * @generated
	 */
	EList<EClassifier> getHelpers();

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
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Instrument#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_Author()
	 * @model
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Instrument#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Instrument#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Date Creation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Creation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Creation</em>' attribute.
	 * @see #setDateCreation(String)
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_DateCreation()
	 * @model
	 * @generated
	 */
	String getDateCreation();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Instrument#getDateCreation <em>Date Creation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Creation</em>' attribute.
	 * @see #getDateCreation()
	 * @generated
	 */
	void setDateCreation(String value);

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
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_InterimFeedback()
	 * @model
	 * @generated
	 */
	String getInterimFeedback();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Instrument#getInterimFeedback <em>Interim Feedback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interim Feedback</em>' attribute.
	 * @see #getInterimFeedback()
	 * @generated
	 */
	void setInterimFeedback(String value);

	/**
	 * Returns the value of the '<em><b>Initially Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initially Activated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initially Activated</em>' attribute.
	 * @see #setInitiallyActivated(boolean)
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_InitiallyActivated()
	 * @model
	 * @generated
	 */
	boolean isInitiallyActivated();

	/**
	 * Sets the value of the '{@link org.malai.instrument.Instrument#isInitiallyActivated <em>Initially Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initially Activated</em>' attribute.
	 * @see #isInitiallyActivated()
	 * @generated
	 */
	void setInitiallyActivated(boolean value);

	/**
	 * Returns the value of the '<em><b>Provided Widgets</b></em>' containment reference list.
	 * The list contents are of type {@link org.malai.widget.Widget}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Widgets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Widgets</em>' containment reference list.
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_ProvidedWidgets()
	 * @model containment="true"
	 * @generated
	 */
	EList<Widget> getProvidedWidgets();

	/**
	 * Returns the value of the '<em><b>Subscribed Widgets</b></em>' reference list.
	 * The list contents are of type {@link org.malai.widget.Widget}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subscribed Widgets</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subscribed Widgets</em>' reference list.
	 * @see org.malai.instrument.InstrumentPackage#getInstrument_SubscribedWidgets()
	 * @model
	 * @generated
	 */
	EList<Widget> getSubscribedWidgets();

} // Instrument

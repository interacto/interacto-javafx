/**
 */
package org.malai.instrument;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.malai.instrument.InstrumentFactory
 * @model kind="package"
 * @generated
 */
public interface InstrumentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "instrument";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.malai.instrument/1_0_0//org/malai/instrument";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org_malai_instrument";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InstrumentPackage eINSTANCE = org.malai.instrument.impl.InstrumentPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.malai.instrument.impl.InstrumentImpl <em>Instrument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.instrument.impl.InstrumentImpl
	 * @see org.malai.instrument.impl.InstrumentPackageImpl#getInstrument()
	 * @generated
	 */
	int INSTRUMENT = 0;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__LINKS = 0;

	/**
	 * The feature id for the '<em><b>Clazz</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__CLAZZ = 1;

	/**
	 * The feature id for the '<em><b>Helpers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__HELPERS = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__AUTHOR = 4;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__VERSION = 5;

	/**
	 * The feature id for the '<em><b>Date Creation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__DATE_CREATION = 6;

	/**
	 * The feature id for the '<em><b>Interim Feedback</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__INTERIM_FEEDBACK = 7;

	/**
	 * The feature id for the '<em><b>Initially Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__INITIALLY_ACTIVATED = 8;

	/**
	 * The feature id for the '<em><b>Provided Widgets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__PROVIDED_WIDGETS = 9;

	/**
	 * The feature id for the '<em><b>Subscribed Widgets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT__SUBSCRIBED_WIDGETS = 10;

	/**
	 * The number of structural features of the '<em>Instrument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUMENT_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link org.malai.instrument.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.instrument.impl.LinkImpl
	 * @see org.malai.instrument.impl.InstrumentPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 1;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__INTERACTION = 0;

	/**
	 * The feature id for the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__ACTION = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Interim Feedback</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__INTERIM_FEEDBACK = 3;

	/**
	 * The feature id for the '<em><b>Is Condition Respected</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__IS_CONDITION_RESPECTED = 4;

	/**
	 * The feature id for the '<em><b>Update Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__UPDATE_ACTION = 5;

	/**
	 * The feature id for the '<em><b>Execute On Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__EXECUTE_ON_UPDATE = 6;

	/**
	 * The feature id for the '<em><b>Initialise Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__INITIALISE_ACTION = 7;

	/**
	 * The feature id for the '<em><b>Clazz</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__CLAZZ = 8;

	/**
	 * The feature id for the '<em><b>Instrument</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__INSTRUMENT = 9;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = 10;


	/**
	 * Returns the meta object for class '{@link org.malai.instrument.Instrument <em>Instrument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instrument</em>'.
	 * @see org.malai.instrument.Instrument
	 * @generated
	 */
	EClass getInstrument();

	/**
	 * Returns the meta object for the containment reference list '{@link org.malai.instrument.Instrument#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.malai.instrument.Instrument#getLinks()
	 * @see #getInstrument()
	 * @generated
	 */
	EReference getInstrument_Links();

	/**
	 * Returns the meta object for the containment reference '{@link org.malai.instrument.Instrument#getClazz <em>Clazz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clazz</em>'.
	 * @see org.malai.instrument.Instrument#getClazz()
	 * @see #getInstrument()
	 * @generated
	 */
	EReference getInstrument_Clazz();

	/**
	 * Returns the meta object for the containment reference list '{@link org.malai.instrument.Instrument#getHelpers <em>Helpers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Helpers</em>'.
	 * @see org.malai.instrument.Instrument#getHelpers()
	 * @see #getInstrument()
	 * @generated
	 */
	EReference getInstrument_Helpers();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Instrument#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.malai.instrument.Instrument#getDescription()
	 * @see #getInstrument()
	 * @generated
	 */
	EAttribute getInstrument_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Instrument#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.malai.instrument.Instrument#getAuthor()
	 * @see #getInstrument()
	 * @generated
	 */
	EAttribute getInstrument_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Instrument#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.malai.instrument.Instrument#getVersion()
	 * @see #getInstrument()
	 * @generated
	 */
	EAttribute getInstrument_Version();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Instrument#getDateCreation <em>Date Creation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date Creation</em>'.
	 * @see org.malai.instrument.Instrument#getDateCreation()
	 * @see #getInstrument()
	 * @generated
	 */
	EAttribute getInstrument_DateCreation();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Instrument#getInterimFeedback <em>Interim Feedback</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interim Feedback</em>'.
	 * @see org.malai.instrument.Instrument#getInterimFeedback()
	 * @see #getInstrument()
	 * @generated
	 */
	EAttribute getInstrument_InterimFeedback();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Instrument#isInitiallyActivated <em>Initially Activated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initially Activated</em>'.
	 * @see org.malai.instrument.Instrument#isInitiallyActivated()
	 * @see #getInstrument()
	 * @generated
	 */
	EAttribute getInstrument_InitiallyActivated();

	/**
	 * Returns the meta object for the containment reference list '{@link org.malai.instrument.Instrument#getProvidedWidgets <em>Provided Widgets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Provided Widgets</em>'.
	 * @see org.malai.instrument.Instrument#getProvidedWidgets()
	 * @see #getInstrument()
	 * @generated
	 */
	EReference getInstrument_ProvidedWidgets();

	/**
	 * Returns the meta object for the reference list '{@link org.malai.instrument.Instrument#getSubscribedWidgets <em>Subscribed Widgets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Subscribed Widgets</em>'.
	 * @see org.malai.instrument.Instrument#getSubscribedWidgets()
	 * @see #getInstrument()
	 * @generated
	 */
	EReference getInstrument_SubscribedWidgets();

	/**
	 * Returns the meta object for class '{@link org.malai.instrument.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.malai.instrument.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link org.malai.instrument.Link#getInteraction <em>Interaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Interaction</em>'.
	 * @see org.malai.instrument.Link#getInteraction()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Interaction();

	/**
	 * Returns the meta object for the reference '{@link org.malai.instrument.Link#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Action</em>'.
	 * @see org.malai.instrument.Link#getAction()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Action();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Link#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.malai.instrument.Link#getDescription()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Link#getInterimFeedback <em>Interim Feedback</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interim Feedback</em>'.
	 * @see org.malai.instrument.Link#getInterimFeedback()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_InterimFeedback();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Link#getIsConditionRespected <em>Is Condition Respected</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Condition Respected</em>'.
	 * @see org.malai.instrument.Link#getIsConditionRespected()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_IsConditionRespected();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Link#getUpdateAction <em>Update Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Update Action</em>'.
	 * @see org.malai.instrument.Link#getUpdateAction()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_UpdateAction();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Link#isExecuteOnUpdate <em>Execute On Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Execute On Update</em>'.
	 * @see org.malai.instrument.Link#isExecuteOnUpdate()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_ExecuteOnUpdate();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.instrument.Link#getInitialiseAction <em>Initialise Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initialise Action</em>'.
	 * @see org.malai.instrument.Link#getInitialiseAction()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_InitialiseAction();

	/**
	 * Returns the meta object for the containment reference '{@link org.malai.instrument.Link#getClazz <em>Clazz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clazz</em>'.
	 * @see org.malai.instrument.Link#getClazz()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Clazz();

	/**
	 * Returns the meta object for the container reference '{@link org.malai.instrument.Link#getInstrument <em>Instrument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Instrument</em>'.
	 * @see org.malai.instrument.Link#getInstrument()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Instrument();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InstrumentFactory getInstrumentFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.malai.instrument.impl.InstrumentImpl <em>Instrument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.instrument.impl.InstrumentImpl
		 * @see org.malai.instrument.impl.InstrumentPackageImpl#getInstrument()
		 * @generated
		 */
		EClass INSTRUMENT = eINSTANCE.getInstrument();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTRUMENT__LINKS = eINSTANCE.getInstrument_Links();

		/**
		 * The meta object literal for the '<em><b>Clazz</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTRUMENT__CLAZZ = eINSTANCE.getInstrument_Clazz();

		/**
		 * The meta object literal for the '<em><b>Helpers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTRUMENT__HELPERS = eINSTANCE.getInstrument_Helpers();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTRUMENT__DESCRIPTION = eINSTANCE.getInstrument_Description();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTRUMENT__AUTHOR = eINSTANCE.getInstrument_Author();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTRUMENT__VERSION = eINSTANCE.getInstrument_Version();

		/**
		 * The meta object literal for the '<em><b>Date Creation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTRUMENT__DATE_CREATION = eINSTANCE.getInstrument_DateCreation();

		/**
		 * The meta object literal for the '<em><b>Interim Feedback</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTRUMENT__INTERIM_FEEDBACK = eINSTANCE.getInstrument_InterimFeedback();

		/**
		 * The meta object literal for the '<em><b>Initially Activated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTRUMENT__INITIALLY_ACTIVATED = eINSTANCE.getInstrument_InitiallyActivated();

		/**
		 * The meta object literal for the '<em><b>Provided Widgets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTRUMENT__PROVIDED_WIDGETS = eINSTANCE.getInstrument_ProvidedWidgets();

		/**
		 * The meta object literal for the '<em><b>Subscribed Widgets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTRUMENT__SUBSCRIBED_WIDGETS = eINSTANCE.getInstrument_SubscribedWidgets();

		/**
		 * The meta object literal for the '{@link org.malai.instrument.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.instrument.impl.LinkImpl
		 * @see org.malai.instrument.impl.InstrumentPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Interaction</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__INTERACTION = eINSTANCE.getLink_Interaction();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__ACTION = eINSTANCE.getLink_Action();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK__DESCRIPTION = eINSTANCE.getLink_Description();

		/**
		 * The meta object literal for the '<em><b>Interim Feedback</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK__INTERIM_FEEDBACK = eINSTANCE.getLink_InterimFeedback();

		/**
		 * The meta object literal for the '<em><b>Is Condition Respected</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK__IS_CONDITION_RESPECTED = eINSTANCE.getLink_IsConditionRespected();

		/**
		 * The meta object literal for the '<em><b>Update Action</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK__UPDATE_ACTION = eINSTANCE.getLink_UpdateAction();

		/**
		 * The meta object literal for the '<em><b>Execute On Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK__EXECUTE_ON_UPDATE = eINSTANCE.getLink_ExecuteOnUpdate();

		/**
		 * The meta object literal for the '<em><b>Initialise Action</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK__INITIALISE_ACTION = eINSTANCE.getLink_InitialiseAction();

		/**
		 * The meta object literal for the '<em><b>Clazz</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__CLAZZ = eINSTANCE.getLink_Clazz();

		/**
		 * The meta object literal for the '<em><b>Instrument</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__INSTRUMENT = eINSTANCE.getLink_Instrument();

	}

} //InstrumentPackage

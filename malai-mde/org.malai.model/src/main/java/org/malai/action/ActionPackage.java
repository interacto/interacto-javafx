/**
 */
package org.malai.action;

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
 * @see org.malai.action.ActionFactory
 * @model kind="package"
 * @generated
 */
public interface ActionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "action";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.malai.action/1_0_0//org/malai/action";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org_malai_action";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ActionPackage eINSTANCE = org.malai.action.impl.ActionPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.malai.action.impl.ActionModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.action.impl.ActionModelImpl
	 * @see org.malai.action.impl.ActionPackageImpl#getActionModel()
	 * @generated
	 */
	int ACTION_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_MODEL__ACTIONS = 0;

	/**
	 * The feature id for the '<em><b>Helpers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_MODEL__HELPERS = 1;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_MODEL_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.malai.action.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.action.impl.ActionImpl
	 * @see org.malai.action.impl.ActionPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 1;

	/**
	 * The feature id for the '<em><b>Clazz</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__CLAZZ = 0;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__DEPENDENCIES = 1;

	/**
	 * The feature id for the '<em><b>Undoable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__UNDOABLE = 2;

	/**
	 * The feature id for the '<em><b>Execute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__EXECUTE = 3;

	/**
	 * The feature id for the '<em><b>Can Do</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__CAN_DO = 4;

	/**
	 * The feature id for the '<em><b>Undo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__UNDO = 5;

	/**
	 * The feature id for the '<em><b>Redo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__REDO = 6;

	/**
	 * The feature id for the '<em><b>Cancelled By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__CANCELLED_BY = 7;

	/**
	 * The feature id for the '<em><b>Modify Instrument</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__MODIFY_INSTRUMENT = 8;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.malai.action.impl.ActionDependencyImpl <em>Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.action.impl.ActionDependencyImpl
	 * @see org.malai.action.impl.ActionPackageImpl#getActionDependency()
	 * @generated
	 */
	int ACTION_DEPENDENCY = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEPENDENCY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Src Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEPENDENCY__SRC_ACTION = 1;

	/**
	 * The feature id for the '<em><b>Tgt Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEPENDENCY__TGT_ACTIONS = 2;

	/**
	 * The number of structural features of the '<em>Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_DEPENDENCY_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link org.malai.action.ActionModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.malai.action.ActionModel
	 * @generated
	 */
	EClass getActionModel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.malai.action.ActionModel#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.malai.action.ActionModel#getActions()
	 * @see #getActionModel()
	 * @generated
	 */
	EReference getActionModel_Actions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.malai.action.ActionModel#getHelpers <em>Helpers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Helpers</em>'.
	 * @see org.malai.action.ActionModel#getHelpers()
	 * @see #getActionModel()
	 * @generated
	 */
	EReference getActionModel_Helpers();

	/**
	 * Returns the meta object for class '{@link org.malai.action.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see org.malai.action.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the containment reference '{@link org.malai.action.Action#getClazz <em>Clazz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Clazz</em>'.
	 * @see org.malai.action.Action#getClazz()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Clazz();

	/**
	 * Returns the meta object for the containment reference list '{@link org.malai.action.Action#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dependencies</em>'.
	 * @see org.malai.action.Action#getDependencies()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Dependencies();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.action.Action#isUndoable <em>Undoable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Undoable</em>'.
	 * @see org.malai.action.Action#isUndoable()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Undoable();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.action.Action#getExecute <em>Execute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Execute</em>'.
	 * @see org.malai.action.Action#getExecute()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Execute();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.action.Action#getCanDo <em>Can Do</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Can Do</em>'.
	 * @see org.malai.action.Action#getCanDo()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_CanDo();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.action.Action#getUndo <em>Undo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Undo</em>'.
	 * @see org.malai.action.Action#getUndo()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Undo();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.action.Action#getRedo <em>Redo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Redo</em>'.
	 * @see org.malai.action.Action#getRedo()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Redo();

	/**
	 * Returns the meta object for the reference list '{@link org.malai.action.Action#getCancelledBy <em>Cancelled By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Cancelled By</em>'.
	 * @see org.malai.action.Action#getCancelledBy()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_CancelledBy();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.action.Action#isModifyInstrument <em>Modify Instrument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modify Instrument</em>'.
	 * @see org.malai.action.Action#isModifyInstrument()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_ModifyInstrument();

	/**
	 * Returns the meta object for class '{@link org.malai.action.ActionDependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependency</em>'.
	 * @see org.malai.action.ActionDependency
	 * @generated
	 */
	EClass getActionDependency();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.action.ActionDependency#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.malai.action.ActionDependency#getName()
	 * @see #getActionDependency()
	 * @generated
	 */
	EAttribute getActionDependency_Name();

	/**
	 * Returns the meta object for the reference '{@link org.malai.action.ActionDependency#getSrcAction <em>Src Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src Action</em>'.
	 * @see org.malai.action.ActionDependency#getSrcAction()
	 * @see #getActionDependency()
	 * @generated
	 */
	EReference getActionDependency_SrcAction();

	/**
	 * Returns the meta object for the reference list '{@link org.malai.action.ActionDependency#getTgtActions <em>Tgt Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Tgt Actions</em>'.
	 * @see org.malai.action.ActionDependency#getTgtActions()
	 * @see #getActionDependency()
	 * @generated
	 */
	EReference getActionDependency_TgtActions();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ActionFactory getActionFactory();

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
		 * The meta object literal for the '{@link org.malai.action.impl.ActionModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.action.impl.ActionModelImpl
		 * @see org.malai.action.impl.ActionPackageImpl#getActionModel()
		 * @generated
		 */
		EClass ACTION_MODEL = eINSTANCE.getActionModel();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_MODEL__ACTIONS = eINSTANCE.getActionModel_Actions();

		/**
		 * The meta object literal for the '<em><b>Helpers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_MODEL__HELPERS = eINSTANCE.getActionModel_Helpers();

		/**
		 * The meta object literal for the '{@link org.malai.action.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.action.impl.ActionImpl
		 * @see org.malai.action.impl.ActionPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Clazz</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__CLAZZ = eINSTANCE.getAction_Clazz();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__DEPENDENCIES = eINSTANCE.getAction_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Undoable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__UNDOABLE = eINSTANCE.getAction_Undoable();

		/**
		 * The meta object literal for the '<em><b>Execute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__EXECUTE = eINSTANCE.getAction_Execute();

		/**
		 * The meta object literal for the '<em><b>Can Do</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__CAN_DO = eINSTANCE.getAction_CanDo();

		/**
		 * The meta object literal for the '<em><b>Undo</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__UNDO = eINSTANCE.getAction_Undo();

		/**
		 * The meta object literal for the '<em><b>Redo</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__REDO = eINSTANCE.getAction_Redo();

		/**
		 * The meta object literal for the '<em><b>Cancelled By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__CANCELLED_BY = eINSTANCE.getAction_CancelledBy();

		/**
		 * The meta object literal for the '<em><b>Modify Instrument</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__MODIFY_INSTRUMENT = eINSTANCE.getAction_ModifyInstrument();

		/**
		 * The meta object literal for the '{@link org.malai.action.impl.ActionDependencyImpl <em>Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.action.impl.ActionDependencyImpl
		 * @see org.malai.action.impl.ActionPackageImpl#getActionDependency()
		 * @generated
		 */
		EClass ACTION_DEPENDENCY = eINSTANCE.getActionDependency();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_DEPENDENCY__NAME = eINSTANCE.getActionDependency_Name();

		/**
		 * The meta object literal for the '<em><b>Src Action</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_DEPENDENCY__SRC_ACTION = eINSTANCE.getActionDependency_SrcAction();

		/**
		 * The meta object literal for the '<em><b>Tgt Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_DEPENDENCY__TGT_ACTIONS = eINSTANCE.getActionDependency_TgtActions();

	}

} //ActionPackage

/**
 */
package org.malai.action;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.action.Action#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.malai.action.Action#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.malai.action.Action#isUndoable <em>Undoable</em>}</li>
 *   <li>{@link org.malai.action.Action#getExecute <em>Execute</em>}</li>
 *   <li>{@link org.malai.action.Action#getCanDo <em>Can Do</em>}</li>
 *   <li>{@link org.malai.action.Action#getUndo <em>Undo</em>}</li>
 *   <li>{@link org.malai.action.Action#getRedo <em>Redo</em>}</li>
 *   <li>{@link org.malai.action.Action#getCancelledBy <em>Cancelled By</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.action.ActionPackage#getAction()
 * @model
 * @generated
 */
public interface Action extends EObject {
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
	 * @see org.malai.action.ActionPackage#getAction_Clazz()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EClass getClazz();

	/**
	 * Sets the value of the '{@link org.malai.action.Action#getClazz <em>Clazz</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clazz</em>' containment reference.
	 * @see #getClazz()
	 * @generated
	 */
	void setClazz(EClass value);

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.malai.action.ActionDependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference list.
	 * @see org.malai.action.ActionPackage#getAction_Dependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<ActionDependency> getDependencies();

	/**
	 * Returns the value of the '<em><b>Undoable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Undoable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Undoable</em>' attribute.
	 * @see #setUndoable(boolean)
	 * @see org.malai.action.ActionPackage#getAction_Undoable()
	 * @model required="true"
	 * @generated
	 */
	boolean isUndoable();

	/**
	 * Sets the value of the '{@link org.malai.action.Action#isUndoable <em>Undoable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Undoable</em>' attribute.
	 * @see #isUndoable()
	 * @generated
	 */
	void setUndoable(boolean value);

	/**
	 * Returns the value of the '<em><b>Execute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Execute</em>' attribute.
	 * @see #setExecute(String)
	 * @see org.malai.action.ActionPackage#getAction_Execute()
	 * @model
	 * @generated
	 */
	String getExecute();

	/**
	 * Sets the value of the '{@link org.malai.action.Action#getExecute <em>Execute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execute</em>' attribute.
	 * @see #getExecute()
	 * @generated
	 */
	void setExecute(String value);

	/**
	 * Returns the value of the '<em><b>Can Do</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Can Do</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Can Do</em>' attribute.
	 * @see #setCanDo(String)
	 * @see org.malai.action.ActionPackage#getAction_CanDo()
	 * @model
	 * @generated
	 */
	String getCanDo();

	/**
	 * Sets the value of the '{@link org.malai.action.Action#getCanDo <em>Can Do</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Can Do</em>' attribute.
	 * @see #getCanDo()
	 * @generated
	 */
	void setCanDo(String value);

	/**
	 * Returns the value of the '<em><b>Undo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Undo</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Undo</em>' attribute.
	 * @see #setUndo(String)
	 * @see org.malai.action.ActionPackage#getAction_Undo()
	 * @model
	 * @generated
	 */
	String getUndo();

	/**
	 * Sets the value of the '{@link org.malai.action.Action#getUndo <em>Undo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Undo</em>' attribute.
	 * @see #getUndo()
	 * @generated
	 */
	void setUndo(String value);

	/**
	 * Returns the value of the '<em><b>Redo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Redo</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Redo</em>' attribute.
	 * @see #setRedo(String)
	 * @see org.malai.action.ActionPackage#getAction_Redo()
	 * @model
	 * @generated
	 */
	String getRedo();

	/**
	 * Sets the value of the '{@link org.malai.action.Action#getRedo <em>Redo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Redo</em>' attribute.
	 * @see #getRedo()
	 * @generated
	 */
	void setRedo(String value);

	/**
	 * Returns the value of the '<em><b>Cancelled By</b></em>' reference list.
	 * The list contents are of type {@link org.malai.action.Action}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cancelled By</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cancelled By</em>' reference list.
	 * @see org.malai.action.ActionPackage#getAction_CancelledBy()
	 * @model
	 * @generated
	 */
	EList<Action> getCancelledBy();

} // Action

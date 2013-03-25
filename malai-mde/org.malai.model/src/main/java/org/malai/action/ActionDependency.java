/**
 */
package org.malai.action;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.malai.action.ActionDependency#getName <em>Name</em>}</li>
 *   <li>{@link org.malai.action.ActionDependency#getSrcAction <em>Src Action</em>}</li>
 *   <li>{@link org.malai.action.ActionDependency#getTgtActions <em>Tgt Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.malai.action.ActionPackage#getActionDependency()
 * @model
 * @generated
 */
public interface ActionDependency extends EObject {
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
	 * @see org.malai.action.ActionPackage#getActionDependency_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.malai.action.ActionDependency#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Src Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Src Action</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src Action</em>' reference.
	 * @see #setSrcAction(Action)
	 * @see org.malai.action.ActionPackage#getActionDependency_SrcAction()
	 * @model required="true"
	 * @generated
	 */
	Action getSrcAction();

	/**
	 * Sets the value of the '{@link org.malai.action.ActionDependency#getSrcAction <em>Src Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src Action</em>' reference.
	 * @see #getSrcAction()
	 * @generated
	 */
	void setSrcAction(Action value);

	/**
	 * Returns the value of the '<em><b>Tgt Actions</b></em>' reference list.
	 * The list contents are of type {@link org.malai.action.Action}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgt Actions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgt Actions</em>' reference list.
	 * @see org.malai.action.ActionPackage#getActionDependency_TgtActions()
	 * @model required="true"
	 * @generated
	 */
	EList<Action> getTgtActions();

} // ActionDependency

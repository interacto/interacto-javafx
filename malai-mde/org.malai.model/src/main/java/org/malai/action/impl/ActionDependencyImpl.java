/**
 */
package org.malai.action.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.malai.action.Action;
import org.malai.action.ActionDependency;
import org.malai.action.ActionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.malai.action.impl.ActionDependencyImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.malai.action.impl.ActionDependencyImpl#getSrcAction <em>Src Action</em>}</li>
 *   <li>{@link org.malai.action.impl.ActionDependencyImpl#getTgtActions <em>Tgt Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionDependencyImpl extends EObjectImpl implements ActionDependency {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSrcAction() <em>Src Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcAction()
	 * @generated
	 * @ordered
	 */
	protected Action srcAction;

	/**
	 * The cached value of the '{@link #getTgtActions() <em>Tgt Actions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTgtActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> tgtActions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ActionPackage.Literals.ACTION_DEPENDENCY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION_DEPENDENCY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action getSrcAction() {
		if (srcAction != null && srcAction.eIsProxy()) {
			InternalEObject oldSrcAction = (InternalEObject)srcAction;
			srcAction = (Action)eResolveProxy(oldSrcAction);
			if (srcAction != oldSrcAction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ActionPackage.ACTION_DEPENDENCY__SRC_ACTION, oldSrcAction, srcAction));
			}
		}
		return srcAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action basicGetSrcAction() {
		return srcAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrcAction(Action newSrcAction) {
		Action oldSrcAction = srcAction;
		srcAction = newSrcAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION_DEPENDENCY__SRC_ACTION, oldSrcAction, srcAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Action> getTgtActions() {
		if (tgtActions == null) {
			tgtActions = new EObjectResolvingEList<Action>(Action.class, this, ActionPackage.ACTION_DEPENDENCY__TGT_ACTIONS);
		}
		return tgtActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ActionPackage.ACTION_DEPENDENCY__NAME:
				return getName();
			case ActionPackage.ACTION_DEPENDENCY__SRC_ACTION:
				if (resolve) return getSrcAction();
				return basicGetSrcAction();
			case ActionPackage.ACTION_DEPENDENCY__TGT_ACTIONS:
				return getTgtActions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ActionPackage.ACTION_DEPENDENCY__NAME:
				setName((String)newValue);
				return;
			case ActionPackage.ACTION_DEPENDENCY__SRC_ACTION:
				setSrcAction((Action)newValue);
				return;
			case ActionPackage.ACTION_DEPENDENCY__TGT_ACTIONS:
				getTgtActions().clear();
				getTgtActions().addAll((Collection<? extends Action>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ActionPackage.ACTION_DEPENDENCY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ActionPackage.ACTION_DEPENDENCY__SRC_ACTION:
				setSrcAction((Action)null);
				return;
			case ActionPackage.ACTION_DEPENDENCY__TGT_ACTIONS:
				getTgtActions().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ActionPackage.ACTION_DEPENDENCY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ActionPackage.ACTION_DEPENDENCY__SRC_ACTION:
				return srcAction != null;
			case ActionPackage.ACTION_DEPENDENCY__TGT_ACTIONS:
				return tgtActions != null && !tgtActions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ActionDependencyImpl

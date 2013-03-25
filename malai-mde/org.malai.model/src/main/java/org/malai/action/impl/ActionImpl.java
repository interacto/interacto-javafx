/**
 */
package org.malai.action.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.malai.action.Action;
import org.malai.action.ActionDependency;
import org.malai.action.ActionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.malai.action.impl.ActionImpl#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.malai.action.impl.ActionImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.malai.action.impl.ActionImpl#isUndoable <em>Undoable</em>}</li>
 *   <li>{@link org.malai.action.impl.ActionImpl#getExecute <em>Execute</em>}</li>
 *   <li>{@link org.malai.action.impl.ActionImpl#getCanDo <em>Can Do</em>}</li>
 *   <li>{@link org.malai.action.impl.ActionImpl#getUndo <em>Undo</em>}</li>
 *   <li>{@link org.malai.action.impl.ActionImpl#getRedo <em>Redo</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionImpl extends EObjectImpl implements Action {
	/**
	 * The cached value of the '{@link #getClazz() <em>Clazz</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClazz()
	 * @generated
	 * @ordered
	 */
	protected EClass clazz;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<ActionDependency> dependencies;

	/**
	 * The default value of the '{@link #isUndoable() <em>Undoable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUndoable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UNDOABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUndoable() <em>Undoable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUndoable()
	 * @generated
	 * @ordered
	 */
	protected boolean undoable = UNDOABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getExecute() <em>Execute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecute()
	 * @generated
	 * @ordered
	 */
	protected static final String EXECUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExecute() <em>Execute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecute()
	 * @generated
	 * @ordered
	 */
	protected String execute = EXECUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCanDo() <em>Can Do</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCanDo()
	 * @generated
	 * @ordered
	 */
	protected static final String CAN_DO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCanDo() <em>Can Do</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCanDo()
	 * @generated
	 * @ordered
	 */
	protected String canDo = CAN_DO_EDEFAULT;

	/**
	 * The default value of the '{@link #getUndo() <em>Undo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUndo()
	 * @generated
	 * @ordered
	 */
	protected static final String UNDO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUndo() <em>Undo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUndo()
	 * @generated
	 * @ordered
	 */
	protected String undo = UNDO_EDEFAULT;

	/**
	 * The default value of the '{@link #getRedo() <em>Redo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedo()
	 * @generated
	 * @ordered
	 */
	protected static final String REDO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRedo() <em>Redo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedo()
	 * @generated
	 * @ordered
	 */
	protected String redo = REDO_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ActionPackage.Literals.ACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClazz() {
		return clazz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClazz(EClass newClazz, NotificationChain msgs) {
		EClass oldClazz = clazz;
		clazz = newClazz;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION__CLAZZ, oldClazz, newClazz);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClazz(EClass newClazz) {
		if (newClazz != clazz) {
			NotificationChain msgs = null;
			if (clazz != null)
				msgs = ((InternalEObject)clazz).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ActionPackage.ACTION__CLAZZ, null, msgs);
			if (newClazz != null)
				msgs = ((InternalEObject)newClazz).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ActionPackage.ACTION__CLAZZ, null, msgs);
			msgs = basicSetClazz(newClazz, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION__CLAZZ, newClazz, newClazz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ActionDependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectContainmentEList<ActionDependency>(ActionDependency.class, this, ActionPackage.ACTION__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUndoable() {
		return undoable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUndoable(boolean newUndoable) {
		boolean oldUndoable = undoable;
		undoable = newUndoable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION__UNDOABLE, oldUndoable, undoable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExecute() {
		return execute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecute(String newExecute) {
		String oldExecute = execute;
		execute = newExecute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION__EXECUTE, oldExecute, execute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCanDo() {
		return canDo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCanDo(String newCanDo) {
		String oldCanDo = canDo;
		canDo = newCanDo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION__CAN_DO, oldCanDo, canDo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUndo() {
		return undo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUndo(String newUndo) {
		String oldUndo = undo;
		undo = newUndo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION__UNDO, oldUndo, undo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRedo() {
		return redo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRedo(String newRedo) {
		String oldRedo = redo;
		redo = newRedo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionPackage.ACTION__REDO, oldRedo, redo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ActionPackage.ACTION__CLAZZ:
				return basicSetClazz(null, msgs);
			case ActionPackage.ACTION__DEPENDENCIES:
				return ((InternalEList<?>)getDependencies()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ActionPackage.ACTION__CLAZZ:
				return getClazz();
			case ActionPackage.ACTION__DEPENDENCIES:
				return getDependencies();
			case ActionPackage.ACTION__UNDOABLE:
				return isUndoable();
			case ActionPackage.ACTION__EXECUTE:
				return getExecute();
			case ActionPackage.ACTION__CAN_DO:
				return getCanDo();
			case ActionPackage.ACTION__UNDO:
				return getUndo();
			case ActionPackage.ACTION__REDO:
				return getRedo();
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
			case ActionPackage.ACTION__CLAZZ:
				setClazz((EClass)newValue);
				return;
			case ActionPackage.ACTION__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends ActionDependency>)newValue);
				return;
			case ActionPackage.ACTION__UNDOABLE:
				setUndoable((Boolean)newValue);
				return;
			case ActionPackage.ACTION__EXECUTE:
				setExecute((String)newValue);
				return;
			case ActionPackage.ACTION__CAN_DO:
				setCanDo((String)newValue);
				return;
			case ActionPackage.ACTION__UNDO:
				setUndo((String)newValue);
				return;
			case ActionPackage.ACTION__REDO:
				setRedo((String)newValue);
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
			case ActionPackage.ACTION__CLAZZ:
				setClazz((EClass)null);
				return;
			case ActionPackage.ACTION__DEPENDENCIES:
				getDependencies().clear();
				return;
			case ActionPackage.ACTION__UNDOABLE:
				setUndoable(UNDOABLE_EDEFAULT);
				return;
			case ActionPackage.ACTION__EXECUTE:
				setExecute(EXECUTE_EDEFAULT);
				return;
			case ActionPackage.ACTION__CAN_DO:
				setCanDo(CAN_DO_EDEFAULT);
				return;
			case ActionPackage.ACTION__UNDO:
				setUndo(UNDO_EDEFAULT);
				return;
			case ActionPackage.ACTION__REDO:
				setRedo(REDO_EDEFAULT);
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
			case ActionPackage.ACTION__CLAZZ:
				return clazz != null;
			case ActionPackage.ACTION__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
			case ActionPackage.ACTION__UNDOABLE:
				return undoable != UNDOABLE_EDEFAULT;
			case ActionPackage.ACTION__EXECUTE:
				return EXECUTE_EDEFAULT == null ? execute != null : !EXECUTE_EDEFAULT.equals(execute);
			case ActionPackage.ACTION__CAN_DO:
				return CAN_DO_EDEFAULT == null ? canDo != null : !CAN_DO_EDEFAULT.equals(canDo);
			case ActionPackage.ACTION__UNDO:
				return UNDO_EDEFAULT == null ? undo != null : !UNDO_EDEFAULT.equals(undo);
			case ActionPackage.ACTION__REDO:
				return REDO_EDEFAULT == null ? redo != null : !REDO_EDEFAULT.equals(redo);
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
		result.append(" (undoable: ");
		result.append(undoable);
		result.append(", execute: ");
		result.append(execute);
		result.append(", canDo: ");
		result.append(canDo);
		result.append(", undo: ");
		result.append(undo);
		result.append(", redo: ");
		result.append(redo);
		result.append(')');
		return result.toString();
	}

} //ActionImpl

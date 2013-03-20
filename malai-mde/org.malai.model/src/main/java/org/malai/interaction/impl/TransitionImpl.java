/**
 */
package org.malai.interaction.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.malai.event.Event;

import org.malai.interaction.InteractionPackage;
import org.malai.interaction.State;
import org.malai.interaction.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.malai.interaction.impl.TransitionImpl#getInputState <em>Input State</em>}</li>
 *   <li>{@link org.malai.interaction.impl.TransitionImpl#getOutputState <em>Output State</em>}</li>
 *   <li>{@link org.malai.interaction.impl.TransitionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.malai.interaction.impl.TransitionImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link org.malai.interaction.impl.TransitionImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.malai.interaction.impl.TransitionImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link org.malai.interaction.impl.TransitionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.malai.interaction.impl.TransitionImpl#getHid <em>Hid</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends EObjectImpl implements Transition {
	/**
	 * The cached value of the '{@link #getInputState() <em>Input State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputState()
	 * @generated
	 * @ordered
	 */
	protected State inputState;

	/**
	 * The cached value of the '{@link #getOutputState() <em>Output State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputState()
	 * @generated
	 * @ordered
	 */
	protected State outputState;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected Event event;

	/**
	 * The default value of the '{@link #getCondition() <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String CONDITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected String condition = CONDITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getActions() <em>Actions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected String actions = ACTIONS_EDEFAULT;

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
	 * The default value of the '{@link #getHid() <em>Hid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHid()
	 * @generated
	 * @ordered
	 */
	protected static final int HID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHid() <em>Hid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHid()
	 * @generated
	 * @ordered
	 */
	protected int hid = HID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InteractionPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getInputState() {
		if (inputState != null && inputState.eIsProxy()) {
			InternalEObject oldInputState = (InternalEObject)inputState;
			inputState = (State)eResolveProxy(oldInputState);
			if (inputState != oldInputState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InteractionPackage.TRANSITION__INPUT_STATE, oldInputState, inputState));
			}
		}
		return inputState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetInputState() {
		return inputState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputState(State newInputState) {
		State oldInputState = inputState;
		inputState = newInputState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InteractionPackage.TRANSITION__INPUT_STATE, oldInputState, inputState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getOutputState() {
		if (outputState != null && outputState.eIsProxy()) {
			InternalEObject oldOutputState = (InternalEObject)outputState;
			outputState = (State)eResolveProxy(oldOutputState);
			if (outputState != oldOutputState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InteractionPackage.TRANSITION__OUTPUT_STATE, oldOutputState, outputState));
			}
		}
		return outputState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetOutputState() {
		return outputState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputState(State newOutputState) {
		State oldOutputState = outputState;
		outputState = newOutputState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InteractionPackage.TRANSITION__OUTPUT_STATE, oldOutputState, outputState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InteractionPackage.TRANSITION__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event getEvent() {
		if (event != null && event.eIsProxy()) {
			InternalEObject oldEvent = (InternalEObject)event;
			event = (Event)eResolveProxy(oldEvent);
			if (event != oldEvent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InteractionPackage.TRANSITION__EVENT, oldEvent, event));
			}
		}
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event basicGetEvent() {
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvent(Event newEvent) {
		Event oldEvent = event;
		event = newEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InteractionPackage.TRANSITION__EVENT, oldEvent, event));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(String newCondition) {
		String oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InteractionPackage.TRANSITION__CONDITION, oldCondition, condition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getActions() {
		return actions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActions(String newActions) {
		String oldActions = actions;
		actions = newActions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InteractionPackage.TRANSITION__ACTIONS, oldActions, actions));
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
			eNotify(new ENotificationImpl(this, Notification.SET, InteractionPackage.TRANSITION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHid() {
		return hid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHid(int newHid) {
		int oldHid = hid;
		hid = newHid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InteractionPackage.TRANSITION__HID, oldHid, hid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void action() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGuardRespected() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InteractionPackage.TRANSITION__INPUT_STATE:
				if (resolve) return getInputState();
				return basicGetInputState();
			case InteractionPackage.TRANSITION__OUTPUT_STATE:
				if (resolve) return getOutputState();
				return basicGetOutputState();
			case InteractionPackage.TRANSITION__DESCRIPTION:
				return getDescription();
			case InteractionPackage.TRANSITION__EVENT:
				if (resolve) return getEvent();
				return basicGetEvent();
			case InteractionPackage.TRANSITION__CONDITION:
				return getCondition();
			case InteractionPackage.TRANSITION__ACTIONS:
				return getActions();
			case InteractionPackage.TRANSITION__NAME:
				return getName();
			case InteractionPackage.TRANSITION__HID:
				return getHid();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InteractionPackage.TRANSITION__INPUT_STATE:
				setInputState((State)newValue);
				return;
			case InteractionPackage.TRANSITION__OUTPUT_STATE:
				setOutputState((State)newValue);
				return;
			case InteractionPackage.TRANSITION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case InteractionPackage.TRANSITION__EVENT:
				setEvent((Event)newValue);
				return;
			case InteractionPackage.TRANSITION__CONDITION:
				setCondition((String)newValue);
				return;
			case InteractionPackage.TRANSITION__ACTIONS:
				setActions((String)newValue);
				return;
			case InteractionPackage.TRANSITION__NAME:
				setName((String)newValue);
				return;
			case InteractionPackage.TRANSITION__HID:
				setHid((Integer)newValue);
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
			case InteractionPackage.TRANSITION__INPUT_STATE:
				setInputState((State)null);
				return;
			case InteractionPackage.TRANSITION__OUTPUT_STATE:
				setOutputState((State)null);
				return;
			case InteractionPackage.TRANSITION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case InteractionPackage.TRANSITION__EVENT:
				setEvent((Event)null);
				return;
			case InteractionPackage.TRANSITION__CONDITION:
				setCondition(CONDITION_EDEFAULT);
				return;
			case InteractionPackage.TRANSITION__ACTIONS:
				setActions(ACTIONS_EDEFAULT);
				return;
			case InteractionPackage.TRANSITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case InteractionPackage.TRANSITION__HID:
				setHid(HID_EDEFAULT);
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
			case InteractionPackage.TRANSITION__INPUT_STATE:
				return inputState != null;
			case InteractionPackage.TRANSITION__OUTPUT_STATE:
				return outputState != null;
			case InteractionPackage.TRANSITION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case InteractionPackage.TRANSITION__EVENT:
				return event != null;
			case InteractionPackage.TRANSITION__CONDITION:
				return CONDITION_EDEFAULT == null ? condition != null : !CONDITION_EDEFAULT.equals(condition);
			case InteractionPackage.TRANSITION__ACTIONS:
				return ACTIONS_EDEFAULT == null ? actions != null : !ACTIONS_EDEFAULT.equals(actions);
			case InteractionPackage.TRANSITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case InteractionPackage.TRANSITION__HID:
				return hid != HID_EDEFAULT;
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
		result.append(" (description: ");
		result.append(description);
		result.append(", condition: ");
		result.append(condition);
		result.append(", actions: ");
		result.append(actions);
		result.append(", name: ");
		result.append(name);
		result.append(", hid: ");
		result.append(hid);
		result.append(')');
		return result.toString();
	}

} //TransitionImpl

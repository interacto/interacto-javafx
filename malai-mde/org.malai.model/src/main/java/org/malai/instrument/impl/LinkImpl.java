/**
 */
package org.malai.instrument.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.malai.action.Action;

import org.malai.instrument.Instrument;
import org.malai.instrument.InstrumentPackage;
import org.malai.instrument.Link;

import org.malai.interaction.Interaction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getInteraction <em>Interaction</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getAction <em>Action</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getInterimFeedback <em>Interim Feedback</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getIsConditionRespected <em>Is Condition Respected</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getUpdateAction <em>Update Action</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#isExecuteOnUpdate <em>Execute On Update</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getInitialiseAction <em>Initialise Action</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.malai.instrument.impl.LinkImpl#getInstrument <em>Instrument</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinkImpl extends EObjectImpl implements Link {
	/**
	 * The cached value of the '{@link #getInteraction() <em>Interaction</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInteraction()
	 * @generated
	 * @ordered
	 */
	protected Interaction interaction;

	/**
	 * The cached value of the '{@link #getAction() <em>Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected Action action;

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
	 * The default value of the '{@link #getInterimFeedback() <em>Interim Feedback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterimFeedback()
	 * @generated
	 * @ordered
	 */
	protected static final String INTERIM_FEEDBACK_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInterimFeedback() <em>Interim Feedback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterimFeedback()
	 * @generated
	 * @ordered
	 */
	protected String interimFeedback = INTERIM_FEEDBACK_EDEFAULT;

	/**
	 * The default value of the '{@link #getIsConditionRespected() <em>Is Condition Respected</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsConditionRespected()
	 * @generated
	 * @ordered
	 */
	protected static final String IS_CONDITION_RESPECTED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsConditionRespected() <em>Is Condition Respected</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsConditionRespected()
	 * @generated
	 * @ordered
	 */
	protected String isConditionRespected = IS_CONDITION_RESPECTED_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpdateAction() <em>Update Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdateAction()
	 * @generated
	 * @ordered
	 */
	protected static final String UPDATE_ACTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpdateAction() <em>Update Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdateAction()
	 * @generated
	 * @ordered
	 */
	protected String updateAction = UPDATE_ACTION_EDEFAULT;

	/**
	 * The default value of the '{@link #isExecuteOnUpdate() <em>Execute On Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExecuteOnUpdate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXECUTE_ON_UPDATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExecuteOnUpdate() <em>Execute On Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExecuteOnUpdate()
	 * @generated
	 * @ordered
	 */
	protected boolean executeOnUpdate = EXECUTE_ON_UPDATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitialiseAction() <em>Initialise Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialiseAction()
	 * @generated
	 * @ordered
	 */
	protected static final String INITIALISE_ACTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitialiseAction() <em>Initialise Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialiseAction()
	 * @generated
	 * @ordered
	 */
	protected String initialiseAction = INITIALISE_ACTION_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InstrumentPackage.Literals.LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interaction getInteraction() {
		if (interaction != null && interaction.eIsProxy()) {
			InternalEObject oldInteraction = (InternalEObject)interaction;
			interaction = (Interaction)eResolveProxy(oldInteraction);
			if (interaction != oldInteraction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InstrumentPackage.LINK__INTERACTION, oldInteraction, interaction));
			}
		}
		return interaction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interaction basicGetInteraction() {
		return interaction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInteraction(Interaction newInteraction) {
		Interaction oldInteraction = interaction;
		interaction = newInteraction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__INTERACTION, oldInteraction, interaction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action getAction() {
		if (action != null && action.eIsProxy()) {
			InternalEObject oldAction = (InternalEObject)action;
			action = (Action)eResolveProxy(oldAction);
			if (action != oldAction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InstrumentPackage.LINK__ACTION, oldAction, action));
			}
		}
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action basicGetAction() {
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAction(Action newAction) {
		Action oldAction = action;
		action = newAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__ACTION, oldAction, action));
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
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInterimFeedback() {
		return interimFeedback;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterimFeedback(String newInterimFeedback) {
		String oldInterimFeedback = interimFeedback;
		interimFeedback = newInterimFeedback;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__INTERIM_FEEDBACK, oldInterimFeedback, interimFeedback));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIsConditionRespected() {
		return isConditionRespected;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsConditionRespected(String newIsConditionRespected) {
		String oldIsConditionRespected = isConditionRespected;
		isConditionRespected = newIsConditionRespected;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__IS_CONDITION_RESPECTED, oldIsConditionRespected, isConditionRespected));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUpdateAction() {
		return updateAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpdateAction(String newUpdateAction) {
		String oldUpdateAction = updateAction;
		updateAction = newUpdateAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__UPDATE_ACTION, oldUpdateAction, updateAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExecuteOnUpdate() {
		return executeOnUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecuteOnUpdate(boolean newExecuteOnUpdate) {
		boolean oldExecuteOnUpdate = executeOnUpdate;
		executeOnUpdate = newExecuteOnUpdate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__EXECUTE_ON_UPDATE, oldExecuteOnUpdate, executeOnUpdate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInitialiseAction() {
		return initialiseAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialiseAction(String newInitialiseAction) {
		String oldInitialiseAction = initialiseAction;
		initialiseAction = newInitialiseAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__INITIALISE_ACTION, oldInitialiseAction, initialiseAction));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__CLAZZ, oldClazz, newClazz);
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
				msgs = ((InternalEObject)clazz).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InstrumentPackage.LINK__CLAZZ, null, msgs);
			if (newClazz != null)
				msgs = ((InternalEObject)newClazz).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InstrumentPackage.LINK__CLAZZ, null, msgs);
			msgs = basicSetClazz(newClazz, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__CLAZZ, newClazz, newClazz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Instrument getInstrument() {
		if (eContainerFeatureID() != InstrumentPackage.LINK__INSTRUMENT) return null;
		return (Instrument)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInstrument(Instrument newInstrument, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newInstrument, InstrumentPackage.LINK__INSTRUMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstrument(Instrument newInstrument) {
		if (newInstrument != eInternalContainer() || (eContainerFeatureID() != InstrumentPackage.LINK__INSTRUMENT && newInstrument != null)) {
			if (EcoreUtil.isAncestor(this, newInstrument))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newInstrument != null)
				msgs = ((InternalEObject)newInstrument).eInverseAdd(this, InstrumentPackage.INSTRUMENT__LINKS, Instrument.class, msgs);
			msgs = basicSetInstrument(newInstrument, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.LINK__INSTRUMENT, newInstrument, newInstrument));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InstrumentPackage.LINK__INSTRUMENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetInstrument((Instrument)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InstrumentPackage.LINK__CLAZZ:
				return basicSetClazz(null, msgs);
			case InstrumentPackage.LINK__INSTRUMENT:
				return basicSetInstrument(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case InstrumentPackage.LINK__INSTRUMENT:
				return eInternalContainer().eInverseRemove(this, InstrumentPackage.INSTRUMENT__LINKS, Instrument.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InstrumentPackage.LINK__INTERACTION:
				if (resolve) return getInteraction();
				return basicGetInteraction();
			case InstrumentPackage.LINK__ACTION:
				if (resolve) return getAction();
				return basicGetAction();
			case InstrumentPackage.LINK__DESCRIPTION:
				return getDescription();
			case InstrumentPackage.LINK__INTERIM_FEEDBACK:
				return getInterimFeedback();
			case InstrumentPackage.LINK__IS_CONDITION_RESPECTED:
				return getIsConditionRespected();
			case InstrumentPackage.LINK__UPDATE_ACTION:
				return getUpdateAction();
			case InstrumentPackage.LINK__EXECUTE_ON_UPDATE:
				return isExecuteOnUpdate();
			case InstrumentPackage.LINK__INITIALISE_ACTION:
				return getInitialiseAction();
			case InstrumentPackage.LINK__CLAZZ:
				return getClazz();
			case InstrumentPackage.LINK__INSTRUMENT:
				return getInstrument();
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
			case InstrumentPackage.LINK__INTERACTION:
				setInteraction((Interaction)newValue);
				return;
			case InstrumentPackage.LINK__ACTION:
				setAction((Action)newValue);
				return;
			case InstrumentPackage.LINK__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case InstrumentPackage.LINK__INTERIM_FEEDBACK:
				setInterimFeedback((String)newValue);
				return;
			case InstrumentPackage.LINK__IS_CONDITION_RESPECTED:
				setIsConditionRespected((String)newValue);
				return;
			case InstrumentPackage.LINK__UPDATE_ACTION:
				setUpdateAction((String)newValue);
				return;
			case InstrumentPackage.LINK__EXECUTE_ON_UPDATE:
				setExecuteOnUpdate((Boolean)newValue);
				return;
			case InstrumentPackage.LINK__INITIALISE_ACTION:
				setInitialiseAction((String)newValue);
				return;
			case InstrumentPackage.LINK__CLAZZ:
				setClazz((EClass)newValue);
				return;
			case InstrumentPackage.LINK__INSTRUMENT:
				setInstrument((Instrument)newValue);
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
			case InstrumentPackage.LINK__INTERACTION:
				setInteraction((Interaction)null);
				return;
			case InstrumentPackage.LINK__ACTION:
				setAction((Action)null);
				return;
			case InstrumentPackage.LINK__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case InstrumentPackage.LINK__INTERIM_FEEDBACK:
				setInterimFeedback(INTERIM_FEEDBACK_EDEFAULT);
				return;
			case InstrumentPackage.LINK__IS_CONDITION_RESPECTED:
				setIsConditionRespected(IS_CONDITION_RESPECTED_EDEFAULT);
				return;
			case InstrumentPackage.LINK__UPDATE_ACTION:
				setUpdateAction(UPDATE_ACTION_EDEFAULT);
				return;
			case InstrumentPackage.LINK__EXECUTE_ON_UPDATE:
				setExecuteOnUpdate(EXECUTE_ON_UPDATE_EDEFAULT);
				return;
			case InstrumentPackage.LINK__INITIALISE_ACTION:
				setInitialiseAction(INITIALISE_ACTION_EDEFAULT);
				return;
			case InstrumentPackage.LINK__CLAZZ:
				setClazz((EClass)null);
				return;
			case InstrumentPackage.LINK__INSTRUMENT:
				setInstrument((Instrument)null);
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
			case InstrumentPackage.LINK__INTERACTION:
				return interaction != null;
			case InstrumentPackage.LINK__ACTION:
				return action != null;
			case InstrumentPackage.LINK__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case InstrumentPackage.LINK__INTERIM_FEEDBACK:
				return INTERIM_FEEDBACK_EDEFAULT == null ? interimFeedback != null : !INTERIM_FEEDBACK_EDEFAULT.equals(interimFeedback);
			case InstrumentPackage.LINK__IS_CONDITION_RESPECTED:
				return IS_CONDITION_RESPECTED_EDEFAULT == null ? isConditionRespected != null : !IS_CONDITION_RESPECTED_EDEFAULT.equals(isConditionRespected);
			case InstrumentPackage.LINK__UPDATE_ACTION:
				return UPDATE_ACTION_EDEFAULT == null ? updateAction != null : !UPDATE_ACTION_EDEFAULT.equals(updateAction);
			case InstrumentPackage.LINK__EXECUTE_ON_UPDATE:
				return executeOnUpdate != EXECUTE_ON_UPDATE_EDEFAULT;
			case InstrumentPackage.LINK__INITIALISE_ACTION:
				return INITIALISE_ACTION_EDEFAULT == null ? initialiseAction != null : !INITIALISE_ACTION_EDEFAULT.equals(initialiseAction);
			case InstrumentPackage.LINK__CLAZZ:
				return clazz != null;
			case InstrumentPackage.LINK__INSTRUMENT:
				return getInstrument() != null;
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
		result.append(", interimFeedback: ");
		result.append(interimFeedback);
		result.append(", isConditionRespected: ");
		result.append(isConditionRespected);
		result.append(", updateAction: ");
		result.append(updateAction);
		result.append(", executeOnUpdate: ");
		result.append(executeOnUpdate);
		result.append(", initialiseAction: ");
		result.append(initialiseAction);
		result.append(')');
		return result.toString();
	}

} //LinkImpl

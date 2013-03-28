/**
 */
package org.malai.instrument.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.malai.instrument.Instrument;
import org.malai.instrument.InstrumentPackage;
import org.malai.instrument.Link;
import org.malai.widget.Widget;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instrument</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getLinks <em>Links</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getHelpers <em>Helpers</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getDateCreation <em>Date Creation</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getInterimFeedback <em>Interim Feedback</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#isInitiallyActivated <em>Initially Activated</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getProvidedWidgets <em>Provided Widgets</em>}</li>
 *   <li>{@link org.malai.instrument.impl.InstrumentImpl#getSubscribedWidgets <em>Subscribed Widgets</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstrumentImpl extends EObjectImpl implements Instrument {
	/**
	 * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<Link> links;

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
	 * The cached value of the '{@link #getHelpers() <em>Helpers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpers()
	 * @generated
	 * @ordered
	 */
	protected EList<EClassifier> helpers;

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
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDateCreation() <em>Date Creation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateCreation()
	 * @generated
	 * @ordered
	 */
	protected static final String DATE_CREATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDateCreation() <em>Date Creation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateCreation()
	 * @generated
	 * @ordered
	 */
	protected String dateCreation = DATE_CREATION_EDEFAULT;

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
	 * The default value of the '{@link #isInitiallyActivated() <em>Initially Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitiallyActivated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INITIALLY_ACTIVATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInitiallyActivated() <em>Initially Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitiallyActivated()
	 * @generated
	 * @ordered
	 */
	protected boolean initiallyActivated = INITIALLY_ACTIVATED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProvidedWidgets() <em>Provided Widgets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvidedWidgets()
	 * @generated
	 * @ordered
	 */
	protected EList<Widget> providedWidgets;

	/**
	 * The cached value of the '{@link #getSubscribedWidgets() <em>Subscribed Widgets</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubscribedWidgets()
	 * @generated
	 * @ordered
	 */
	protected EList<Widget> subscribedWidgets;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstrumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InstrumentPackage.Literals.INSTRUMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Link> getLinks() {
		if (links == null) {
			links = new EObjectContainmentWithInverseEList<Link>(Link.class, this, InstrumentPackage.INSTRUMENT__LINKS, InstrumentPackage.LINK__INSTRUMENT);
		}
		return links;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InstrumentPackage.INSTRUMENT__CLAZZ, oldClazz, newClazz);
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
				msgs = ((InternalEObject)clazz).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InstrumentPackage.INSTRUMENT__CLAZZ, null, msgs);
			if (newClazz != null)
				msgs = ((InternalEObject)newClazz).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InstrumentPackage.INSTRUMENT__CLAZZ, null, msgs);
			msgs = basicSetClazz(newClazz, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.INSTRUMENT__CLAZZ, newClazz, newClazz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EClassifier> getHelpers() {
		if (helpers == null) {
			helpers = new EObjectContainmentEList<EClassifier>(EClassifier.class, this, InstrumentPackage.INSTRUMENT__HELPERS);
		}
		return helpers;
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
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.INSTRUMENT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.INSTRUMENT__AUTHOR, oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.INSTRUMENT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDateCreation() {
		return dateCreation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateCreation(String newDateCreation) {
		String oldDateCreation = dateCreation;
		dateCreation = newDateCreation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.INSTRUMENT__DATE_CREATION, oldDateCreation, dateCreation));
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
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.INSTRUMENT__INTERIM_FEEDBACK, oldInterimFeedback, interimFeedback));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInitiallyActivated() {
		return initiallyActivated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitiallyActivated(boolean newInitiallyActivated) {
		boolean oldInitiallyActivated = initiallyActivated;
		initiallyActivated = newInitiallyActivated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstrumentPackage.INSTRUMENT__INITIALLY_ACTIVATED, oldInitiallyActivated, initiallyActivated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Widget> getProvidedWidgets() {
		if (providedWidgets == null) {
			providedWidgets = new EObjectContainmentEList<Widget>(Widget.class, this, InstrumentPackage.INSTRUMENT__PROVIDED_WIDGETS);
		}
		return providedWidgets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Widget> getSubscribedWidgets() {
		if (subscribedWidgets == null) {
			subscribedWidgets = new EObjectResolvingEList<Widget>(Widget.class, this, InstrumentPackage.INSTRUMENT__SUBSCRIBED_WIDGETS);
		}
		return subscribedWidgets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InstrumentPackage.INSTRUMENT__LINKS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLinks()).basicAdd(otherEnd, msgs);
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
			case InstrumentPackage.INSTRUMENT__LINKS:
				return ((InternalEList<?>)getLinks()).basicRemove(otherEnd, msgs);
			case InstrumentPackage.INSTRUMENT__CLAZZ:
				return basicSetClazz(null, msgs);
			case InstrumentPackage.INSTRUMENT__HELPERS:
				return ((InternalEList<?>)getHelpers()).basicRemove(otherEnd, msgs);
			case InstrumentPackage.INSTRUMENT__PROVIDED_WIDGETS:
				return ((InternalEList<?>)getProvidedWidgets()).basicRemove(otherEnd, msgs);
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
			case InstrumentPackage.INSTRUMENT__LINKS:
				return getLinks();
			case InstrumentPackage.INSTRUMENT__CLAZZ:
				return getClazz();
			case InstrumentPackage.INSTRUMENT__HELPERS:
				return getHelpers();
			case InstrumentPackage.INSTRUMENT__DESCRIPTION:
				return getDescription();
			case InstrumentPackage.INSTRUMENT__AUTHOR:
				return getAuthor();
			case InstrumentPackage.INSTRUMENT__VERSION:
				return getVersion();
			case InstrumentPackage.INSTRUMENT__DATE_CREATION:
				return getDateCreation();
			case InstrumentPackage.INSTRUMENT__INTERIM_FEEDBACK:
				return getInterimFeedback();
			case InstrumentPackage.INSTRUMENT__INITIALLY_ACTIVATED:
				return isInitiallyActivated();
			case InstrumentPackage.INSTRUMENT__PROVIDED_WIDGETS:
				return getProvidedWidgets();
			case InstrumentPackage.INSTRUMENT__SUBSCRIBED_WIDGETS:
				return getSubscribedWidgets();
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
			case InstrumentPackage.INSTRUMENT__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends Link>)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__CLAZZ:
				setClazz((EClass)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__HELPERS:
				getHelpers().clear();
				getHelpers().addAll((Collection<? extends EClassifier>)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__AUTHOR:
				setAuthor((String)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__VERSION:
				setVersion((String)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__DATE_CREATION:
				setDateCreation((String)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__INTERIM_FEEDBACK:
				setInterimFeedback((String)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__INITIALLY_ACTIVATED:
				setInitiallyActivated((Boolean)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__PROVIDED_WIDGETS:
				getProvidedWidgets().clear();
				getProvidedWidgets().addAll((Collection<? extends Widget>)newValue);
				return;
			case InstrumentPackage.INSTRUMENT__SUBSCRIBED_WIDGETS:
				getSubscribedWidgets().clear();
				getSubscribedWidgets().addAll((Collection<? extends Widget>)newValue);
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
			case InstrumentPackage.INSTRUMENT__LINKS:
				getLinks().clear();
				return;
			case InstrumentPackage.INSTRUMENT__CLAZZ:
				setClazz((EClass)null);
				return;
			case InstrumentPackage.INSTRUMENT__HELPERS:
				getHelpers().clear();
				return;
			case InstrumentPackage.INSTRUMENT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case InstrumentPackage.INSTRUMENT__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case InstrumentPackage.INSTRUMENT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case InstrumentPackage.INSTRUMENT__DATE_CREATION:
				setDateCreation(DATE_CREATION_EDEFAULT);
				return;
			case InstrumentPackage.INSTRUMENT__INTERIM_FEEDBACK:
				setInterimFeedback(INTERIM_FEEDBACK_EDEFAULT);
				return;
			case InstrumentPackage.INSTRUMENT__INITIALLY_ACTIVATED:
				setInitiallyActivated(INITIALLY_ACTIVATED_EDEFAULT);
				return;
			case InstrumentPackage.INSTRUMENT__PROVIDED_WIDGETS:
				getProvidedWidgets().clear();
				return;
			case InstrumentPackage.INSTRUMENT__SUBSCRIBED_WIDGETS:
				getSubscribedWidgets().clear();
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
			case InstrumentPackage.INSTRUMENT__LINKS:
				return links != null && !links.isEmpty();
			case InstrumentPackage.INSTRUMENT__CLAZZ:
				return clazz != null;
			case InstrumentPackage.INSTRUMENT__HELPERS:
				return helpers != null && !helpers.isEmpty();
			case InstrumentPackage.INSTRUMENT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case InstrumentPackage.INSTRUMENT__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case InstrumentPackage.INSTRUMENT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case InstrumentPackage.INSTRUMENT__DATE_CREATION:
				return DATE_CREATION_EDEFAULT == null ? dateCreation != null : !DATE_CREATION_EDEFAULT.equals(dateCreation);
			case InstrumentPackage.INSTRUMENT__INTERIM_FEEDBACK:
				return INTERIM_FEEDBACK_EDEFAULT == null ? interimFeedback != null : !INTERIM_FEEDBACK_EDEFAULT.equals(interimFeedback);
			case InstrumentPackage.INSTRUMENT__INITIALLY_ACTIVATED:
				return initiallyActivated != INITIALLY_ACTIVATED_EDEFAULT;
			case InstrumentPackage.INSTRUMENT__PROVIDED_WIDGETS:
				return providedWidgets != null && !providedWidgets.isEmpty();
			case InstrumentPackage.INSTRUMENT__SUBSCRIBED_WIDGETS:
				return subscribedWidgets != null && !subscribedWidgets.isEmpty();
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
		result.append(", author: ");
		result.append(author);
		result.append(", version: ");
		result.append(version);
		result.append(", dateCreation: ");
		result.append(dateCreation);
		result.append(", interimFeedback: ");
		result.append(interimFeedback);
		result.append(", initiallyActivated: ");
		result.append(initiallyActivated);
		result.append(')');
		return result.toString();
	}

} //InstrumentImpl

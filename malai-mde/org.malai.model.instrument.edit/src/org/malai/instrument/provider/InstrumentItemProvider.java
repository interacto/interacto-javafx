/**
 */
package org.malai.instrument.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.malai.instrument.Instrument;
import org.malai.instrument.InstrumentFactory;
import org.malai.instrument.InstrumentPackage;
import org.malai.widget.WidgetFactory;

/**
 * This is the item provider adapter for a {@link org.malai.instrument.Instrument} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class InstrumentItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstrumentItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addDescriptionPropertyDescriptor(object);
			addAuthorPropertyDescriptor(object);
			addVersionPropertyDescriptor(object);
			addDateCreationPropertyDescriptor(object);
			addInterimFeedbackPropertyDescriptor(object);
			addInitiallyActivatedPropertyDescriptor(object);
			addSubscribedWidgetsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Instrument_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Instrument_description_feature", "_UI_Instrument_type"),
				 InstrumentPackage.Literals.INSTRUMENT__DESCRIPTION,
				 true,
				 true,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Author feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAuthorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Instrument_author_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Instrument_author_feature", "_UI_Instrument_type"),
				 InstrumentPackage.Literals.INSTRUMENT__AUTHOR,
				 true,
				 true,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Instrument_version_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Instrument_version_feature", "_UI_Instrument_type"),
				 InstrumentPackage.Literals.INSTRUMENT__VERSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Date Creation feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDateCreationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Instrument_dateCreation_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Instrument_dateCreation_feature", "_UI_Instrument_type"),
				 InstrumentPackage.Literals.INSTRUMENT__DATE_CREATION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Interim Feedback feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInterimFeedbackPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Instrument_interimFeedback_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Instrument_interimFeedback_feature", "_UI_Instrument_type"),
				 InstrumentPackage.Literals.INSTRUMENT__INTERIM_FEEDBACK,
				 true,
				 true,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Initially Activated feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInitiallyActivatedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Instrument_initiallyActivated_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Instrument_initiallyActivated_feature", "_UI_Instrument_type"),
				 InstrumentPackage.Literals.INSTRUMENT__INITIALLY_ACTIVATED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Subscribed Widgets feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSubscribedWidgetsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Instrument_subscribedWidgets_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Instrument_subscribedWidgets_feature", "_UI_Instrument_type"),
				 InstrumentPackage.Literals.INSTRUMENT__SUBSCRIBED_WIDGETS,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(InstrumentPackage.Literals.INSTRUMENT__LINKS);
			childrenFeatures.add(InstrumentPackage.Literals.INSTRUMENT__CLAZZ);
			childrenFeatures.add(InstrumentPackage.Literals.INSTRUMENT__HELPERS);
			childrenFeatures.add(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns Instrument.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Instrument"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@Override
	public String getText(Object object) {
		String label = "";
		
		if(object instanceof Instrument) {
			Instrument ins = (Instrument) object;
			EClass clazz = ins.getClazz();
			if(clazz!=null && clazz.getName()!=null)
				label = " " + clazz.getName();
			if(clazz!=null && clazz.isAbstract())
				label +=" (abstract)";
		}
		
		return getString("_UI_Instrument_type") + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Instrument.class)) {
			case InstrumentPackage.INSTRUMENT__DESCRIPTION:
			case InstrumentPackage.INSTRUMENT__AUTHOR:
			case InstrumentPackage.INSTRUMENT__VERSION:
			case InstrumentPackage.INSTRUMENT__DATE_CREATION:
			case InstrumentPackage.INSTRUMENT__INTERIM_FEEDBACK:
			case InstrumentPackage.INSTRUMENT__INITIALLY_ACTIVATED:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case InstrumentPackage.INSTRUMENT__LINKS:
			case InstrumentPackage.INSTRUMENT__CLAZZ:
			case InstrumentPackage.INSTRUMENT__HELPERS:
			case InstrumentPackage.INSTRUMENT__PROVIDED_WIDGETS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__LINKS,
				 InstrumentFactory.eINSTANCE.createLink()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__CLAZZ,
				 EcoreFactory.eINSTANCE.createEClass()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__HELPERS,
				 EcoreFactory.eINSTANCE.createEClass()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__HELPERS,
				 EcoreFactory.eINSTANCE.createEDataType()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__HELPERS,
				 EcoreFactory.eINSTANCE.createEEnum()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createTree()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createSlider()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createProgressBar()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createTable()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createLabel()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createTextArea()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createTextField()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createSpinner()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createMultiLineList()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createSingleLineList()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createPanel()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createWindow()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createDialogueBox()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createSplitPane()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createTabbedPanel()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createButton()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createToggleButton()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createMenu()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createCheckBox()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createRadioButton()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createToggleMenu()));

		newChildDescriptors.add
			(createChildParameter
				(InstrumentPackage.Literals.INSTRUMENT__PROVIDED_WIDGETS,
				 WidgetFactory.eINSTANCE.createRadioMenu()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == InstrumentPackage.Literals.INSTRUMENT__CLAZZ ||
			childFeature == InstrumentPackage.Literals.INSTRUMENT__HELPERS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return InstrumentEditPlugin.INSTANCE;
	}

}

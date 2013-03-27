/**
 */
package org.malai.widget.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.malai.widget.ContainerWidget;
import org.malai.widget.WidgetFactory;
import org.malai.widget.WidgetPackage;

/**
 * This is the item provider adapter for a {@link org.malai.widget.ContainerWidget} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ContainerWidgetItemProvider
	extends WidgetItemProvider
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
	public ContainerWidgetItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS);
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
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_ContainerWidget_type");
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

		switch (notification.getFeatureID(ContainerWidget.class)) {
			case WidgetPackage.CONTAINER_WIDGET__ELEMENTS:
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
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createTree()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createSlider()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createProgressBar()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createTable()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createLabel()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createTextArea()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createTextField()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createSpinner()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createMultiLineList()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createSingleLineList()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createPanel()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createWindow()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createDialogueBox()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createSplitPane()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createTabbedPanel()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createButton()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createToggleButton()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createMenu()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createCheckBox()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createRadioButton()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createToggleMenu()));

		newChildDescriptors.add
			(createChildParameter
				(WidgetPackage.Literals.CONTAINER_WIDGET__ELEMENTS,
				 WidgetFactory.eINSTANCE.createRadioMenu()));
	}

}

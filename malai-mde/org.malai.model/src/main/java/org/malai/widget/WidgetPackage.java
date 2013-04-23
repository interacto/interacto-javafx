/**
 */
package org.malai.widget;

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
 * @see org.malai.widget.WidgetFactory
 * @model kind="package"
 * @generated
 */
public interface WidgetPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "widget";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.malai.widget/1_0_0//org/malai/widget";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org_malai_widget";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WidgetPackage eINSTANCE = org.malai.widget.impl.WidgetPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.WidgetImpl <em>Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.WidgetImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getWidget()
	 * @generated
	 */
	int WIDGET = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__PARENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__NAME = 1;

	/**
	 * The number of structural features of the '<em>Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.ContainerWidgetImpl <em>Container Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.ContainerWidgetImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getContainerWidget()
	 * @generated
	 */
	int CONTAINER_WIDGET = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_WIDGET__PARENT = WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_WIDGET__NAME = WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_WIDGET__ELEMENTS = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Container Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_WIDGET_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.ListWidgetImpl <em>List Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.ListWidgetImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getListWidget()
	 * @generated
	 */
	int LIST_WIDGET = 2;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_WIDGET__PARENT = WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_WIDGET__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>List Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_WIDGET_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.ButtonWidgetImpl <em>Button Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.ButtonWidgetImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getButtonWidget()
	 * @generated
	 */
	int BUTTON_WIDGET = 3;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUTTON_WIDGET__PARENT = WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUTTON_WIDGET__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Button Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUTTON_WIDGET_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.TextWidgetImpl <em>Text Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.TextWidgetImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getTextWidget()
	 * @generated
	 */
	int TEXT_WIDGET = 4;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_WIDGET__PARENT = WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_WIDGET__NAME = WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_WIDGET__TEXT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_WIDGET_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.TreeImpl <em>Tree</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.TreeImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getTree()
	 * @generated
	 */
	int TREE = 5;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREE__PARENT = WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREE__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Tree</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREE_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.SliderImpl <em>Slider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.SliderImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getSlider()
	 * @generated
	 */
	int SLIDER = 6;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLIDER__PARENT = WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLIDER__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Slider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLIDER_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.ProgressBarImpl <em>Progress Bar</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.ProgressBarImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getProgressBar()
	 * @generated
	 */
	int PROGRESS_BAR = 7;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR__PARENT = WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Progress Bar</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROGRESS_BAR_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.TableImpl <em>Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.TableImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getTable()
	 * @generated
	 */
	int TABLE = 8;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__PARENT = WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.LabelImpl <em>Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.LabelImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getLabel()
	 * @generated
	 */
	int LABEL = 9;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__PARENT = TEXT_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__NAME = TEXT_WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__TEXT = TEXT_WIDGET__TEXT;

	/**
	 * The number of structural features of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_FEATURE_COUNT = TEXT_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.TextAreaImpl <em>Text Area</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.TextAreaImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getTextArea()
	 * @generated
	 */
	int TEXT_AREA = 10;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA__PARENT = TEXT_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA__NAME = TEXT_WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA__TEXT = TEXT_WIDGET__TEXT;

	/**
	 * The number of structural features of the '<em>Text Area</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_AREA_FEATURE_COUNT = TEXT_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.TextFieldImpl <em>Text Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.TextFieldImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getTextField()
	 * @generated
	 */
	int TEXT_FIELD = 11;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__PARENT = TEXT_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__NAME = TEXT_WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD__TEXT = TEXT_WIDGET__TEXT;

	/**
	 * The number of structural features of the '<em>Text Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_FEATURE_COUNT = TEXT_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.SpinnerImpl <em>Spinner</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.SpinnerImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getSpinner()
	 * @generated
	 */
	int SPINNER = 12;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPINNER__PARENT = TEXT_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPINNER__NAME = TEXT_WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPINNER__TEXT = TEXT_WIDGET__TEXT;

	/**
	 * The number of structural features of the '<em>Spinner</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPINNER_FEATURE_COUNT = TEXT_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.MultiLineListImpl <em>Multi Line List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.MultiLineListImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getMultiLineList()
	 * @generated
	 */
	int MULTI_LINE_LIST = 13;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_LINE_LIST__PARENT = LIST_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_LINE_LIST__NAME = LIST_WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Multi Line List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_LINE_LIST_FEATURE_COUNT = LIST_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.SingleLineListImpl <em>Single Line List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.SingleLineListImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getSingleLineList()
	 * @generated
	 */
	int SINGLE_LINE_LIST = 14;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_LINE_LIST__PARENT = LIST_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_LINE_LIST__NAME = LIST_WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Single Line List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_LINE_LIST_FEATURE_COUNT = LIST_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.PanelImpl <em>Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.PanelImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getPanel()
	 * @generated
	 */
	int PANEL = 15;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__PARENT = CONTAINER_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__NAME = CONTAINER_WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL__ELEMENTS = CONTAINER_WIDGET__ELEMENTS;

	/**
	 * The number of structural features of the '<em>Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PANEL_FEATURE_COUNT = CONTAINER_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.WindowImpl <em>Window</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.WindowImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getWindow()
	 * @generated
	 */
	int WINDOW = 16;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__PARENT = CONTAINER_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__NAME = CONTAINER_WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__ELEMENTS = CONTAINER_WIDGET__ELEMENTS;

	/**
	 * The number of structural features of the '<em>Window</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_FEATURE_COUNT = CONTAINER_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.DialogueBoxImpl <em>Dialogue Box</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.DialogueBoxImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getDialogueBox()
	 * @generated
	 */
	int DIALOGUE_BOX = 17;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIALOGUE_BOX__PARENT = WINDOW__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIALOGUE_BOX__NAME = WINDOW__NAME;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIALOGUE_BOX__ELEMENTS = WINDOW__ELEMENTS;

	/**
	 * The number of structural features of the '<em>Dialogue Box</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIALOGUE_BOX_FEATURE_COUNT = WINDOW_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.SplitPaneImpl <em>Split Pane</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.SplitPaneImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getSplitPane()
	 * @generated
	 */
	int SPLIT_PANE = 18;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPLIT_PANE__PARENT = CONTAINER_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPLIT_PANE__NAME = CONTAINER_WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPLIT_PANE__ELEMENTS = CONTAINER_WIDGET__ELEMENTS;

	/**
	 * The number of structural features of the '<em>Split Pane</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPLIT_PANE_FEATURE_COUNT = CONTAINER_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.TabbedPanelImpl <em>Tabbed Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.TabbedPanelImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getTabbedPanel()
	 * @generated
	 */
	int TABBED_PANEL = 19;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABBED_PANEL__PARENT = CONTAINER_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABBED_PANEL__NAME = CONTAINER_WIDGET__NAME;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABBED_PANEL__ELEMENTS = CONTAINER_WIDGET__ELEMENTS;

	/**
	 * The number of structural features of the '<em>Tabbed Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABBED_PANEL_FEATURE_COUNT = CONTAINER_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.ButtonImpl <em>Button</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.ButtonImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getButton()
	 * @generated
	 */
	int BUTTON = 20;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUTTON__PARENT = BUTTON_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUTTON__NAME = BUTTON_WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Button</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUTTON_FEATURE_COUNT = BUTTON_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.ToggleButtonImpl <em>Toggle Button</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.ToggleButtonImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getToggleButton()
	 * @generated
	 */
	int TOGGLE_BUTTON = 21;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOGGLE_BUTTON__PARENT = BUTTON__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOGGLE_BUTTON__NAME = BUTTON__NAME;

	/**
	 * The number of structural features of the '<em>Toggle Button</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOGGLE_BUTTON_FEATURE_COUNT = BUTTON_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.MenuImpl <em>Menu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.MenuImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getMenu()
	 * @generated
	 */
	int MENU = 22;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__PARENT = BUTTON_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU__NAME = BUTTON_WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MENU_FEATURE_COUNT = BUTTON_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.CheckBoxImpl <em>Check Box</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.CheckBoxImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getCheckBox()
	 * @generated
	 */
	int CHECK_BOX = 23;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECK_BOX__PARENT = BUTTON_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECK_BOX__NAME = BUTTON_WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Check Box</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECK_BOX_FEATURE_COUNT = BUTTON_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.RadioButtonImpl <em>Radio Button</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.RadioButtonImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getRadioButton()
	 * @generated
	 */
	int RADIO_BUTTON = 24;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_BUTTON__PARENT = BUTTON_WIDGET__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_BUTTON__NAME = BUTTON_WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Radio Button</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_BUTTON_FEATURE_COUNT = BUTTON_WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.ToggleMenuImpl <em>Toggle Menu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.ToggleMenuImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getToggleMenu()
	 * @generated
	 */
	int TOGGLE_MENU = 25;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOGGLE_MENU__PARENT = MENU__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOGGLE_MENU__NAME = MENU__NAME;

	/**
	 * The number of structural features of the '<em>Toggle Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOGGLE_MENU_FEATURE_COUNT = MENU_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.malai.widget.impl.RadioMenuImpl <em>Radio Menu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.malai.widget.impl.RadioMenuImpl
	 * @see org.malai.widget.impl.WidgetPackageImpl#getRadioMenu()
	 * @generated
	 */
	int RADIO_MENU = 26;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_MENU__PARENT = MENU__PARENT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_MENU__NAME = MENU__NAME;

	/**
	 * The number of structural features of the '<em>Radio Menu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_MENU_FEATURE_COUNT = MENU_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.malai.widget.Widget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget</em>'.
	 * @see org.malai.widget.Widget
	 * @generated
	 */
	EClass getWidget();

	/**
	 * Returns the meta object for the container reference '{@link org.malai.widget.Widget#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see org.malai.widget.Widget#getParent()
	 * @see #getWidget()
	 * @generated
	 */
	EReference getWidget_Parent();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.widget.Widget#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.malai.widget.Widget#getName()
	 * @see #getWidget()
	 * @generated
	 */
	EAttribute getWidget_Name();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.ContainerWidget <em>Container Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container Widget</em>'.
	 * @see org.malai.widget.ContainerWidget
	 * @generated
	 */
	EClass getContainerWidget();

	/**
	 * Returns the meta object for the containment reference list '{@link org.malai.widget.ContainerWidget#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.malai.widget.ContainerWidget#getElements()
	 * @see #getContainerWidget()
	 * @generated
	 */
	EReference getContainerWidget_Elements();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.ListWidget <em>List Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Widget</em>'.
	 * @see org.malai.widget.ListWidget
	 * @generated
	 */
	EClass getListWidget();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.ButtonWidget <em>Button Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Button Widget</em>'.
	 * @see org.malai.widget.ButtonWidget
	 * @generated
	 */
	EClass getButtonWidget();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.TextWidget <em>Text Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Widget</em>'.
	 * @see org.malai.widget.TextWidget
	 * @generated
	 */
	EClass getTextWidget();

	/**
	 * Returns the meta object for the attribute '{@link org.malai.widget.TextWidget#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.malai.widget.TextWidget#getText()
	 * @see #getTextWidget()
	 * @generated
	 */
	EAttribute getTextWidget_Text();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Tree <em>Tree</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tree</em>'.
	 * @see org.malai.widget.Tree
	 * @generated
	 */
	EClass getTree();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Slider <em>Slider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Slider</em>'.
	 * @see org.malai.widget.Slider
	 * @generated
	 */
	EClass getSlider();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.ProgressBar <em>Progress Bar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Progress Bar</em>'.
	 * @see org.malai.widget.ProgressBar
	 * @generated
	 */
	EClass getProgressBar();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Table <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table</em>'.
	 * @see org.malai.widget.Table
	 * @generated
	 */
	EClass getTable();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Label <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label</em>'.
	 * @see org.malai.widget.Label
	 * @generated
	 */
	EClass getLabel();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.TextArea <em>Text Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Area</em>'.
	 * @see org.malai.widget.TextArea
	 * @generated
	 */
	EClass getTextArea();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.TextField <em>Text Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Field</em>'.
	 * @see org.malai.widget.TextField
	 * @generated
	 */
	EClass getTextField();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Spinner <em>Spinner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Spinner</em>'.
	 * @see org.malai.widget.Spinner
	 * @generated
	 */
	EClass getSpinner();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.MultiLineList <em>Multi Line List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Line List</em>'.
	 * @see org.malai.widget.MultiLineList
	 * @generated
	 */
	EClass getMultiLineList();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.SingleLineList <em>Single Line List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Single Line List</em>'.
	 * @see org.malai.widget.SingleLineList
	 * @generated
	 */
	EClass getSingleLineList();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Panel <em>Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Panel</em>'.
	 * @see org.malai.widget.Panel
	 * @generated
	 */
	EClass getPanel();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Window <em>Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Window</em>'.
	 * @see org.malai.widget.Window
	 * @generated
	 */
	EClass getWindow();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.DialogueBox <em>Dialogue Box</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dialogue Box</em>'.
	 * @see org.malai.widget.DialogueBox
	 * @generated
	 */
	EClass getDialogueBox();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.SplitPane <em>Split Pane</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Split Pane</em>'.
	 * @see org.malai.widget.SplitPane
	 * @generated
	 */
	EClass getSplitPane();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.TabbedPanel <em>Tabbed Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tabbed Panel</em>'.
	 * @see org.malai.widget.TabbedPanel
	 * @generated
	 */
	EClass getTabbedPanel();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Button <em>Button</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Button</em>'.
	 * @see org.malai.widget.Button
	 * @generated
	 */
	EClass getButton();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.ToggleButton <em>Toggle Button</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Toggle Button</em>'.
	 * @see org.malai.widget.ToggleButton
	 * @generated
	 */
	EClass getToggleButton();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.Menu <em>Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Menu</em>'.
	 * @see org.malai.widget.Menu
	 * @generated
	 */
	EClass getMenu();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.CheckBox <em>Check Box</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Check Box</em>'.
	 * @see org.malai.widget.CheckBox
	 * @generated
	 */
	EClass getCheckBox();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.RadioButton <em>Radio Button</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Radio Button</em>'.
	 * @see org.malai.widget.RadioButton
	 * @generated
	 */
	EClass getRadioButton();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.ToggleMenu <em>Toggle Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Toggle Menu</em>'.
	 * @see org.malai.widget.ToggleMenu
	 * @generated
	 */
	EClass getToggleMenu();

	/**
	 * Returns the meta object for class '{@link org.malai.widget.RadioMenu <em>Radio Menu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Radio Menu</em>'.
	 * @see org.malai.widget.RadioMenu
	 * @generated
	 */
	EClass getRadioMenu();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WidgetFactory getWidgetFactory();

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
		 * The meta object literal for the '{@link org.malai.widget.impl.WidgetImpl <em>Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.WidgetImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getWidget()
		 * @generated
		 */
		EClass WIDGET = eINSTANCE.getWidget();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIDGET__PARENT = eINSTANCE.getWidget_Parent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET__NAME = eINSTANCE.getWidget_Name();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.ContainerWidgetImpl <em>Container Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.ContainerWidgetImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getContainerWidget()
		 * @generated
		 */
		EClass CONTAINER_WIDGET = eINSTANCE.getContainerWidget();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER_WIDGET__ELEMENTS = eINSTANCE.getContainerWidget_Elements();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.ListWidgetImpl <em>List Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.ListWidgetImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getListWidget()
		 * @generated
		 */
		EClass LIST_WIDGET = eINSTANCE.getListWidget();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.ButtonWidgetImpl <em>Button Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.ButtonWidgetImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getButtonWidget()
		 * @generated
		 */
		EClass BUTTON_WIDGET = eINSTANCE.getButtonWidget();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.TextWidgetImpl <em>Text Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.TextWidgetImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getTextWidget()
		 * @generated
		 */
		EClass TEXT_WIDGET = eINSTANCE.getTextWidget();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_WIDGET__TEXT = eINSTANCE.getTextWidget_Text();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.TreeImpl <em>Tree</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.TreeImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getTree()
		 * @generated
		 */
		EClass TREE = eINSTANCE.getTree();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.SliderImpl <em>Slider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.SliderImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getSlider()
		 * @generated
		 */
		EClass SLIDER = eINSTANCE.getSlider();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.ProgressBarImpl <em>Progress Bar</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.ProgressBarImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getProgressBar()
		 * @generated
		 */
		EClass PROGRESS_BAR = eINSTANCE.getProgressBar();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.TableImpl <em>Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.TableImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getTable()
		 * @generated
		 */
		EClass TABLE = eINSTANCE.getTable();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.LabelImpl <em>Label</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.LabelImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getLabel()
		 * @generated
		 */
		EClass LABEL = eINSTANCE.getLabel();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.TextAreaImpl <em>Text Area</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.TextAreaImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getTextArea()
		 * @generated
		 */
		EClass TEXT_AREA = eINSTANCE.getTextArea();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.TextFieldImpl <em>Text Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.TextFieldImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getTextField()
		 * @generated
		 */
		EClass TEXT_FIELD = eINSTANCE.getTextField();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.SpinnerImpl <em>Spinner</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.SpinnerImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getSpinner()
		 * @generated
		 */
		EClass SPINNER = eINSTANCE.getSpinner();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.MultiLineListImpl <em>Multi Line List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.MultiLineListImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getMultiLineList()
		 * @generated
		 */
		EClass MULTI_LINE_LIST = eINSTANCE.getMultiLineList();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.SingleLineListImpl <em>Single Line List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.SingleLineListImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getSingleLineList()
		 * @generated
		 */
		EClass SINGLE_LINE_LIST = eINSTANCE.getSingleLineList();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.PanelImpl <em>Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.PanelImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getPanel()
		 * @generated
		 */
		EClass PANEL = eINSTANCE.getPanel();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.WindowImpl <em>Window</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.WindowImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getWindow()
		 * @generated
		 */
		EClass WINDOW = eINSTANCE.getWindow();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.DialogueBoxImpl <em>Dialogue Box</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.DialogueBoxImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getDialogueBox()
		 * @generated
		 */
		EClass DIALOGUE_BOX = eINSTANCE.getDialogueBox();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.SplitPaneImpl <em>Split Pane</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.SplitPaneImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getSplitPane()
		 * @generated
		 */
		EClass SPLIT_PANE = eINSTANCE.getSplitPane();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.TabbedPanelImpl <em>Tabbed Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.TabbedPanelImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getTabbedPanel()
		 * @generated
		 */
		EClass TABBED_PANEL = eINSTANCE.getTabbedPanel();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.ButtonImpl <em>Button</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.ButtonImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getButton()
		 * @generated
		 */
		EClass BUTTON = eINSTANCE.getButton();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.ToggleButtonImpl <em>Toggle Button</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.ToggleButtonImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getToggleButton()
		 * @generated
		 */
		EClass TOGGLE_BUTTON = eINSTANCE.getToggleButton();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.MenuImpl <em>Menu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.MenuImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getMenu()
		 * @generated
		 */
		EClass MENU = eINSTANCE.getMenu();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.CheckBoxImpl <em>Check Box</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.CheckBoxImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getCheckBox()
		 * @generated
		 */
		EClass CHECK_BOX = eINSTANCE.getCheckBox();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.RadioButtonImpl <em>Radio Button</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.RadioButtonImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getRadioButton()
		 * @generated
		 */
		EClass RADIO_BUTTON = eINSTANCE.getRadioButton();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.ToggleMenuImpl <em>Toggle Menu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.ToggleMenuImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getToggleMenu()
		 * @generated
		 */
		EClass TOGGLE_MENU = eINSTANCE.getToggleMenu();

		/**
		 * The meta object literal for the '{@link org.malai.widget.impl.RadioMenuImpl <em>Radio Menu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.malai.widget.impl.RadioMenuImpl
		 * @see org.malai.widget.impl.WidgetPackageImpl#getRadioMenu()
		 * @generated
		 */
		EClass RADIO_MENU = eINSTANCE.getRadioMenu();

	}

} //WidgetPackage

/**
 */
package org.malai.widget.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.malai.widget.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WidgetFactoryImpl extends EFactoryImpl implements WidgetFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WidgetFactory init() {
		try {
			WidgetFactory theWidgetFactory = (WidgetFactory)EPackage.Registry.INSTANCE.getEFactory("http://org.malai.widget/1_0_0//org/malai/widget"); 
			if (theWidgetFactory != null) {
				return theWidgetFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WidgetFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case WidgetPackage.TREE: return createTree();
			case WidgetPackage.SLIDER: return createSlider();
			case WidgetPackage.PROGRESS_BAR: return createProgressBar();
			case WidgetPackage.TABLE: return createTable();
			case WidgetPackage.LABEL: return createLabel();
			case WidgetPackage.TEXT_AREA: return createTextArea();
			case WidgetPackage.TEXT_FIELD: return createTextField();
			case WidgetPackage.SPINNER: return createSpinner();
			case WidgetPackage.MULTI_LINE_LIST: return createMultiLineList();
			case WidgetPackage.SINGLE_LINE_LIST: return createSingleLineList();
			case WidgetPackage.PANEL: return createPanel();
			case WidgetPackage.WINDOW: return createWindow();
			case WidgetPackage.DIALOGUE_BOX: return createDialogueBox();
			case WidgetPackage.SPLIT_PANE: return createSplitPane();
			case WidgetPackage.TABBED_PANEL: return createTabbedPanel();
			case WidgetPackage.BUTTON: return createButton();
			case WidgetPackage.TOGGLE_BUTTON: return createToggleButton();
			case WidgetPackage.MENU: return createMenu();
			case WidgetPackage.CHECK_BOX: return createCheckBox();
			case WidgetPackage.RADIO_BUTTON: return createRadioButton();
			case WidgetPackage.TOGGLE_MENU: return createToggleMenu();
			case WidgetPackage.RADIO_MENU: return createRadioMenu();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tree createTree() {
		TreeImpl tree = new TreeImpl();
		return tree;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Slider createSlider() {
		SliderImpl slider = new SliderImpl();
		return slider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProgressBar createProgressBar() {
		ProgressBarImpl progressBar = new ProgressBarImpl();
		return progressBar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Table createTable() {
		TableImpl table = new TableImpl();
		return table;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Label createLabel() {
		LabelImpl label = new LabelImpl();
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextArea createTextArea() {
		TextAreaImpl textArea = new TextAreaImpl();
		return textArea;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextField createTextField() {
		TextFieldImpl textField = new TextFieldImpl();
		return textField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Spinner createSpinner() {
		SpinnerImpl spinner = new SpinnerImpl();
		return spinner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultiLineList createMultiLineList() {
		MultiLineListImpl multiLineList = new MultiLineListImpl();
		return multiLineList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SingleLineList createSingleLineList() {
		SingleLineListImpl singleLineList = new SingleLineListImpl();
		return singleLineList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Panel createPanel() {
		PanelImpl panel = new PanelImpl();
		return panel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Window createWindow() {
		WindowImpl window = new WindowImpl();
		return window;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DialogueBox createDialogueBox() {
		DialogueBoxImpl dialogueBox = new DialogueBoxImpl();
		return dialogueBox;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SplitPane createSplitPane() {
		SplitPaneImpl splitPane = new SplitPaneImpl();
		return splitPane;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TabbedPanel createTabbedPanel() {
		TabbedPanelImpl tabbedPanel = new TabbedPanelImpl();
		return tabbedPanel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Button createButton() {
		ButtonImpl button = new ButtonImpl();
		return button;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ToggleButton createToggleButton() {
		ToggleButtonImpl toggleButton = new ToggleButtonImpl();
		return toggleButton;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Menu createMenu() {
		MenuImpl menu = new MenuImpl();
		return menu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CheckBox createCheckBox() {
		CheckBoxImpl checkBox = new CheckBoxImpl();
		return checkBox;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RadioButton createRadioButton() {
		RadioButtonImpl radioButton = new RadioButtonImpl();
		return radioButton;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ToggleMenu createToggleMenu() {
		ToggleMenuImpl toggleMenu = new ToggleMenuImpl();
		return toggleMenu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RadioMenu createRadioMenu() {
		RadioMenuImpl radioMenu = new RadioMenuImpl();
		return radioMenu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetPackage getWidgetPackage() {
		return (WidgetPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static WidgetPackage getPackage() {
		return WidgetPackage.eINSTANCE;
	}

} //WidgetFactoryImpl

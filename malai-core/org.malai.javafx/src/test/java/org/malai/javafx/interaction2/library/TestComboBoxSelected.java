package org.malai.javafx.interaction2.library;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

public class TestComboBoxSelected extends BaseWIMPWidgetTest<ComboBox<?>, ComboBoxSelected> {
	@Override
	ComboBox<?> createWidget() {
		return new ComboBox<>();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new ActionEvent(wimpWidget, null));
	}

	@Override
	protected ComboBoxSelected createInteraction() {
		return new ComboBoxSelected();
	}
}

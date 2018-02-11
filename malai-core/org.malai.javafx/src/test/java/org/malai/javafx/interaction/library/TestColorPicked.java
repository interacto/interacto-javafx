package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;

public class TestColorPicked extends BaseWIMPWidgetTest<ColorPicker, ColorPicked> {
	@Override
	ColorPicker createWidget() {
		return new ColorPicker();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new ActionEvent(wimpWidget, null));
	}

	@Override
	protected ColorPicked createInteraction() {
		return new ColorPicked();
	}
}

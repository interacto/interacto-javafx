package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
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

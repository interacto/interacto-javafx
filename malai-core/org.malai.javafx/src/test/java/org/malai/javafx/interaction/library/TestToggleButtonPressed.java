package org.malai.javafx.interaction.library;

import javafx.scene.control.ToggleButton;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class TestToggleButtonPressed extends BaseWIMPWidgetTest<ToggleButton, ToggleButtonPressed> {
	@Override
	ToggleButton createWidget() {
		return new ToggleButton();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fire();
	}

	@Override
	protected ToggleButtonPressed createInteraction() {
		return new ToggleButtonPressed();
	}
}

package org.malai.javafx.interaction2.library;

import javafx.scene.control.ToggleButton;

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

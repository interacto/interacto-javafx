package org.malai.javafx.interaction2.library;

import javafx.scene.control.Button;

public class TestButtonPressed extends BaseWIMPWidgetTest<Button, ButtonPressed> {
	@Override
	Button createWidget() {
		return new Button();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fire();
	}

	@Override
	protected ButtonPressed createInteraction() {
		return new ButtonPressed();
	}
}

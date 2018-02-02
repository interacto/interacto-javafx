package org.malai.javafx.interaction2.library;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class TestTextInputChanged extends BaseWIMPWidgetTest<TextInputControl, TextInputChanged> {
	@Override
	TextInputControl createWidget() {
		return new TextField();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new ActionEvent(wimpWidget, null));
	}

	@Override
	protected TextInputChanged createInteraction() {
		return new TextInputChanged();
	}
}

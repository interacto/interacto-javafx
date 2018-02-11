package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;

public class TestChoiceBoxSelected extends BaseWIMPWidgetTest<ChoiceBox<?>, ChoiceBoxSelected> {
	@Override
	ChoiceBox<?> createWidget() {
		return new ChoiceBox<>();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new ActionEvent(wimpWidget, null));
	}

	@Override
	protected ChoiceBoxSelected createInteraction() {
		return new ChoiceBoxSelected();
	}
}

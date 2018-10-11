package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
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

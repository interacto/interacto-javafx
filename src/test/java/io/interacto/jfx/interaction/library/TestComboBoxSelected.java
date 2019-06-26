package io.interacto.jfx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
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

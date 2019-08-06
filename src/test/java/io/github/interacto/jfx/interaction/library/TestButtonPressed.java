package io.github.interacto.jfx.interaction.library;

import javafx.scene.control.Button;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
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

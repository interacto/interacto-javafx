package org.malai.javafx.interaction.library;

import javafx.scene.control.MenuButton;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class TestMenuButtonPressed extends BaseWIMPWidgetTest<MenuButton, MenuButtonPressed> {
	@Override
	MenuButton createWidget() {
		return new MenuButton();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fire();
	}

	@Override
	protected MenuButtonPressed createInteraction() {
		return new MenuButtonPressed();
	}
}

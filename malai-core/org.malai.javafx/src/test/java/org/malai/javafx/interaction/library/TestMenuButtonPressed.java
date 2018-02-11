package org.malai.javafx.interaction.library;

import javafx.scene.control.MenuButton;

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

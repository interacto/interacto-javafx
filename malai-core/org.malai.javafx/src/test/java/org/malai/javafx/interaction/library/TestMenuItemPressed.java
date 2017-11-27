package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestMenuItemPressed extends BaseJfXInteractionTest<MenuItemPressed> {
	@Override
	protected MenuItemPressed createInteraction() {
		return new MenuItemPressed();
	}
}

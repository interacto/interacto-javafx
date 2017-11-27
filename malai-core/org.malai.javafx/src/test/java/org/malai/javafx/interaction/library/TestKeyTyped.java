package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestKeyTyped extends BaseJfXInteractionTest<KeyTyped> {
	@Override
	protected KeyTyped createInteraction() {
		return new KeyTyped();
	}
}

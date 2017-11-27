package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestKeysTyped extends BaseJfXInteractionTest<KeysTyped> {
	@Override
	protected KeysTyped createInteraction() {
		return new KeysTyped();
	}
}

package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestKeysScrolling extends BaseJfXInteractionTest<KeysScrolling> {
	@Override
	protected KeysScrolling createInteraction() {
		return new KeysScrolling();
	}
}

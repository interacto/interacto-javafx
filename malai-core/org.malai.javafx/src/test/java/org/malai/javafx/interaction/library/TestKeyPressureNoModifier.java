package org.malai.javafx.interaction.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestKeyPressureNoModifier extends BaseJfXInteractionTest<KeyPressureNoModifier> {
	@Override
	protected KeyPressureNoModifier createInteraction() {
		return new KeyPressureNoModifier();
	}
}

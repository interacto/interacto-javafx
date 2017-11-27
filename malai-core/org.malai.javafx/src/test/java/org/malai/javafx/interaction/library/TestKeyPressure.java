package org.malai.javafx.interaction.library;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class TestKeyPressure extends BaseJfXInteractionTest<KeyPressure> {
	@Override
	protected KeyPressure createInteraction() {
		return new KeyPressure();
	}

	@Test
	public void testOneKeyPressEndsAndReinit() throws MustAbortStateMachineException {
		interaction.onKeyPressure(createKeyPressEvent("A", KeyCode.A), 0);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	public void testTwoKeyPressEndsAndReinit() throws MustAbortStateMachineException {
		interaction.onKeyPressure(createKeyPressEvent("A", KeyCode.A), 0);
		interaction.onKeyPressure(createKeyPressEvent("B", KeyCode.B), 0);
		Mockito.verify(handler, Mockito.times(2)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(2)).interactionStarts(interaction);
	}
}

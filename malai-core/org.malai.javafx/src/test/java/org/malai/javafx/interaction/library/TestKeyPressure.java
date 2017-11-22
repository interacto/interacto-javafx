package org.malai.javafx.interaction.library;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.MustAbortStateMachineException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestKeyPressure extends TestJfXInteraction<KeyPressure> {
	@Override
	protected KeyPressure createInteraction() {
		return new KeyPressure();
	}

	@Test
	public void testOneKeyPressEndsAndReinit() {
		final BooleanProperty hasEnded = new SimpleBooleanProperty(false);
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				hasEnded.set(true);
			}
		});
		interaction.onKeyPressure(createKeyPressEvent("A", KeyCode.A), 0);
		assertTrue(interaction.getCurrentState() instanceof InitState);
		assertTrue(hasEnded.get());
	}

	@Test
	public void testTwoKeyPressEndsAndReinit() {
		final IntegerProperty nbExec = new SimpleIntegerProperty(0);
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				nbExec.set(nbExec.get()+1);
			}
		});
		interaction.onKeyPressure(createKeyPressEvent("A", KeyCode.A), 0);
		interaction.onKeyPressure(createKeyPressEvent("B", KeyCode.B), 0);
		assertTrue(interaction.getCurrentState() instanceof InitState);
		assertEquals(2, nbExec.get());
	}
}

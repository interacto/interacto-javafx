package org.malai.javafx.interaction.library;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.MustAbortStateMachineException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestKeysPressure extends TestJfXInteraction<KeysPressure> {
	@Override
	protected KeysPressure createInteraction() {
		return new KeysPressure();
	}

	@Test
	public void testTwoKeyPressuresOnReleaseEnds() {
		final BooleanProperty hasEnded = new SimpleBooleanProperty(false);
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				hasEnded.set(true);
			}
		});
		interaction.onKeyPressure(createKeyPressEvent("A", KeyCode.A), 0);
		interaction.onKeyPressure(createKeyPressEvent("B", KeyCode.B), 0);
		interaction.onKeyRelease(createKeyReleaseEvent("B", KeyCode.B), 0);
		assertTrue(hasEnded.get());
	}

	@Test
	public void testTwoKeyPressuresOnReleaseReinitRecycleEvent() {
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
		interaction.onKeyRelease(createKeyReleaseEvent("B", KeyCode.B), 0);
		interaction.onKeyPressure(createKeyPressEvent("B", KeyCode.B), 0);
		interaction.onKeyRelease(createKeyReleaseEvent("B", KeyCode.B), 0);
		assertEquals(2, nbExec.get());
	}

	@Test
	public void testTwoKeyPressuresAndReleases() {
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
		interaction.onKeyRelease(createKeyReleaseEvent("A", KeyCode.A), 0);
		interaction.onKeyRelease(createKeyReleaseEvent("B", KeyCode.B), 0);
		assertEquals(2, nbExec.get());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}
}

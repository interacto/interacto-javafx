package org.malai.javafx.interaction;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseButton;
import org.junit.Test;
import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.javafx.interaction.library.Press;
import org.malai.stateMachine.MustAbortStateMachineException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPress extends TestJfXInteraction<Press> {
	@Override
	protected Press createInteraction() {
		return new Press();
	}

	@Test
	public void testOnePressEndsAndReinit() {
		final BooleanProperty hasEnded = new SimpleBooleanProperty(false);
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				hasEnded.set(true);
			}
		});
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		assertTrue(interaction.getCurrentState() instanceof InitState);
		assertTrue(hasEnded.get());
	}

	@Test
	public void testTwoPressEndsAndReinit() {
		final IntegerProperty nbExec = new SimpleIntegerProperty(0);
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				nbExec.set(nbExec.get()+1);
			}
		});
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onPressure(createMousePressEvent(11d, 21d, MouseButton.PRIMARY), 0);
		assertTrue(interaction.getCurrentState() instanceof InitState);
		assertEquals(2, nbExec.get());
	}
}

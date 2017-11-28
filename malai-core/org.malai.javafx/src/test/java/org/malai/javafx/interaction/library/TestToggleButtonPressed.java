package org.malai.javafx.interaction.library;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TestToggleButtonPressed extends BaseJfXInteractionTest<ToggleButtonPressed> {
	ToggleButton button;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		button = new ToggleButton();
	}

	@Override
	protected ToggleButtonPressed createInteraction() {
		return new ToggleButtonPressed();
	}

	@Test
	void testToggleButtonClickGoodState() throws MustAbortStateMachineException {
		interaction.onJfxToggleButtonPressed(button);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testToggleButtonClickGoodData() throws MustAbortStateMachineException {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				assertEquals(button, ((ToggleButtonPressed) interaction).widget);
			}
		});

		interaction.onJfxToggleButtonPressed(button);
	}

	@Test
	void testToggleButtonClickReinit() throws MustAbortStateMachineException {
		interaction.onJfxToggleButtonPressed(button);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterToggleButtons() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(button));
		button.fire();
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotRegisteredToggleButton() throws MustAbortStateMachineException {
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotToggleButtonRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testToggleButtonNoActionWhenNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(null);
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testToggleButtonNoActionWhenContainsNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}
}

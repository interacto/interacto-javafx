package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.InitState;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustCancelStateMachineException;
import org.mockito.Mockito;

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
	void testToggleButtonClickGoodState() throws MustCancelStateMachineException {
		interaction.onJfxToggleButtonPressed(button);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testToggleButtonClickGoodData() {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops() throws MustCancelStateMachineException {
				super.interactionStops();
				assertEquals(button, interaction.widget);
			}
		});

		interaction.onJfxToggleButtonPressed(button);
	}

	@Test
	void testToggleButtonClickReinit() {
		interaction.onJfxToggleButtonPressed(button);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterToggleButtons() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(button));
		button.fire();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testNoActionWhenNotRegisteredToggleButton() throws MustCancelStateMachineException {
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNotToggleButtonRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testToggleButtonNoActionWhenNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(null);
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testToggleButtonNoActionWhenContainsNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}
}

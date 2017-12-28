package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class TestButtonPressed extends BaseJfXInteractionTest<ButtonPressed> {
	Button button;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		button = new Button();
	}

	@Override
	protected ButtonPressed createInteraction() {
		return new ButtonPressed();
	}

	@Test
	void testButtonClickGoodState() throws MustCancelStateMachineException {
		interaction.onJfxButtonPressed(button);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testButtonClickGoodData() {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops() throws MustCancelStateMachineException {
				super.interactionStops();
				assertEquals(button, interaction.widget);
			}
		});
		interaction.onJfxButtonPressed(button);
	}

	@Test
	void testButtonClickReinit() {
		interaction.onJfxButtonPressed(button);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterButtons() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(button));
		button.fire();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testNoActionWhenNotRegistered() throws MustCancelStateMachineException {
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNotButtonRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(null);
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		button.fire();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}
}

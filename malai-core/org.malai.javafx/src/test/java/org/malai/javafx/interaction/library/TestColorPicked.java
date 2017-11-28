package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
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
public class TestColorPicked extends BaseJfXInteractionTest<ColorPicked> {
	ColorPicker picker;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		picker = new ColorPicker();
	}

	@Override
	protected ColorPicked createInteraction() {
		return new ColorPicked();
	}


	@Test
	void testColorPickedGoodState() throws MustAbortStateMachineException {
		interaction.onJfxColorPicked(picker);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testColorPickedClickGoodData() throws MustAbortStateMachineException {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				assertEquals(picker, ((ColorPicked) interaction).widget);
			}
		});
		interaction.onJfxColorPicked(picker);
	}

	@Test
	void testColorPickedClickReinit() throws MustAbortStateMachineException {
		interaction.onJfxColorPicked(picker);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterColorPicked() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(picker));
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testColorPickedNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotColorPickedRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testColorPickedNoActionWhenNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(null);
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testColorPickedNoActionWhenContainsNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}
}
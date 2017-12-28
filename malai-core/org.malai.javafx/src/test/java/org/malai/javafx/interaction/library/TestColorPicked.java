package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
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
	void testColorPickedGoodState() throws MustCancelStateMachineException {
		interaction.onJfxColorPicked(picker);
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testColorPickedClickGoodData() {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops() throws MustCancelStateMachineException {
				super.interactionStops();
				assertEquals(picker, interaction.widget);
			}
		});
		interaction.onJfxColorPicked(picker);
	}

	@Test
	void testColorPickedClickReinit() {
		interaction.onJfxColorPicked(picker);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	void testRegisterColorPicked() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(picker));
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
	}

	@Test
	void testColorPickedNoActionWhenNotRegistered() throws MustCancelStateMachineException {
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testNoActionWhenNotColorPickedRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testColorPickedNoActionWhenNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(null);
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}

	@Test
	void testColorPickedNoActionWhenContainsNullRegistered() throws MustCancelStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		picker.fireEvent(new ActionEvent());
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}
}
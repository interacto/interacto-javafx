package org.malai.javafx.interaction.library;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestTextChanged extends BaseJfXInteractionTest<TextChanged> {
	TextInputControl input;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		input = new TextField();
	}

	@Override
	protected TextChanged createInteraction() {
		return new TextChanged();
	}

	@Test
	void testTextChangedGoodState() throws MustAbortStateMachineException {
		interaction.onTextChanged(input);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}


	@Test
	void testTextChangedReinit() throws MustAbortStateMachineException {
		interaction.onTextChanged(input);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
	}


	@Test
	void testTextChanged() throws MustAbortStateMachineException {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStops(interaction);
				assertEquals(input, ((TextChanged) interaction).widget);
				assertEquals("foo bar", ((TextChanged) interaction).widget.getText());
			}
		});
		input.setText("foo bar");
		interaction.onTextChanged(input);
	}

	@Test
	void testRegisterText() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(input));

		input.setText("foo");
		interaction.onTextChanged(input);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testTextNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));

		input.setText("foo");
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotTextRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		input.setText("foo");
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testTextNoActionWhenNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(null);
		input.setText("foo");
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testTextNoActionWhenContainsNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		input.setText("foo");
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testTextChangedTransition() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		input.setText("foo");
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}


}

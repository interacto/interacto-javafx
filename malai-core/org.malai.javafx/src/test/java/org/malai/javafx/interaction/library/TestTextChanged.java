package org.malai.javafx.interaction.library;

import java.util.Collections;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		sleep(1200L);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testTextChangedThreeTimes() throws MustAbortStateMachineException {
		interaction.onTextChanged(input);
		sleep(500L);
		interaction.onTextChanged(input);
		sleep(200L);
		interaction.onTextChanged(input);
		sleep(1200L);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates(interaction);
	}

	@Test
	void testTextChangedButNotFinished() throws MustAbortStateMachineException {
		interaction.onTextChanged(input);
		sleep(1200L);
		interaction.onTextChanged(input);
		sleep(1200L);
		Mockito.verify(handler, Mockito.times(2)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(2)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates(interaction);
	}

	@Test
	void testTextChangedReinit() {
		input.setText("foo");
		interaction.onTextChanged(input);
		sleep(1200L);
		assertNull(interaction.getWidget());
		assertEquals(-1, interaction.getLastHIDUsed());
		assertTrue(interaction.getCurrentState() instanceof InitState);
		assertEquals("foo", interaction.getTxt());
	}


	@Test
	void testTextChanged() {
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
		sleep(1200L);
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

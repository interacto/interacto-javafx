package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.InitState;
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
	protected TextChanged createInteraction() {
		return new TextChanged();
	}

	@Override
	public void start(final Stage stage) throws Exception {
		super.start(stage);
		input = new TextField();
		Scene scene = new Scene(input);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
	}

	@Test
	void testTypeOnInputTextChanged() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(input));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testTypeOnInputTextChangedStopped() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(input));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		sleep(1200L);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
	}

	@Test
	void testTextTypedTextChanged() {
		interaction.registerToNodes(Collections.singletonList(input));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		assertEquals("abc", interaction.widget.getText());
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
	}


	@Test
	void testRegisterText() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(input));
		clickOn(input).type(KeyCode.A);
		interaction.onTextChanged(input);
		sleep(1200L);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Test
	void testTextNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testNoActionWhenNotTextRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(new CheckBox()));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testTextNoActionWhenNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(null);
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testTextNoActionWhenContainsNullRegistered() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

	@Test
	void testTextChangedTransition() throws MustAbortStateMachineException {
		interaction.registerToNodes(Collections.singletonList(null));
		clickOn(input).type(KeyCode.A, KeyCode.B, KeyCode.C);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}
}

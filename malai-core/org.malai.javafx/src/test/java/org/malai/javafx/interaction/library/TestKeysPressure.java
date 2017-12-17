package org.malai.javafx.interaction.library;

import java.util.Arrays;
import java.util.Collections;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestKeysPressure extends BaseJfXInteractionTest<KeysPressure> {
	@Override
	protected KeysPressure createInteraction() {
		return new KeysPressure();
	}

	@Test
	public void testOneKeyPressure() throws MustAbortStateMachineException {
		interaction.onKeyPressure(createKeyPressEvent("Ctrl", KeyCode.CONTROL), 0);
		Mockito.verify(handler, Mockito.times(0)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(0)).interactionAborts(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}

	@Nested
	class TestWithWidget extends ApplicationTest {
		TextField textField;

		@BeforeEach
		void beforeEach() {
			interaction.registerToNodes(Collections.singletonList(textField));
		}

		@Override
		public void start(final Stage stage) throws Exception {
			super.start(stage);
			textField = new TextField();
			stage.setScene(new Scene(textField));
			stage.show();
			stage.toFront();
		}

		@Test
		public void testTwoKeyPressures() throws MustAbortStateMachineException {
			clickOn(textField).press(KeyCode.CONTROL, KeyCode.A).sleep(50L);
			Mockito.verify(handler, Mockito.times(0)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
			assertEquals(Arrays.asList(KeyCode.CONTROL, KeyCode.A), interaction.getKeyCodes());
		}

		@Test
		public void testTwoKeyPressuresAndOneRelease() throws MustAbortStateMachineException {
			clickOn(textField).press(KeyCode.CONTROL, KeyCode.A).release(KeyCode.A).sleep(100L);
			Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.times(2)).interactionStarts(interaction);
			assertEquals(Collections.singletonList(KeyCode.CONTROL), interaction.getKeyCodes());
		}

		@Test
		public void testTwoKeyPressuresAndTwoReleases() throws MustAbortStateMachineException {
			clickOn(textField).press(KeyCode.CONTROL, KeyCode.A).release(KeyCode.CONTROL, KeyCode.A).sleep(100L);
			Mockito.verify(handler, Mockito.times(2)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.times(2)).interactionStarts(interaction);
			assertEquals(Collections.emptyList(), interaction.getKeyCodes());
		}

		@Test
		public void testThreeKeyPressuresAndTwoReleases() throws MustAbortStateMachineException {
			clickOn(textField).press(KeyCode.CONTROL, KeyCode.A).release(KeyCode.A).press(KeyCode.A).release(KeyCode.A).sleep(100L);
			Mockito.verify(handler, Mockito.times(2)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.times(3)).interactionStarts(interaction);
			assertEquals(Collections.singletonList(KeyCode.CONTROL), interaction.getKeyCodes());
		}
	}
}

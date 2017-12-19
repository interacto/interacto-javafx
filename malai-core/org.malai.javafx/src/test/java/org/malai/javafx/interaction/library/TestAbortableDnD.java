package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(MockitoExtension.class)
public class TestAbortableDnD extends TestDnD {
	@Override
	protected AbortableDnD createInteraction() {
		return new AbortableDnD();
	}

	@Nested
	class TestOnPane extends ApplicationTest {
		Pane pane;

		@BeforeEach
		void beforeEach() {
			interaction.registerToNodes(Collections.singletonList(pane));
		}

		@Override
		public void start(final Stage stage) throws Exception {
			super.start(stage);
			pane = new Pane();
			pane.setPrefWidth(400);
			pane.setPrefHeight(400);
			stage.setScene(new Scene(pane));
			stage.show();
			stage.toFront();
		}

		@Test
		public void testDnDOK() throws MustAbortStateMachineException {
			drag(pane).dropBy(100, 100).sleep(50L);
			Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		}

		@Test
		public void testDnDEscapeOK() throws MustAbortStateMachineException {
			Platform.runLater(() -> pane.requestFocus());
			WaitForAsyncUtils.waitForFxEvents();
			drag(pane).moveBy(100, 100).type(KeyCode.ESCAPE).sleep(50L);
			Mockito.verify(handler, Mockito.times(0)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionAborts(interaction);
		}
	}

	@Nested
	class TestOnNode extends ApplicationTest {
		Pane pane;
		Rectangle rec;

		@BeforeEach
		void beforeEach() {
			interaction.registerToNodes(Collections.singletonList(rec));
		}

		@Override
		public void start(final Stage stage) throws Exception {
			super.start(stage);
			pane = new Pane();
			pane.setPrefWidth(400d);
			pane.setPrefHeight(400d);
			rec = new Rectangle(100d, 100d, 70d, 50d);
			pane.getChildren().addAll(rec);
			stage.setScene(new Scene(pane));
			stage.show();
			stage.toFront();
		}

		@Test
		public void testDnDOK() throws MustAbortStateMachineException {
			drag(rec).dropBy(100, 100).sleep(50L);
			Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		}

		@Test
		public void testDnDEscapeOK() throws MustAbortStateMachineException {
			Platform.runLater(() -> pane.requestFocus());
			WaitForAsyncUtils.waitForFxEvents();
			drag(rec);
			Platform.runLater(() -> rec.requestFocus());
			WaitForAsyncUtils.waitForFxEvents();
			moveBy(100, 100).type(KeyCode.ESCAPE).sleep(50L);
			Mockito.verify(handler, Mockito.times(0)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionAborts(interaction);
		}
	}
}

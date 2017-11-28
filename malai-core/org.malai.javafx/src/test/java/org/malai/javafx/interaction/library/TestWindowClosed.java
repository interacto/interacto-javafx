package org.malai.javafx.interaction.library;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.Interaction;
import org.malai.interaction.TransitionImpl;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;
import sun.plugin2.ipc.windows.WindowsEvent;

import java.awt.event.WindowEvent;

@ExtendWith(MockitoExtension.class)
public class TestWindowClosed extends BaseJfXInteractionTest<WindowClosed> {
	Window window;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		Platform.runLater(() -> {
			Stage stage = new Stage();
			Scene scene = new Scene(new Button());
			stage.setScene(scene);
			window = stage.getScene().getWindow();
		});
	}

	@Override
	protected WindowClosed createInteraction() {
		return new WindowClosed();
	}
	@Test
	void testWindowClosedGoodState() throws MustAbortStateMachineException {
		interaction.onWindowClosed(createWindowEvent(window, javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST));
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
	}
}

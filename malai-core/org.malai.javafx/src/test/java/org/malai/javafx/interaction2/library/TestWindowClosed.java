package org.malai.javafx.interaction2.library;

import java.util.Collections;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Override
	protected WindowClosed createInteraction() {
		return new WindowClosed();
	}

	@Test
	void testWindowClosedGoodState() throws CancelFSMException {
		interaction.processEvent(createWindowEvent(window, javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}


	@Test
	void testRegisterNode() throws CancelFSMException {
		interaction.registerToWindows(Collections.singletonList(window));
		Platform.runLater(() -> window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST)));
		WaitForAsyncUtils.waitForFxEvents();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testUnRegisterNode() throws CancelFSMException {
		interaction.registerToWindows(Collections.singletonList(window));
		interaction.unregisterFromWindows(Collections.singletonList(window));
		Platform.runLater(() -> window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST)));
		WaitForAsyncUtils.waitForFxEvents();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testGoodStateNULL() throws CancelFSMException {
		interaction.processEvent(null);
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testClickGoodData() {
		interaction.getFsm().setHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(window, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(window, null));
	}
}

package org.malai.javafx.interaction;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.malai.command.Command;
import org.malai.command.CommandImpl;
import org.malai.javafx.binding.KeyNodeBinder;
import org.malai.javafx.instrument.JfxInstrument;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestKeyLost extends ApplicationTest {
	StubInstrument instrument;
	Canvas canvas;
	Stage stage;

	@Override
	public void start(Stage stageToConfigure) {
		canvas = new Canvas();
		instrument = new StubInstrument();
		instrument.setActivated(true);
		final VBox parent = new VBox();
		parent.getChildren().add(canvas);
		final Scene scene = new Scene(parent);
		stageToConfigure.setScene(scene);
		stageToConfigure.show();
		stageToConfigure.toFront();
		stageToConfigure.centerOnScreen();
		stageToConfigure.requestFocus();
		stage = stageToConfigure;
	}

	@BeforeEach
	void setUp() {
		WaitForAsyncUtils.waitForFxEvents();
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
		new KeyNodeBinder<>(StubCmd::new, instrument).with(KeyCode.CONTROL, KeyCode.C).on(canvas).bind();
	}

	@Disabled
	@Test
	void testLostWindowFocus() {
		press(KeyCode.ALT);
		Platform.runLater(() -> stage.setIconified(true));
		WaitForAsyncUtils.waitForFxEvents(40);
		Platform.runLater(() -> stage.setIconified(false));
		WaitForAsyncUtils.waitForFxEvents(40);
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
		press(KeyCode.CONTROL).type(KeyCode.C);

		assertEquals(1, instrument.exec.get());
		assertEquals(1, ((StubCmd) instrument.lastCreatedCmd).exec.get());
	}


	public static class StubCmd extends CommandImpl {
		final IntegerProperty exec = new SimpleIntegerProperty(0);

		@Override
		protected void doCmdBody() {
			synchronized(exec) {
				exec.setValue(exec.getValue() + 1);
			}
		}

		@Override
		public boolean canDo() {
			return true;
		}

		@Override
		public RegistrationPolicy getRegistrationPolicy() {
			return RegistrationPolicy.NONE;
		}
	}

	static class StubInstrument extends JfxInstrument {
		final IntegerProperty exec = new SimpleIntegerProperty(0);
		Command lastCreatedCmd = null;

		@Override
		protected void configureBindings() {
		}

		@Override
		public void onCmdDone(final Command cmd) {
			synchronized(exec) {
				exec.setValue(exec.getValue() + 1);
				lastCreatedCmd = cmd;
			}
		}
	}
}

package io.github.interacto.jfx.interaction;

import io.github.interacto.command.Command;
import io.github.interacto.command.CommandImpl;
import io.github.interacto.jfx.binding.Bindings;
import io.github.interacto.jfx.instrument.JfxInstrument;
import java.util.concurrent.atomic.AtomicInteger;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestKeyLost {
	Canvas canvas;
	Stage stage;
	AtomicInteger cpt;

	@Start
	public void start(final Stage stageToConfigure) {
		canvas = new Canvas();
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
		cpt = new AtomicInteger();
		WaitForAsyncUtils.waitForFxEvents();
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();

		Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.with(KeyCode.CONTROL, KeyCode.C)
			.on(canvas)
			.end(i -> cpt.incrementAndGet())
			.bind();
	}

	@Disabled
	@Test
	void testLostWindowFocus(final FxRobot robot) {
		robot.press(KeyCode.ALT);
		Platform.runLater(() -> stage.setIconified(true));
		WaitForAsyncUtils.waitForFxEvents(40);
		Platform.runLater(() -> stage.setIconified(false));
		WaitForAsyncUtils.waitForFxEvents(40);
		Platform.runLater(() -> canvas.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
		robot.press(KeyCode.CONTROL).type(KeyCode.C);

		assertEquals(1, cpt.get());
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

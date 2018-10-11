package org.malai.javafx.interaction.binding;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.AfterEach;
import org.malai.command.Command;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

public abstract class TestBinder<W> {
	W widget1;
	W widget2;
	StubInstrument instrument;

	@AfterEach
	void afterEach(final FxRobot robot) {
		robot.release(new KeyCode[] {});
		robot.release(new MouseButton[] {});
		WaitForAsyncUtils.waitForFxEvents();
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

	void grabFocus(final Node node) {
		Platform.runLater(() -> node.requestFocus());
		WaitForAsyncUtils.waitForFxEvents(20);
	}
}

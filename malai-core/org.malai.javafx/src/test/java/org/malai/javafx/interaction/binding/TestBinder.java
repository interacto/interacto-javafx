package org.malai.javafx.interaction.binding;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import org.malai.action.Action;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public abstract class TestBinder<W> extends ApplicationTest {
	W widget1;
	W widget2;
	StubInstrument instrument;

	public static class StubAction extends ActionImpl {
		final IntegerProperty exec = new SimpleIntegerProperty(0);

		@Override
		protected void doActionBody() {
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
		Action lastCreatedAction = null;

		@Override
		protected void configureBindings() {
		}

		@Override
		public void onActionDone(final Action action) {
			synchronized(exec) {
				exec.setValue(exec.getValue() + 1);
				lastCreatedAction = action;
			}
		}
	}

	void grabFocus(final Node node) {
		Platform.runLater(() -> node.requestFocus());
		WaitForAsyncUtils.waitForFxEvents(20);
	}
}

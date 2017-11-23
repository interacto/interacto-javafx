package org.malai.javafx.interaction.binding;

import java.util.concurrent.TimeoutException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.AfterEach;
import org.malai.action.Action;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

public abstract class TestBinder<W> extends ApplicationTest {
	W widget1;
	W widget2;
	StubInstrument instrument;

	@AfterEach
	public void tearDown() throws TimeoutException {
		FxToolkit.hideStage();
		FxToolkit.cleanupStages();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}

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

		@Override
		protected void configureBindings() throws InstantiationException, IllegalAccessException {

		}

		@Override
		public void onActionDone(final Action action) {
			synchronized(exec) {
				exec.setValue(exec.getValue() + 1);
			}
		}
	}
}

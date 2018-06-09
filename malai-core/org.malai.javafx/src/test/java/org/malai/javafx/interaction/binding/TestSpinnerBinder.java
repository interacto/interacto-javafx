package org.malai.javafx.interaction.binding;

import javafx.application.Platform;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.malai.javafx.binding.SpinnerBinder;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSpinnerBinder extends TestNodeBinder<Spinner<Double>> {
	@Override
	public void start(final Stage stage) {
		widget1 = new Spinner<>(0, 100, 50);
		widget2 = new Spinner<>(0, 100, 50);
		super.start(stage);
	}

	@Test
	public void testEndsOnUIThread() {
		new SpinnerBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertTrue(Platform.isFxApplicationThread())).
			bind();

		clickOn(widget1.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
		sleep(500);
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	public void testupdatesOnUIThread() {
		new SpinnerBinder<>(StubCmd::new, instrument).
			on(widget1).
			then((i, c) -> assertTrue(Platform.isFxApplicationThread())).
			bind();

		clickOn(widget1.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
		clickOn(widget1.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
		clickOn(widget1.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
		sleep(500);
		WaitForAsyncUtils.waitForFxEvents();
	}
}

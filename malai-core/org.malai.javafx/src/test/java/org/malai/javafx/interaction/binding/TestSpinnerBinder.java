package org.malai.javafx.interaction.binding;

import javafx.application.Platform;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.binding.SpinnerBinder;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestSpinnerBinder extends TestNodeBinder<Spinner<Double>> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Spinner<>(0, 100, 50);
		widget2 = new Spinner<>(0, 100, 50);
		super.start(stage);
	}

	@Test
	public void testEndsOnUIThread(final FxRobot robot) {
		new SpinnerBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertTrue(Platform.isFxApplicationThread())).
			bind();

		robot.clickOn(widget1.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
		robot.sleep(500);
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	public void testupdatesOnUIThread(final FxRobot robot) {
		new SpinnerBinder<>(StubCmd::new, instrument).
			on(widget1).
			then((i, c) -> assertTrue(Platform.isFxApplicationThread())).
			bind();

		robot.clickOn(widget1.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
		robot.clickOn(widget1.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
		robot.clickOn(widget1.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
		robot.sleep(500);
		WaitForAsyncUtils.waitForFxEvents();
	}
}

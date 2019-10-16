package io.github.interacto.jfx.robot;

import javafx.scene.control.Spinner;
import javafx.scene.input.MouseButton;
import org.testfx.api.FxRobot;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * selecting registeredItems in combo boxes and lists.
 */
public interface FxRobotSpinner {
	default <T> void incrementSpinner(final FxRobot robot, final Spinner<T> spinner) {
		robot.clickOn(spinner.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
	}

	default <T> void decrementSpinner(final FxRobot robot, final Spinner<T> spinner) {
		robot.clickOn(spinner.lookup(".decrement-arrow-button"), MouseButton.PRIMARY);
	}
}

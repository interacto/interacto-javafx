package io.github.interacto.jfx.robot;

import javafx.scene.control.Spinner;
import javafx.scene.input.MouseButton;
import org.testfx.api.FxRobotInterface;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * selecting registeredItems in combo boxes and lists.
 */
public interface FxRobotSpinner extends FxRobotInterface {
	default <T> void incrementSpinner(final Spinner<T> combo) {
		clickOn(combo.lookup(".increment-arrow-button"), MouseButton.PRIMARY);
	}

	default <T> void decrementSpinner(final Spinner<T> combo) {
		clickOn(combo.lookup(".decrement-arrow-button"), MouseButton.PRIMARY);
	}
}

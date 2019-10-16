package io.github.interacto.jfx.robot;

import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import org.testfx.api.FxRobot;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * picking colours.
 */
public interface FxRobotColourPicker {
	default void pickColour(final FxRobot robot, final ColorPicker picker) {
		robot.clickOn(picker).type(KeyCode.TAB).type(KeyCode.TAB).type(KeyCode.ENTER);
	}
}

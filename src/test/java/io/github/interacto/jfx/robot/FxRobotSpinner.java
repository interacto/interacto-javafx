/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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

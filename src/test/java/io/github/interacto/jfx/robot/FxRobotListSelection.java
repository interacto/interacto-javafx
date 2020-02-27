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

import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import org.testfx.api.FxRobot;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * selecting registeredItems in combo boxes and lists.
 */
public interface FxRobotListSelection {
	default <T> void selectGivenComboBoxItem(final FxRobot robot, final ComboBox<T> combo, final T item) {
		final int index = combo.getItems().indexOf(item);
		final int indexSel = combo.getSelectionModel().getSelectedIndex();

		if(index == -1) {
			fail("The item " + item + " is not in the combo box " + combo);
		}

		robot.clickOn(combo);

		if(index > indexSel) {
			for(int i = indexSel; i < index; i++) {
				robot.type(KeyCode.DOWN);
			}
		}else {
			if(index < indexSel) {
				for(int i = indexSel; i > index; i--) {
					robot.type(KeyCode.UP);
				}
			}
		}

		robot.type(KeyCode.ENTER);
	}
}

/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
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
package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.robot.FxRobotListSelection;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class TestComboboxBinder extends TestNodeBinder<ComboBox<String>> implements FxRobotListSelection {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new ComboBox<>(FXCollections.observableArrayList("a", "b"));
		widget2 = new ComboBox<>(FXCollections.observableArrayList("c", "d"));
		widget1.getSelectionModel().select(0);
		widget2.getSelectionModel().select(0);
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleComboBoxFunction(final FxRobot robot) {
		binding = Bindings.comboboxBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSingleComboBoxSupplier(final FxRobot robot) {
		binding = Bindings.comboboxBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoComboboxes(final FxRobot robot) {
		binding = Bindings.comboboxBinder()
			.toProduce(i -> cmd)
			.on(widget1, widget2)
			.bind();
		selectGivenComboBoxItem(robot, widget2, "d");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		binding = Bindings.comboboxBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.first(c -> c.exec.setValue(10))
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		binding = Bindings.comboboxBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		binding = Bindings.comboboxBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.when(i -> false)
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
		assertNotNull(binding);
	}
}

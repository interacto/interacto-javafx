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
import io.github.interacto.jfx.test.BindingsAssert;
import io.github.interacto.jfx.test.WidgetBindingExtension;
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
@ExtendWith(WidgetBindingExtension.class)
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
	public void testCommandExecutedOnSingleComboBoxFunction(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.comboboxBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSingleComboBoxSupplier(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.comboboxBinder()
			.toProduce(() -> new StubCmd())
			.on(widget1)
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testCommandExecutedOnTwoComboboxes(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.comboboxBinder()
			.toProduce(StubCmd::new)
			.on(widget1, widget2)
			.bind();
		selectGivenComboBoxItem(robot, widget2, "d");
		WaitForAsyncUtils.waitForFxEvents();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, binding.getTimesEnded());
		bindingsAssert.cmdsProduced(2);
	}

	@Test
	public void testInit1Executed(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.comboboxBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.first(c -> c.exec.set(10))
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testInit2Executed(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.comboboxBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.first((i, c) -> c.exec.set(10))
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testCheckFalse(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.comboboxBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.when(i -> false)
			.bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, binding.getTimesEnded());
		bindingsAssert.noCmdProduced();
	}
}

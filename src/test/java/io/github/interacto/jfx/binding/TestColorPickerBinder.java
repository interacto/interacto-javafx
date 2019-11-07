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

import io.github.interacto.jfx.robot.FxRobotColourPicker;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
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
public class TestColorPickerBinder extends TestNodeBinder<ColorPicker> implements FxRobotColourPicker {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new ColorPicker(Color.BLUE);
		widget2 = new ColorPicker(Color.BLUE);
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSinglePickerFunction(final FxRobot robot) {
		binding = Bindings.colorPickerBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSinglePickerSupplier(final FxRobot robot) {
		binding = Bindings.colorPickerBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoPickers(final FxRobot robot) {
		binding = Bindings.colorPickerBinder()
			.on(widget1, widget2)
			.toProduce(i -> cmd)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		pickColour(robot, widget2);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		binding = Bindings.colorPickerBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.first(c -> c.exec.setValue(10))
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		binding = Bindings.colorPickerBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		binding = Bindings.colorPickerBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.when(i -> false)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
		assertNotNull(binding);
	}
}

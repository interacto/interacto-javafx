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
import io.github.interacto.jfx.test.BindingsAssert;
import io.github.interacto.jfx.test.WidgetBindingExtension;
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
@ExtendWith(WidgetBindingExtension.class)
public class TestColorPickerBinder extends TestNodeBinder<ColorPicker> implements FxRobotColourPicker {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new ColorPicker(Color.BLUE);
		widget2 = new ColorPicker(Color.BLUE);
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSinglePickerFunction(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.colorPickerBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSinglePickerSupplier(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.colorPickerBinder()
			.toProduce(() -> new StubCmd())
			.on(widget1)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testCommandExecutedOnTwoPickers(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.colorPickerBinder()
			.on(widget1, widget2)
			.toProduce(StubCmd::new)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		pickColour(robot, widget2);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, binding.getTimesEnded());
		bindingsAssert.cmdsProduced(2);
	}

	@Test
	public void testInit1Executed(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.colorPickerBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.first(c -> c.exec.set(10))
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testInit2Executed(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.colorPickerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.first((i, c) -> c.exec.set(10))
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testCheckFalse(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.colorPickerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.when(i -> false)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		bindingsAssert.noCmdProduced();
	}
}

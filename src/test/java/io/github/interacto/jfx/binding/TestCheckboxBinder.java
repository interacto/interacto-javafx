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

import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class TestCheckboxBinder extends TestNodeBinder<CheckBox> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new CheckBox("cb1");
		widget2 = new CheckBox("cb2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleCBFunction(final FxRobot robot) {
		binding = Bindings.checkboxBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSingleCBSupplier(final FxRobot robot) {
		binding = Bindings.checkboxBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoCheckboxes(final FxRobot robot) {
		binding = Bindings.checkboxBinder()
			.toProduce(i -> cmd)
			.on(widget1, widget2)
			.bind();
		robot.clickOn(widget2);
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		binding = Bindings.checkboxBinder()
			.toProduce(i -> cmd)
			.first(c -> c.exec.setValue(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		binding = Bindings.checkboxBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		robot.clickOn(widget1);
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		binding = Bindings.checkboxBinder()
			.on(widget1)
			.when(i -> false)
			.toProduce(i -> cmd)
			.bind();
		robot.clickOn(widget1);
		assertEquals(0, cmd.exec.get());
		assertNotNull(binding);
	}
}

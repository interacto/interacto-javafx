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

import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
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
@ExtendWith(WidgetBindingExtension.class)
public class TestCheckboxBinder extends TestNodeBinder<CheckBox> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new CheckBox("cb1");
		widget2 = new CheckBox("cb2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleCBFunction(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.checkboxBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		ctx.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSingleCBSupplier(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.checkboxBinder()
			.toProduce(() -> new StubCmd())
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testCommandExecutedOnTwoCheckboxes(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.checkboxBinder()
			.toProduce(StubCmd::new)
			.on(widget1, widget2)
			.bind();
		robot.clickOn(widget2);
		robot.clickOn(widget1);
		assertEquals(2, binding.getTimesEnded());
		ctx.cmdsProduced(2);
	}

	@Test
	public void testInit1Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.checkboxBinder()
			.toProduce(StubCmd::new)
			.first(c -> c.exec.set(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testInit2Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.checkboxBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.first((i, c) -> c.exec.set(10))
			.bind();
		robot.clickOn(widget1);
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testCheckFalse(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.checkboxBinder()
			.on(widget1)
			.when(i -> false)
			.toProduce(StubCmd::new)
			.bind();
		robot.clickOn(widget1);
		assertEquals(0, binding.getTimesEnded());
		ctx.noCmdProduced();
	}
}

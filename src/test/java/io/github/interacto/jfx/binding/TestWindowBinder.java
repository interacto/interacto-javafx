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

import io.github.interacto.jfx.interaction.library.KeyPressed;
import io.github.interacto.jfx.interaction.library.KeysPressed;
import io.github.interacto.jfx.test.BindingsAssert;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestWindowBinder extends TestBinder<Window> {
	@Start
	public void start(final Stage stage) {
		final VBox parent = new VBox();
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		stage.centerOnScreen();
		stage.requestFocus();
		widget1 = scene.getWindow();
	}

	@Test
	public void testCommandExecutedOnSingleWinFunction(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.windowBinder()
			.usingInteraction(KeyPressed::new)
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();

		robot.clickOn(widget1).type(KeyCode.C);
		bindingsAssert.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSingleWinSupplier(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.windowBinder()
			.usingInteraction(KeyPressed::new)
			.toProduce(() -> new StubCmd())
			.on(widget1)
			.bind();

		robot.clickOn(widget1).type(KeyCode.C);
		bindingsAssert.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testInit1Executed(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.windowBinder()
			.usingInteraction(KeyPressed::new)
			.toProduce(StubCmd::new)
			.first(c -> c.exec.set(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1).type(KeyCode.C);
		bindingsAssert.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testInit2Executed(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.windowBinder()
			.usingInteraction(KeysPressed::new)
			.toProduce(StubCmd::new)
			.on(widget1)
			.first((i, c) -> c.exec.set(10))
			.bind();
		robot.clickOn(widget1).type(KeyCode.C);
		bindingsAssert.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testCheckFalse(final FxRobot robot, final BindingsAssert bindingsAssert) {
		binding = Bindings.windowBinder()
			.usingInteraction(KeyPressed::new)
			.on(widget1)
			.when(i -> false)
			.toProduce(StubCmd::new)
			.bind();
		robot.clickOn(widget1).type(KeyCode.A);
		bindingsAssert.noCmdProduced();
	}
}

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

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.interacto.jfx.interaction.library.ButtonPressed;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
public class TestAnonCommandBinder extends BaseNodeBinderTest<Button> {
	@Mock A a;

	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Button("button1");
		widget2 = new Button("button2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButton(final FxRobot robot) {
		Bindings.anonCmdBinder(() -> a.foo())
			.usingInteraction(ButtonPressed::new)
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		Mockito.verify(a, Mockito.times(1)).foo();
	}

	@Test
	public void testCommandExecutedOnTwoButtons(final FxRobot robot) {
		Bindings.anonCmdBinder(() -> a.foo())
			.usingInteraction(ButtonPressed::new)
			.on(widget1, widget2)
			.bind();

		robot.clickOn(widget1);
		robot.clickOn(widget2);
		robot.clickOn(widget2);
		Mockito.verify(a, Mockito.times(3)).foo();
	}

	@Test
	void testExceptionNullCmd() {
		assertThrows(IllegalArgumentException.class, () -> Bindings.anonCmdBinder(null));
	}

	static class A {
		public void foo() {
		}
	}
}

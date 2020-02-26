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

import io.github.interacto.binding.WidgetBinding;
import io.github.interacto.jfx.interaction.library.Click;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestNodeBinder {
	Pane widget1;
	WidgetBinding<?> binding;

	@Start
	public void start(final Stage stage) {
		widget1 = new Pane();
		widget1.setMinHeight(10);
		widget1.setMinWidth(10);
		widget1.setMaxHeight(10);
		widget1.setMaxWidth(10);
		final VBox parent = new VBox();
		parent.getChildren().add(widget1);
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		stage.centerOnScreen();
		stage.requestFocus();
	}

	@AfterEach
	void tearDown() {
		if(binding != null) {
			binding.uninstallBinding();
		}
	}

	@Test
	void testDifferentOrderBuilder1(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.usingInteraction(Click::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	void testDifferentOrderBuilder2(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.on(widget1)
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(Click::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	void testDifferentOrderBuilder3(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.on(widget1)
			.usingInteraction(Click::new)
			.toProduce(TestBinder.StubCmd::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	void testDifferentOrderBuilder4(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.usingInteraction(Click::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
		assertNotNull(binding);
	}
}

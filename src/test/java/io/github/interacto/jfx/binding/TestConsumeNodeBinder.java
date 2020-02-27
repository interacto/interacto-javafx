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
package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.interaction.library.Press;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestConsumeNodeBinder {
	Pane widget1;
	Pane widget2;

	@Start
	void start(final Stage stage) {
		widget1 = new Pane();
		widget2 = new Pane();
		widget2.setMinWidth(10);
		widget2.setMinHeight(10);
		final VBox parent = new VBox();
		parent.getChildren().add(widget1);
		widget1.getChildren().add(widget2);
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		stage.centerOnScreen();
		stage.requestFocus();
	}

	@Test
	void testEventNotConsumed(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(TestBinder.StubCmd::new)
			.on(widget2)
			.bind();

		Bindings.nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.bind();

		robot.clickOn(widget2);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.cmdsProduced(2);
	}

	@Test
	void testEventConsumed(final FxRobot robot, final BindingsContext ctx) {
		final var b2 = Bindings.nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(TestBinder.StubCmd::new)
			.on(widget2)
			.consume()
			.bind();

		Bindings.nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.consume()
			.bind();

		robot.clickOn(widget2);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.cmdsProduced(1)
			.listAssert()
			.allSatisfy(cmd -> cmd.producedBy(b2));
	}

	@Test
	void testEventConsumedDifferentOrder(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.consume()
			.bind();

		final var b2 = Bindings.nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(TestBinder.StubCmd::new)
			.on(widget2)
			.consume()
			.bind();

		robot.clickOn(widget2);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.cmdsProduced(1)
			.listAssert()
			.allSatisfy(cmd -> cmd.producedBy(b2));
	}

	@Test
	void testEventConsumedClone(final FxRobot robot, final BindingsContext ctx) {
		final var base = Bindings.nodeBinder()
			.usingInteraction(Press::new)
			.toProduce(TestBinder.StubCmd::new)
			.consume();

		final var b2 = base.on(widget2).bind();

		base.on(widget1).bind();

		robot.clickOn(widget2);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.cmdsProduced(1)
			.listAssert()
			.allSatisfy(cmd -> cmd.producedBy(b2));
	}
}

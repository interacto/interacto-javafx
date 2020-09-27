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

import io.github.interacto.jfx.TimeoutWaiter;
import io.github.interacto.jfx.binding.api.LogLevel;
import io.github.interacto.jfx.command.ActivateInactivateInstruments;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.interaction.library.DragLock;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.robot.Motion;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestBindings implements TimeoutWaiter {
	Canvas canvas = new Canvas();
	JfxWidgetBinding<?, ?, ?> binding;

	@Start
	public void start(final Stage stage) {
		canvas = new Canvas();
		canvas.setWidth(10);
		canvas.setHeight(10);
		final VBox parent = new VBox();
		parent.getChildren().add(canvas);
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
		waitForTimeoutTransitions();
	}

	@Test
	void testBinder() {
		binding =
			Bindings.nodeBinder()
				.continuousExecution()
				.on(new Button())
				.on(canvas)
				.on(FXCollections.observableArrayList(new ToggleButton(), new CheckBox()))
				.log(LogLevel.BINDING)
				.strictStart()
				.throttle(11L)
				.when(() -> true)
				.usingInteraction(DnD::new)
				.cancel(i -> { })
				.endOrCancel(i -> { })
				.when(i -> true)
				.toProduce(ActivateInactivateInstruments::new)
				.end(c -> { })
				.then(c -> { })
				.bind();

		assertThat(binding).isNotNull();
		assertThat(binding.isContinuousCmdExec()).isTrue();
		assertThat(binding.isActivated()).isTrue();
		assertThat(binding.getInteraction()).isInstanceOf(DnD.class);
		assertThat(binding.produces()).isNotNull();
		assertThat(binding.isStrictStart()).isTrue();
	}

	@Test
	void testClickMustNotBlockDragLock(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.usingInteraction(DragLock::new)
			.on(canvas)
			.toProduce(TestBinder.StubCmd::new)
			.when(i -> i.getButton() == MouseButton.SECONDARY)
			.log(LogLevel.INTERACTION, LogLevel.BINDING, LogLevel.COMMAND)
			.strictStart()
			.bind();

		robot.clickOn(canvas, Motion.DEFAULT, MouseButton.PRIMARY);
		waitForTimeoutTransitions();
		robot.clickOn(canvas, Motion.DEFAULT, MouseButton.SECONDARY);
		robot.clickOn(canvas, Motion.DEFAULT, MouseButton.SECONDARY);
		robot.moveBy(1, 1);
		robot.clickOn(canvas, Motion.DEFAULT, MouseButton.SECONDARY);
		robot.clickOn(canvas, Motion.DEFAULT, MouseButton.SECONDARY);

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}
}

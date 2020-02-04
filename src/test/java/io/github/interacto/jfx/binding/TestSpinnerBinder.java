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

import io.github.interacto.jfx.TimeoutWaiter;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.SpinnerChangedFSM;
import io.github.interacto.jfx.robot.FxRobotSpinner;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import io.github.interacto.jfx.ui.SpinnerFixed;
import io.github.interacto.logging.LogLevel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestSpinnerBinder extends TestNodeBinder<Spinner<Double>> implements FxRobotSpinner, TimeoutWaiter {
	long timeout;

	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new SpinnerFixed<>(0, 100, 50);
		widget2 = new SpinnerFixed<>(0, 100, 50);
		super.start(stage);
	}

	@BeforeEach
	void setUp() {
		timeout = SpinnerChangedFSM.getTimeGap();
	}

	@AfterEach
	void tearDown() {
		SpinnerChangedFSM.setTimeGap(timeout);
	}

	@Test
	public void testCommandExecutedOnSingleSpinnerConsumer(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.spinnerBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testEndsOnEnd(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.spinnerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testEndsOnFirst(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.spinnerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.first((i, c) -> c.exec.set(10))
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testEndsOnThen(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.spinnerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.then((i, c) -> c.exec.incrementAndGet())
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(3, cmd.exec.get()));
	}

	@Test
	public void testContinuousThen(final FxRobot robot, final BindingsContext ctx) {
		SpinnerChangedFSM.setTimeGap(2000L);

		binding = Bindings.spinnerBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.then((i, c) -> c.exec.incrementAndGet())
			.bind();

		incrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		decrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(6, cmd.exec.get()));
	}

	@Test
	public void testContinuousThenTimeOut(final FxRobot robot, final BindingsContext ctx) {
		SpinnerChangedFSM.setTimeGap(2000L);

		binding = Bindings.spinnerBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();

		incrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		decrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		ctx.cmdsProduced(2);
	}

	@Test
	public void testEndsOnUIThread(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.spinnerBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.end(i -> assertTrue(Platform.isFxApplicationThread()))
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testupdatesOnUIThread(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.spinnerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.then((i, c) -> assertTrue(Platform.isFxApplicationThread()))
			.bind();

		incrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testBuilderCloned() {
		final var binder = Bindings.spinnerBinder();
		assertNotSame(binder, Bindings.spinnerBinder());
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()).first(c -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()).first((i, c) -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()).then(i -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()).then((i, c) -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()).strictStart());
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()).throttle(10L));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()).continuousExecution());
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(() -> new StubCmd()));
		assertNotSame(binder, Bindings.spinnerBinder().on(widget1));
		assertNotSame(binder, Bindings.spinnerBinder().on(FXCollections.observableArrayList(widget1)));
		assertNotSame(binder, Bindings.spinnerBinder().when(() -> false));
		assertNotSame(binder, Bindings.spinnerBinder().when(i -> false));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> new StubCmd()).end(i -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().help((Pane) null));
		assertNotSame(binder, Bindings.spinnerBinder().help((HelpAnimation) null));
		assertNotSame(binder, Bindings.spinnerBinder().async(null, null, null));
		assertNotSame(binder, Bindings.spinnerBinder().log(LogLevel.COMMAND));
		assertNotSame(binder, Bindings.spinnerBinder().cancel(i -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().endOrCancel(i -> { }));
	}
}

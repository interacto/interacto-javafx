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
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
public class TestShortcutBinder extends BaseNodeBinderTest<Canvas> implements TimeoutWaiter {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Canvas();
		widget2 = new Canvas();
		super.start(stage);
	}

	@BeforeEach
	void setUp() {
		WaitForAsyncUtils.waitForFxEvents();
		grabFocus(widget1);
	}

	@Test
	public void testNoCommandExecutedOnNoKey(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnKey(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.with(KeyCode.C)
			.toProduce(StubCmd::new)
			.on(widget1)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testCommandExecutedOnTwoKeys(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.with(KeyCode.C, KeyCode.CONTROL)
			.bind();
		robot.press(KeyCode.CONTROL).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testCommandExecutedOnThreeKeys(final FxRobot robot, final BindingsContext ctx) {
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.end(i -> cpt.incrementAndGet())
			.with(KeyCode.C, KeyCode.CONTROL)
			.on(widget1)
			.bind();
		robot.press(KeyCode.CONTROL).type(KeyCode.C).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.cmdsProduced(2);
	}

	@Test
	public void testNoCommandExecutedOnTwoKeysReleased(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.with(KeyCode.C, KeyCode.CONTROL)
			.bind();

		robot.type(KeyCode.CONTROL).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.noCmdProduced();
	}

	@Test
	public void testNoCommandExecutedOnBadKey(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.with(KeyCode.C)
			.bind();

		robot.type(KeyCode.A);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.noCmdProduced();
	}

	@Test
	public void testCommandExecutedOnTwoCanvas(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.with(KeyCode.C)
			.on(widget1, widget2)
			.bind();

		grabFocus(widget2);
		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		grabFocus(widget1);
		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.cmdsProduced(2);
	}

	@Test
	public void testInit1Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(() -> new StubCmd())
			.on(widget1)
			.first(c -> c.exec.set(10))
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testInit2Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.first((i, c) -> c.exec.set(20))
			.on(widget1)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(21, cmd.exec.get()));
	}

	@Test
	public void testCheckFalse(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.when(i -> false)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.noCmdProduced();
	}

	@Test
	@DisplayName("Registering same pane two times does not produce events twice")
	public void testDoubleRegistration(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.with(KeyCode.A, KeyCode.CONTROL)
			.bind();

		Bindings.shortcutBinder()
			.on(widget1)
			.with(KeyCode.U, KeyCode.CONTROL)
			.toProduce(i -> new StubCmd())
			.bind();

		robot.press(KeyCode.CONTROL, KeyCode.A).release(KeyCode.A).sleep(10L);
		WaitForAsyncUtils.waitForFxEvents();
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testTwoShortcutsWithWhen(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.with(KeyCode.X)
			.when(() -> true)
			.bind();

		final var binding2 = Bindings.shortcutBinder()
			.on(widget1)
			.with(KeyCode.C)
			.toProduce(StubCmd::new)
			.when(() -> true)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		ctx
			.cmdsProduced(2)
			.listAssert()
			.allSatisfy(cmd -> cmd
				.producedBy(binding2)
				.ofType(StubCmd.class));
	}


	@Test
	void testKeyBindingSingleChar(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.S)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.type(KeyCode.S);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testKeyBindingFailSingleChar(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.S)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.type(KeyCode.B);
		waitForTimeoutTransitions();
		ctx.noCmdProduced();
	}

	@Test
	void testKeyBindingSingleCharAndShift(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.S, KeyCode.SHIFT)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.SHIFT).type(KeyCode.S).release(KeyCode.SHIFT);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testKeyBindingTwoCharsAndCtrl(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.B, KeyCode.K, KeyCode.CONTROL)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.CONTROL).press(KeyCode.K).type(KeyCode.B).release(KeyCode.K).release(KeyCode.CONTROL);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testKeyBindingTwoCharsAndCtrlFail(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.B, KeyCode.K, KeyCode.CONTROL)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.CONTROL).type(KeyCode.K).release(KeyCode.CONTROL);
		waitForTimeoutTransitions();
		ctx.noCmdProduced();
	}

	@Test
	void testKeyBindingAlt(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.X, KeyCode.ALT)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.ALT).type(KeyCode.X).release(KeyCode.ALT);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testKeyBindingNoAlt(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.X)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.ALT).type(KeyCode.X).release(KeyCode.ALT);
		waitForTimeoutTransitions();
		ctx.noCmdProduced();
	}

	@Test
	void testKeyBindingCtrlAlt(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.X, KeyCode.ALT, KeyCode.CONTROL)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.CONTROL).press(KeyCode.ALT).type(KeyCode.X);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testKeyBindingMeta(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.META, KeyCode.Y)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.COMMAND).type(KeyCode.Y).release(KeyCode.COMMAND);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testKeyBindingNoMeta(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.META, KeyCode.Y)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.type(KeyCode.Y);
		waitForTimeoutTransitions();
		ctx.noCmdProduced();
	}

	@Test
	void testKeyBindingCmd(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.COMMAND, KeyCode.Y)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.COMMAND).type(KeyCode.Y).release(KeyCode.COMMAND);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testKeyBindingSingleCharAndShortcut(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.S, KeyCode.SHORTCUT)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.CONTROL).type(KeyCode.S).release(KeyCode.CONTROL);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testKeyBindingSingleCharAndShortcutKOLinux(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.S, KeyCode.SHORTCUT)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.COMMAND).type(KeyCode.S).release(KeyCode.COMMAND);
		waitForTimeoutTransitions();
		ctx.noCmdProduced();
	}

	@Test
	void testKeyBindingNoKey(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.type(KeyCode.S);
		waitForTimeoutTransitions();
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void recyclingKeyEventsShouldNotConsiderModifier(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.S, KeyCode.SHORTCUT)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.CONTROL).press(KeyCode.S).release(KeyCode.CONTROL)
			.release(KeyCode.S);
		waitForTimeoutTransitions();
		ctx.cmdsProduced(1);
	}

	@Test
	void recyclingKeyEventsShouldNotConsiderAlt(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.with(KeyCode.S, KeyCode.ALT)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.ALT).press(KeyCode.S).release(KeyCode.ALT)
			.release(KeyCode.S);
		waitForTimeoutTransitions();
		ctx.cmdsProduced(1);
	}

	@Test
	void recyclingKeyEventsShouldNotConsiderHome(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.on(widget1)
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.S, KeyCode.HOME)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.HOME).press(KeyCode.S).release(KeyCode.HOME)
			.release(KeyCode.S);
		waitForTimeoutTransitions();
		ctx.cmdsProduced(1);
	}


	@Test
	void recyclingKeyEventsShouldNotConsiderShift(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.E, KeyCode.SHIFT)
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.SHIFT).press(KeyCode.E).release(KeyCode.SHIFT)
			.release(KeyCode.E);
		waitForTimeoutTransitions();
		ctx.cmdsProduced(1);
	}

	@Disabled
	@Test
	void recyclingModifiersCanTriggerCommand(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.shortcutBinder()
			.toProduce(TestBinder.StubCmd::new)
			.with(KeyCode.S, KeyCode.SHORTCUT)
			.on(widget1)
			.log(LogLevel.INTERACTION)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		waitForTimeoutTransitions();
		robot.press(KeyCode.CONTROL).press(KeyCode.S).release(KeyCode.CONTROL)
			.type(KeyCode.CONTROL);
		waitForTimeoutTransitions();
		ctx.cmdsProduced(1);
	}
}

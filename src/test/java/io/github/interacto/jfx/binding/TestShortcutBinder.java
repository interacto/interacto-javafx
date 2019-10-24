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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
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
public class TestShortcutBinder extends TestNodeBinder<Canvas> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Canvas();
		widget2 = new Canvas();
		super.start(stage);
	}

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		WaitForAsyncUtils.waitForFxEvents();
		grabFocus(widget1);
	}

	@Test
	public void testNoCommandExecutedOnNoKey(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnKey(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.with(KeyCode.C)
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoKeys(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.with(KeyCode.C, KeyCode.CONTROL)
			.bind();
		robot.press(KeyCode.CONTROL).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnThreeKeys(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.end(i -> cpt.incrementAndGet())
			.with(KeyCode.C, KeyCode.CONTROL)
			.on(widget1)
			.bind();
		robot.press(KeyCode.CONTROL).type(KeyCode.C).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, cpt.get());
		assertNotNull(binding);
	}

	@Test
	public void testNoCommandExecutedOnTwoKeysReleased(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.with(KeyCode.C, KeyCode.CONTROL)
			.bind();

		robot.type(KeyCode.CONTROL).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testNoCommandExecutedOnBadKey(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.with(KeyCode.C)
			.bind();

		robot.type(KeyCode.A);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoCanvas(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.with(KeyCode.C)
			.on(widget1, widget2)
			.bind();

		grabFocus(widget2);
		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		grabFocus(widget1);
		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.first(c -> c.exec.setValue(10))
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.first((i, c) -> c.exec.setValue(20))
			.on(widget1)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(21, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.when(i -> false)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	@DisplayName("Registering same pane two times does not produce events twice")
	public void testDoubleRegistration(final FxRobot robot) {
		binding = Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.with(KeyCode.A, KeyCode.CONTROL)
			.bind();

		final var binding2 = Bindings.shortcutBinder()
			.on(widget1)
			.with(KeyCode.U, KeyCode.CONTROL)
			.toProduce(i -> cmd)
			.bind();

		robot.press(KeyCode.CONTROL, KeyCode.A).release(KeyCode.A).sleep(10L);
		WaitForAsyncUtils.waitForFxEvents();
		binding2.uninstallBinding();
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testTwoShortcutsWithWhen(final FxRobot robot) {
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

		final var producedCmds2 = new ArrayList<>();
		final var disposable2 = binding2.produces().subscribe(producedCmds2::add);
		disposable = binding.produces().subscribe(producedCmds::add);

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		binding2.uninstallBinding();
		disposable2.dispose();
		assertEquals(0, producedCmds.size());
		assertEquals(2, producedCmds2.size());
	}
}

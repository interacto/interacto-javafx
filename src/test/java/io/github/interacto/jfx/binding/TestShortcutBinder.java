package io.github.interacto.jfx.binding;

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
		Bindings.shortcutBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnKey(final FxRobot robot) {
		Bindings.shortcutBinder()
			.with(KeyCode.C)
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoKeys(final FxRobot robot) {
		Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.with(KeyCode.C, KeyCode.CONTROL)
			.bind();
		robot.press(KeyCode.CONTROL).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnThreeKeys(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();

		Bindings.shortcutBinder()
			.toProduce(StubCmd::new)
			.end(i -> cpt.incrementAndGet())
			.with(KeyCode.C, KeyCode.CONTROL)
			.on(widget1)
			.bind();
		robot.press(KeyCode.CONTROL).type(KeyCode.C).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, cpt.get());
	}

	@Test
	public void testNoCommandExecutedOnTwoKeysReleased(final FxRobot robot) {
		Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.with(KeyCode.C, KeyCode.CONTROL)
			.bind();

		robot.type(KeyCode.CONTROL).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
	}

	@Test
	public void testNoCommandExecutedOnBadKey(final FxRobot robot) {
		Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.with(KeyCode.C)
			.bind();

		robot.type(KeyCode.A);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoCanvas(final FxRobot robot) {
		Bindings.shortcutBinder()
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
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.first(c -> c.exec.setValue(10))
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.first((i, c) -> c.exec.setValue(20))
			.on(widget1)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(21, cmd.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		Bindings.shortcutBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.when(i -> false)
			.bind();

		robot.type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
	}

	@Test
	@DisplayName("Registering same pane two times does not produce events twice")
	public void testDoubleRegistration(final FxRobot robot) {
		Bindings.shortcutBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.with(KeyCode.A, KeyCode.CONTROL)
			.bind();

		Bindings.shortcutBinder()
			.on(widget1)
			.with(KeyCode.U, KeyCode.CONTROL)
			.toProduce(i -> cmd)
			.bind();

		robot.press(KeyCode.CONTROL, KeyCode.A).release(KeyCode.A).sleep(10L);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
	}
}

package org.malai.javafx.interaction.binding;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.malai.command.Command;
import org.malai.javafx.binding.KeyNodeBinder;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestKeyNodeBinder extends TestNodeBinder<Canvas> {
	@Override
	public void start(Stage stage) {
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
	public void testNoCommandExecutedOnNoKey() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).bind();
		type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
		assertEquals(1, ((StubCmd) instrument.lastCreatedCmd).exec.get());
	}

	@Test
	public void testCommandExecutedOnKey() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).with(KeyCode.C).bind();
		type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
		assertEquals(1, ((StubCmd) instrument.lastCreatedCmd).exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoKeys() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).with(KeyCode.C, KeyCode.CONTROL).bind();
		press(KeyCode.CONTROL).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
		assertEquals(1, ((StubCmd) instrument.lastCreatedCmd).exec.get());
	}

	@Test
	public void testCommandExecutedOnThreeKeys() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).with(KeyCode.C, KeyCode.CONTROL).bind();
		press(KeyCode.CONTROL).type(KeyCode.C).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testNoCommandExecutedOnTwoKeysReleased() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).with(KeyCode.C, KeyCode.CONTROL).bind();
		type(KeyCode.CONTROL).type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, instrument.exec.get());
	}

	@Test
	public void testNoCommandExecutedOnBadKey() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).with(KeyCode.C).bind();
		type(KeyCode.A);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, instrument.exec.get());
		assertNull(instrument.lastCreatedCmd);
	}

	@Test
	public void testCommandExecutedOnTwoCanvas() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1, widget2).with(KeyCode.C).bind();
		grabFocus(widget2);
		type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
		Command cmd = instrument.lastCreatedCmd;
		assertEquals(1, ((StubCmd) instrument.lastCreatedCmd).exec.get());
		grabFocus(widget1);
		type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, instrument.exec.get());
		assertEquals(1, ((StubCmd) instrument.lastCreatedCmd).exec.get());
		assertNotSame(cmd, instrument.lastCreatedCmd);
	}

	@Test
	public void testInit1Executed() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).first(a -> a.exec.setValue(10)).bind();
		type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
		assertEquals(11, ((StubCmd) instrument.lastCreatedCmd).exec.get());
	}

	@Test
	public void testInit2Executed() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).first((i, c) -> c.exec.setValue(20)).bind();
		type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
		assertEquals(21, ((StubCmd) instrument.lastCreatedCmd).exec.get());
	}

	@Test
	public void testCheckFalse() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).when(i -> false).bind();
		type(KeyCode.C);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, instrument.exec.get());
	}

	@Test
	@DisplayName("Registering same pane two times does not produce events twice")
	public void testDoubleRegistration() {
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).with(KeyCode.A, KeyCode.CONTROL).bind();
		new KeyNodeBinder<>(StubCmd::new, instrument).on(widget1).with(KeyCode.U, KeyCode.CONTROL).bind();
		press(KeyCode.CONTROL, KeyCode.A).release(KeyCode.A).sleep(10L);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
	}
}

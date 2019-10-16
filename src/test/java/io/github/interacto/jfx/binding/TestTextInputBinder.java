package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.TimeoutWaiter;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestTextInputBinder extends TestNodeBinder<TextInputControl> implements TimeoutWaiter {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new TextArea();
		widget2 = new TextField();
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleWidgetFunction(final FxRobot robot) {
		Bindings.textInputBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1).write("foo");
		waitForTimeoutTransitions();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnSingleWidgetSupplier(final FxRobot robot) {
		Bindings.textInputBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1).write("barrr");
		waitForTimeoutTransitions();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoWidgets(final FxRobot robot) {
		Bindings.textInputBinder()
			.toProduce(i -> cmd)
			.on(widget1, widget2)
			.bind();
		robot.clickOn(widget2).write("barrr");
		waitForTimeoutTransitions();
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		robot.clickOn(widget1).write("barrddddr");
		waitForTimeoutTransitions();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		Bindings.textInputBinder()
			.toProduce(i -> cmd)
			.first(c -> c.exec.setValue(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1).write("aaaa");
		waitForTimeoutTransitions();
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		Bindings.textInputBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		robot.clickOn(widget1).write("f");
		waitForTimeoutTransitions();
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		Bindings.textInputBinder()
			.on(widget1)
			.when(i -> false)
			.toProduce(i -> cmd)
			.bind();
		robot.clickOn(widget1).write("a");
		waitForTimeoutTransitions();
		assertEquals(0, cmd.exec.get());
	}
}

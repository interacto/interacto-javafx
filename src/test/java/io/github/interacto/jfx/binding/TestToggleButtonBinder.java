package io.github.interacto.jfx.binding;

import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class TestToggleButtonBinder extends TestNodeBinder<ToggleButton> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new ToggleButton("button1");
		widget2 = new ToggleButton("button2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButtonFunction(final FxRobot robot) {
		binding = Bindings.toggleButtonBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSingleButtonSupplier(final FxRobot robot) {
		binding = Bindings.toggleButtonBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.bind();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoButtons(final FxRobot robot) {
		binding = Bindings.toggleButtonBinder()
			.toProduce(i -> cmd)
			.on(widget1, widget2)
			.bind();
		robot.clickOn(widget2);
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		binding = Bindings.toggleButtonBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.first(c -> c.exec.setValue(10))
			.bind();
		robot.clickOn(widget1);
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		binding = Bindings.toggleButtonBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		robot.clickOn(widget1);
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		binding = Bindings.toggleButtonBinder()
			.on(widget1)
			.when(i -> false)
			.toProduce(i -> cmd)
			.bind();
		robot.clickOn(widget1);
		assertEquals(0, cmd.exec.get());
		assertNotNull(binding);
	}
}

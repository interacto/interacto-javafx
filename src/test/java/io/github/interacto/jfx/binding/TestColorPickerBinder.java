package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.robot.FxRobotColourPicker;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestColorPickerBinder extends TestNodeBinder<ColorPicker> implements FxRobotColourPicker {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new ColorPicker(Color.BLUE);
		widget2 = new ColorPicker(Color.BLUE);
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButtonFunction(final FxRobot robot) {
		Bindings.colorPickerBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnSingleButtonSupplier(final FxRobot robot) {
		Bindings.colorPickerBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoComboboxes(final FxRobot robot) {
		Bindings.colorPickerBinder()
			.on(widget1, widget2)
			.toProduce(i -> cmd)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		pickColour(robot, widget2);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		Bindings.colorPickerBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.first(c -> c.exec.setValue(10))
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		Bindings.colorPickerBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		Bindings.colorPickerBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.when(i -> false)
			.bind();
		pickColour(robot, widget1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, cmd.exec.get());
	}
}

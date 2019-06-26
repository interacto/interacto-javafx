package io.interacto.jfx.interaction.binding;

import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.interacto.jfx.binding.ToggleButtonBinder;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	public void testCommandExecutedOnSingleButton(final FxRobot robot) {
		new ToggleButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		robot.clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoButtons(final FxRobot robot) {
		new ToggleButtonBinder<>(StubCmd::new, instrument).
			on(widget1, widget2).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		robot.clickOn(widget2);
		assertEquals(1, instrument.exec.get());
		robot.clickOn(widget1);
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		new ToggleButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			first(c -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		robot.clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		new ToggleButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			first((i, c) -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		robot.clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		new ToggleButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			when(i -> false).
			bind();
		robot.clickOn(widget1);
		assertEquals(0, instrument.exec.get());
	}
}

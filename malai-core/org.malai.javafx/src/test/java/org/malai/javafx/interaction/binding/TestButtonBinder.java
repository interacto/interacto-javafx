package org.malai.javafx.interaction.binding;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.binding.ButtonBinder;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestButtonBinder extends TestNodeBinder<Button> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Button("button1");
		widget2 = new Button("button2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButton(final FxRobot robot) {
		new ButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		robot.clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testIsOnUIThread(final FxRobot robot) {
		new ButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertTrue(Platform.isFxApplicationThread())).
			bind();
		robot.clickOn(widget1);
	}

	@Test
	public void testCommandExecutedOnTwoButtons(final FxRobot robot) {
		new ButtonBinder<>(StubCmd::new, instrument).
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
		new ButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			first(c -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		robot.clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		new ButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			first((i, c) -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		robot.clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		new ButtonBinder<>(StubCmd::new, instrument).
			on(widget1).
			when(i -> false).
			bind();
		robot.clickOn(widget1);
		assertEquals(0, instrument.exec.get());
	}
}

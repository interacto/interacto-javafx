package org.malai.javafx.interaction.binding;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.binding.ComboBoxBinder;
import org.malai.javafx.robot.FxRobotListSelection;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestComboboxBinder extends TestNodeBinder<ComboBox<String>> implements FxRobotListSelection {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new ComboBox<>(FXCollections.observableArrayList("a", "b"));
		widget2 = new ComboBox<>(FXCollections.observableArrayList("c", "d"));
		widget1.getSelectionModel().select(0);
		widget2.getSelectionModel().select(0);
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButton(final FxRobot robot) {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoComboboxes(final FxRobot robot) {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1, widget2).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		selectGivenComboBoxItem(robot, widget2, "d");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			first(c -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			first((i, c) -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			when(i -> false).
			bind();
		selectGivenComboBoxItem(robot, widget1, "b");
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, instrument.exec.get());
	}
}

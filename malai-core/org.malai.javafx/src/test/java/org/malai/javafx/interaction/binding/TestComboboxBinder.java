package org.malai.javafx.interaction.binding;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.malai.javafx.binding.ComboBoxBinder;
import org.malai.javafx.robot.FxRobotListSelection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestComboboxBinder extends TestNodeBinder<ComboBox<String>> implements FxRobotListSelection {
	@Override
	public void start(Stage stage) {
		widget1 = new ComboBox<>(FXCollections.observableArrayList("a", "b"));
		widget2 = new ComboBox<>(FXCollections.observableArrayList("c", "d"));
		widget1.getSelectionModel().select(0);
		widget2.getSelectionModel().select(0);
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButton() {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoComboboxes() {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1, widget2).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		selectGivenComboBoxItem(widget2, "d");
		assertEquals(1, instrument.exec.get());
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed() {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			first(c -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed() {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			first((i, c) -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse() {
		new ComboBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			when(i -> false).
			bind();
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(0, instrument.exec.get());
	}
}

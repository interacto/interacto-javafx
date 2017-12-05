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
	public void testActionExecutedOnSingleButton() throws InstantiationException, IllegalAccessException {
		new ComboBoxBinder<>(StubAction.class, instrument).
			on(widget1).
			end((a, i) -> assertEquals(1, a.exec.get())).
			bind();
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testActionExecutedOnTwoComboboxes() throws InstantiationException, IllegalAccessException {
		new ComboBoxBinder<>(StubAction.class, instrument).
			on(widget1, widget2).
			end((a, i) -> assertEquals(1, a.exec.get())).
			bind();
		selectGivenComboBoxItem(widget2, "d");
		assertEquals(1, instrument.exec.get());
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed() throws InstantiationException, IllegalAccessException {
		new ComboBoxBinder<>(StubAction.class, instrument).
			on(widget1).
			first(a -> a.exec.setValue(10)).
			end((a, i) -> assertEquals(11, a.exec.get())).
			bind();
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed() throws InstantiationException, IllegalAccessException {
		new ComboBoxBinder<>(StubAction.class, instrument).
			on(widget1).
			first((a, i) -> a.exec.setValue(10)).
			end((a, i) -> assertEquals(11, a.exec.get())).
			bind();
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse() throws InstantiationException, IllegalAccessException {
		new ComboBoxBinder<>(StubAction.class, instrument).
			on(widget1).
			when(i -> false).
			bind();
		selectGivenComboBoxItem(widget1, "b");
		assertEquals(0, instrument.exec.get());
	}
}

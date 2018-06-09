package org.malai.javafx.interaction.binding;

import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.malai.javafx.binding.CheckBoxBinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCheckboxBinder extends TestNodeBinder<CheckBox> {
	@Override
	public void start(Stage stage) {
		widget1 = new CheckBox("cb1");
		widget2 = new CheckBox("cb2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButton() {
		new CheckBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoCheckboxes() {
		new CheckBoxBinder<>(StubCmd::new, instrument).
			on(widget1, widget2).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		clickOn(widget2);
		assertEquals(1, instrument.exec.get());
		clickOn(widget1);
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed() {
		new CheckBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			first(c -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed() {
		new CheckBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			first((i, c) -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse() {
		new CheckBoxBinder<>(StubCmd::new, instrument).
			on(widget1).
			when(i -> false).
			bind();
		clickOn(widget1);
		assertEquals(0, instrument.exec.get());
	}
}

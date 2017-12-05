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
	public void testActionExecutedOnSingleButton() throws InstantiationException, IllegalAccessException {
		new CheckBoxBinder<>(StubAction.class, instrument).
			on(widget1).
			end((a, i) -> assertEquals(1, a.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testActionExecutedOnTwoCheckboxes() throws InstantiationException, IllegalAccessException {
		new CheckBoxBinder<>(StubAction.class, instrument).
			on(widget1).on(widget2).
			end((a, i) -> assertEquals(1, a.exec.get())).
			bind();
		clickOn(widget2);
		assertEquals(1, instrument.exec.get());
		clickOn(widget1);
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed() throws InstantiationException, IllegalAccessException {
		new CheckBoxBinder<>(StubAction.class, instrument).
			on(widget1).
			first(a -> a.exec.setValue(10)).
			end((a, i) -> assertEquals(11, a.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed() throws InstantiationException, IllegalAccessException {
		new CheckBoxBinder<>(StubAction.class, instrument).
			on(widget1).
			first((a, i) -> a.exec.setValue(10)).
			end((a, i) -> assertEquals(11, a.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse() throws InstantiationException, IllegalAccessException {
		new CheckBoxBinder<>(StubAction.class, instrument).
			on(widget1).
			when(i -> false).
			bind();
		clickOn(widget1);
		assertEquals(0, instrument.exec.get());
	}
}

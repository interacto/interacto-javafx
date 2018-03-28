package org.malai.javafx.interaction.binding;

import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.malai.javafx.binding.ToggleButtonBinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestToggleButtonBinder extends TestNodeBinder<ToggleButton> {
	@Override
	public void start(Stage stage) {
		widget1 = new ToggleButton("button1");
		widget2 = new ToggleButton("button2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButton() {
		new ToggleButtonBinder<>(StubCmd.class, instrument).
			on(widget1).
			end((c, i) -> assertEquals(1, c.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoButtons() {
		new ToggleButtonBinder<>(StubCmd.class, instrument).
			on(widget1, widget2).
			end((c, i) -> assertEquals(1, c.exec.get())).
			bind();
		clickOn(widget2);
		assertEquals(1, instrument.exec.get());
		clickOn(widget1);
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed() {
		new ToggleButtonBinder<>(StubCmd.class, instrument).
			on(widget1).
			first(c -> c.exec.setValue(10)).
			end((c, i) -> assertEquals(11, c.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed() {
		new ToggleButtonBinder<>(StubCmd.class, instrument).
			on(widget1).
			first((c, i) -> c.exec.setValue(10)).
			end((c, i) -> assertEquals(11, c.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse() {
		new ToggleButtonBinder<>(StubCmd.class, instrument).
			on(widget1).
			when(i -> false).
			bind();
		clickOn(widget1);
		assertEquals(0, instrument.exec.get());
	}
}

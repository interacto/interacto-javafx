package org.malai.javafx.interaction.binding;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.malai.javafx.binding.ButtonBinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestButtonBinder extends TestNodeBinder<Button> {
	@Override
	public void start(Stage stage) {
		widget1 = new Button("button1");
		widget2 = new Button("button2");
		super.start(stage);
	}

	@Test
	public void testActionExecutedOnSingleButton() {
		new ButtonBinder<>(StubAction.class, instrument).
			on(widget1).
			end((a, i) -> assertEquals(1, a.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testActionExecutedOnTwoButtons() {
		new ButtonBinder<>(StubAction.class, instrument).
			on(widget1, widget2).
			end((a, i) -> assertEquals(1, a.exec.get())).
			bind();
		clickOn(widget2);
		assertEquals(1, instrument.exec.get());
		clickOn(widget1);
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed() {
		new ButtonBinder<>(StubAction.class, instrument).
			on(widget1).
			first(a -> a.exec.setValue(10)).
			end((a, i) -> assertEquals(11, a.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed() {
		new ButtonBinder<>(StubAction.class, instrument).
			on(widget1).
			first((a, i) -> a.exec.setValue(10)).
			end((a, i) -> assertEquals(11, a.exec.get())).
			bind();
		clickOn(widget1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse() {
		new ButtonBinder<>(StubAction.class, instrument).
			on(widget1).
			when(i -> false).
			bind();
		clickOn(widget1);
		assertEquals(0, instrument.exec.get());
	}
}

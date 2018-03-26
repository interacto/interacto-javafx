package org.malai.javafx.interaction.binding;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.javafx.binding.AnonActionBinder;
import org.malai.javafx.interaction.library.ButtonPressed;
import org.mockito.Mock;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class TestAnonActionBinder extends TestNodeBinder<Button> {
	@Mock A a;

	@Override
	public void start(Stage stage) {
		widget1 = new Button("button1");
		widget2 = new Button("button2");
		super.start(stage);
	}

	@Test
	public void testActionExecutedOnSingleButton() {
		new AnonActionBinder<>(() -> a.foo(), new ButtonPressed(), instrument).on(widget1).bind();
		clickOn(widget1);
		Mockito.verify(a, Mockito.times(1)).foo();
	}

	static class A {
		public void foo() {
		}
	}
}

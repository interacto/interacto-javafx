package io.github.interacto.jfx.interaction.binding;

import io.github.interacto.jfx.binding.AnonCmdBinder;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.interacto.jfx.interaction.library.ButtonPressed;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
public class TestAnonCommandBinder extends TestNodeBinder<Button> {
	@Mock A a;

	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Button("button1");
		widget2 = new Button("button2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButton(final FxRobot robot) {
		new AnonCmdBinder<>(new ButtonPressed(), () -> a.foo(), instrument).on(widget1).bind();
		robot.clickOn(widget1);
		Mockito.verify(a, Mockito.times(1)).foo();
	}

	static class A {
		public void foo() {
		}
	}
}

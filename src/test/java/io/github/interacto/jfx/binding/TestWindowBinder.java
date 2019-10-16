package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.interaction.library.KeyPressed;
import io.github.interacto.jfx.interaction.library.KeysPressed;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestWindowBinder extends TestBinder<Window> {
	@Start
	public void start(final Stage stage) {
		instrument = new StubInstrument();
		instrument.setActivated(true);
		final VBox parent = new VBox();
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		stage.centerOnScreen();
		stage.requestFocus();
		widget1 = scene.getWindow();
	}

	@Test
	public void testCommandExecutedOnSingleWinFunction(final FxRobot robot) {
		Bindings.windowBinder()
			.usingInteraction(KeyPressed::new)
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();

		robot.clickOn(widget1).type(KeyCode.C);
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnSingleWinSupplier(final FxRobot robot) {
		Bindings.windowBinder()
			.usingInteraction(KeyPressed::new)
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();

		robot.clickOn(widget1).type(KeyCode.C);
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		Bindings.windowBinder()
			.usingInteraction(KeyPressed::new)
			.toProduce(i -> cmd)
			.first(c -> c.exec.setValue(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1).type(KeyCode.C);
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		Bindings.windowBinder()
			.usingInteraction(KeysPressed::new)
			.toProduce(() -> cmd)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		robot.clickOn(widget1).type(KeyCode.C);
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		Bindings.windowBinder()
			.usingInteraction(KeyPressed::new)
			.on(widget1)
			.when(i -> false)
			.toProduce(i -> cmd)
			.bind();
		robot.clickOn(widget1).type(KeyCode.A);
		assertEquals(0, cmd.exec.get());
	}
}

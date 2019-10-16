package io.github.interacto.jfx.binding;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestButtonBinder extends TestNodeBinder<Button> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Button("button1");
		widget2 = new Button("button2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButtonConsumer(final FxRobot robot) {
		Bindings.buttonBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnSingleButtonFunction(final FxRobot robot) {
		Bindings.buttonBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.bind();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testIsOnUIThread(final FxRobot robot) {
		Bindings.buttonBinder()
			.on(widget1)
			.end(i -> assertTrue(Platform.isFxApplicationThread()))
			.toProduce(() -> cmd)
			.bind();
		robot.clickOn(widget1);
	}

	@Test
	public void testCommandExecutedOnTwoButtons(final FxRobot robot) {
		Bindings.buttonBinder()
			.toProduce(() -> cmd)
			.on(widget1, widget2)
			.bind();

		robot.clickOn(widget2);
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCollectionButtons(final FxRobot robot) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1, widget2);
		final AtomicInteger cpt = new AtomicInteger();

		Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.on(buttons)
			.end(i -> cpt.incrementAndGet())
			.bind();

		robot.clickOn(widget2);
		robot.clickOn(widget1);
		assertEquals(2, cpt.get());
	}

	@Test
	public void testCollectionButtonsRemove(final FxRobot robot) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1, widget2);
		final AtomicInteger cpt = new AtomicInteger();

		Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.on(buttons)
			.end(i -> cpt.incrementAndGet())
			.bind();

		buttons.remove(widget1);

		robot.clickOn(widget2);
		robot.clickOn(widget1);
		assertEquals(1, cpt.get());
	}

	@Test
	public void testCollectionButtonsAdd(final FxRobot robot) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1);
		final AtomicInteger cpt = new AtomicInteger();

		Bindings.buttonBinder()
			.on(buttons)
			.toProduce(StubCmd::new)
			.end(i -> cpt.incrementAndGet())
			.bind();

		buttons.add(widget2);

		robot.clickOn(widget2);
		assertEquals(1, cpt.get());
	}


	@Test
	public void testInit1Executed(final FxRobot robot) {
		Bindings.buttonBinder()
			.on(widget1)
			.toProduce(() -> cmd)
			.first(c -> c.exec.setValue(10))
			.bind();
		robot.clickOn(widget1);
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		Bindings.buttonBinder()
			.toProduce(i -> cmd)
			.first((i, c) -> c.exec.setValue(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		Bindings.buttonBinder()
			.toProduce(i -> cmd)
			.when(i -> false)
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(0, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoButtonsSame(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();

		Bindings.buttonBinder()
			.on(widget1, widget1)
			.toProduce(() -> cmd)
			.end(i -> cpt.incrementAndGet())
			.bind();

		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertEquals(1, cpt.get());
	}
}

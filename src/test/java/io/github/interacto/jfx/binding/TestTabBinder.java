package io.github.interacto.jfx.binding;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestTabBinder extends TestNodeBinder<TabPane> {
	Tab tab1;
	Tab tab2;
	Tab tab3;
	Tab tab4;

	@Override
	@Start
	public void start(final Stage stage) {
		tab1 = new Tab("tab1");
		tab2 = new Tab("tab2");
		tab3 = new Tab("tab3");
		tab4 = new Tab("tab4");
		tab1.setId("t1");
		tab2.setId("t2");
		tab3.setId("t3");
		tab4.setId("t4");
		widget1 = new TabPane(tab1, tab2);
		widget2 = new TabPane(tab3, tab4);
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleTabConsumer(final FxRobot robot) {
		Bindings.tabBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnSingleTabFunction(final FxRobot robot) {
		Bindings.tabBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testIsOnUIThread(final FxRobot robot) {
		Bindings.tabBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.end(i -> assertTrue(Platform.isFxApplicationThread()))
			.bind();
		robot.clickOn(widget1.lookup("#t1"));
	}

	@Test
	public void testCommandExecutedOnTwoButtons(final FxRobot robot) {
		Bindings.tabBinder()
			.on(widget1, widget2)
			.toProduce(() -> cmd)
			.bind();

		robot.clickOn(widget2.lookup("#t4"));
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		widget1.getSelectionModel().select(1);
		robot.clickOn(widget1.lookup("#t1"));
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoButtonsTwoOn(final FxRobot robot) {
		Bindings.tabBinder()
			.on(widget1)
			.toProduce(() -> cmd)
			.on(widget2)
			.bind();

		robot.clickOn(widget2.lookup("#t4"));
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		widget1.getSelectionModel().select(1);
		robot.clickOn(widget1.lookup("#t1"));
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		Bindings.tabBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.first(c -> c.exec.setValue(10))
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		Bindings.tabBinder()
			.toProduce(i -> cmd)
			.first((i, c) -> c.exec.setValue(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		Bindings.tabBinder()
			.when(i -> false)
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn(widget1.lookup("#t1"));
		assertEquals(0, cmd.exec.get());
	}
}

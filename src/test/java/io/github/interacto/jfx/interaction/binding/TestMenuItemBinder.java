package io.github.interacto.jfx.interaction.binding;

import io.github.interacto.jfx.binding.MenuItemBinder;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestMenuItemBinder extends TestBinder<MenuItem> {
	Menu menu;
	final String menuID = "menu";
	final String menuitemID1 = "menuitemID1";
	final String menuitemID2 = "menuitemID2";

	@Start
	public void start(final Stage stage) {
		widget1 = new MenuItem("menu1");
		widget2 = new MenuItem("menu2");
		widget1.setId(menuitemID1);
		widget2.setId(menuitemID2);
		instrument = new StubInstrument();
		instrument.setActivated(true);
		final VBox parent = new VBox();
		menu = new Menu("menu");
		menu.setId(menuID);
		final MenuBar menubar = new MenuBar();
		menu.getItems().addAll(widget1, widget2);
		menubar.getMenus().addAll(menu);
		parent.getChildren().add(menubar);
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
	}

	@Test
	public void testCommandExecutedOnSingleButton(final FxRobot robot) {
		new MenuItemBinder<>(i -> cmd, instrument)
			.on(widget1)
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(1, cmd.exec.get());
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoMenus(final FxRobot robot) {
		new MenuItemBinder<>(i -> cmd, instrument)
			.on(widget1, widget2)
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		assertEquals(1, instrument.exec.get());
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed(final FxRobot robot) {
		new MenuItemBinder<>(i -> cmd, instrument)
			.on(widget1)
			.first(c -> c.exec.setValue(10))
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(11, cmd.exec.get());
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		new MenuItemBinder<>(i -> cmd, instrument)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(11, cmd.exec.get());
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		new MenuItemBinder<>(i -> cmd, instrument)
			.on(widget1)
			.when(i -> false)
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(0, instrument.exec.get());
	}
}

package org.malai.javafx.interaction.binding;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.malai.javafx.binding.MenuItemBinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMenuItemBinder extends TestBinder<MenuItem> {
	Menu menu;
	final String menuID = "menu";
	final String menuitemID1 = "menuitemID1";
	final String menuitemID2 = "menuitemID2";

	@Override
	public void start(Stage stage) {
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
	public void testCommandExecutedOnSingleButton() {
		new MenuItemBinder<>(StubCmd::new, instrument).
			on(widget1).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		clickOn("#"+menuID).clickOn("#"+menuitemID1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoMenus() {
		new MenuItemBinder<>(StubCmd::new, instrument).
			on(widget1, widget2).
			end((i, c) -> assertEquals(1, c.exec.get())).
			bind();
		clickOn("#"+menuID).clickOn("#"+menuitemID2);
		assertEquals(1, instrument.exec.get());
		clickOn("#"+menuID).clickOn("#"+menuitemID1);
		assertEquals(2, instrument.exec.get());
	}

	@Test
	public void testInit1Executed() {
		new MenuItemBinder<>(StubCmd::new, instrument).
			on(widget1).
			first(c -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		clickOn("#"+menuID).clickOn("#"+menuitemID1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testInit2Executed() {
		new MenuItemBinder<>(StubCmd::new, instrument).
			on(widget1).
			first((i, c) -> c.exec.setValue(10)).
			end((i, c) -> assertEquals(11, c.exec.get())).
			bind();
		clickOn("#"+menuID).clickOn("#"+menuitemID1);
		assertEquals(1, instrument.exec.get());
	}

	@Test
	public void testCheckFalse() {
		new MenuItemBinder<>(StubCmd::new, instrument).
			on(widget1).
			when(i -> false).
			bind();
		clickOn("#"+menuID).clickOn("#"+menuitemID1);
		assertEquals(0, instrument.exec.get());
	}
}

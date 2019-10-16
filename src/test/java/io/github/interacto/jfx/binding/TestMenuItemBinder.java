package io.github.interacto.jfx.binding;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
		Bindings.menuItemBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCommandExecutedOnTwoMenus(final FxRobot robot) {
		Bindings.menuItemBinder()
			.toProduce(i -> cmd)
			.on(widget1, widget2)
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testCollectionButtons(final FxRobot robot) {
		final ObservableList<MenuItem> menus = FXCollections.observableArrayList(widget1, widget2);
		final AtomicInteger cpt = new AtomicInteger();

		Bindings.menuItemBinder()
			.on(menus)
			.end(i -> cpt.incrementAndGet())
			.toProduce(StubCmd::new)
			.bind();

		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(2, cpt.get());
	}

	@Test
	public void testCollectionButtonsRemove(final FxRobot robot) {
		final ObservableList<MenuItem> menus = FXCollections.observableArrayList(widget1, widget2);
		final AtomicInteger cpt = new AtomicInteger();

		Bindings.menuItemBinder()
			.on(menus)
			.toProduce(StubCmd::new)
			.end(i -> cpt.incrementAndGet())
			.bind();

		menus.remove(widget1);

		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(1, cpt.get());
	}

	@Test
	public void testCollectionButtonsAdd(final FxRobot robot) {
		final ObservableList<MenuItem> menus = FXCollections.observableArrayList(widget1);
		final AtomicInteger cpt = new AtomicInteger();

		Bindings.menuItemBinder()
			.toProduce(StubCmd::new)
			.on(menus)
			.end(i -> cpt.incrementAndGet())
			.bind();

		menus.add(widget2);

		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		assertEquals(1, cpt.get());
	}


	@Test
	public void testInit1Executed(final FxRobot robot) {
		Bindings.menuItemBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.first(c -> c.exec.setValue(10))
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		Bindings.menuItemBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.first((i, c) -> c.exec.setValue(10))
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(11, cmd.exec.get());
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		Bindings.menuItemBinder()
			.on(widget1)
			.when(i -> false)
			.toProduce(i -> cmd)
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(0, instrument.exec.get());
	}
}

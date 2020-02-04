/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
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
	public void testCommandExecutedOnSinglMenu(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.menuItemBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		ctx.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoMenus(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.menuItemBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1, widget2)
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(2, binding.getTimesEnded());
		ctx.cmdsProduced(2);
	}

	@Test
	public void testCollectionMenus(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.menuItemBinder()
			.on(FXCollections.observableArrayList(widget1, widget2))
			.toProduce(StubCmd::new)
			.bind();

		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(2, binding.getTimesEnded());
		ctx.cmdsProduced(2);
	}

	@Test
	public void testCollectionMenusRemove(final FxRobot robot, final BindingsContext ctx) {
		final ObservableList<MenuItem> menus = FXCollections.observableArrayList(widget1, widget2);

		binding = Bindings.menuItemBinder()
			.on(menus)
			.toProduce(StubCmd::new)
			.bind();

		menus.remove(widget1);

		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(1, binding.getTimesEnded());
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testCollectionMenusAdd(final FxRobot robot, final BindingsContext ctx) {
		final ObservableList<MenuItem> menus = FXCollections.observableArrayList(widget1);

		binding = Bindings.menuItemBinder()
			.toProduce(StubCmd::new)
			.on(menus)
			.bind();

		menus.add(widget2);

		robot.clickOn("#" + menuID).clickOn("#" + menuitemID2);
		ctx.oneCmdProduced(StubCmd.class);
	}


	@Test
	public void testInit1Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.menuItemBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.first(c -> c.exec.set(10))
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testInit2Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.menuItemBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.first((i, c) -> c.exec.set(10))
			.bind();
		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testCheckFalse(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.menuItemBinder()
			.on(widget1)
			.when(i -> false)
			.toProduce(StubCmd::new)
			.bind();

		robot.clickOn("#" + menuID).clickOn("#" + menuitemID1);
		assertEquals(0, binding.getTimesEnded());
		ctx.noCmdProduced();
	}
}

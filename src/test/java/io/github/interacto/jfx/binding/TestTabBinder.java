/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
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
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestTabBinder extends BaseNodeBinderTest<TabPane> {
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
	public void testCommandExecutedOnSingleTabConsumer(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.tabBinder()
			.toProduce(() -> new StubCmd())
			.on(widget1)
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		ctx.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSingleTabFunction(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.tabBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testIsOnUIThread(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.tabBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.end(i -> assertTrue(Platform.isFxApplicationThread()))
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		WaitForAsyncUtils.waitForFxEvents();
		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testCommandExecutedOnTwoTabs(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.tabBinder()
			.on(widget1, widget2)
			.toProduce(StubCmd::new)
			.bind();

		robot.clickOn(widget2.lookup("#t4"));
		widget1.getSelectionModel().select(1);
		robot.clickOn(widget1.lookup("#t2"));
		ctx.cmdsProduced(2);
	}

	@Test
	public void testCommandExecutedOnTwoTabsTwoOn(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.tabBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.on(widget2)
			.bind();

		robot.clickOn(widget2.lookup("#t4"));
		widget1.getSelectionModel().select(1);
		robot.clickOn(widget1.lookup("#t2"));
		ctx.cmdsProduced(2);
	}

	@Test
	public void testInit1Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.tabBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.first(c -> c.exec.set(10))
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testInit2Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.tabBinder()
			.toProduce(StubCmd::new)
			.first((i, c) -> c.exec.set(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1.lookup("#t2"));
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testCheckFalse(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.tabBinder()
			.when(i -> false)
			.toProduce(StubCmd::new)
			.on(widget1)
			.bind();
		robot.clickOn(widget1.lookup("#t1"));
		ctx.noCmdProduced();
	}
}

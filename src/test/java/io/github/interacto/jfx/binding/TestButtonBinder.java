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

import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import io.github.interacto.logging.LogLevel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestButtonBinder extends BaseNodeBinderTest<Button> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Button("button1");
		widget2 = new Button("button2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButtonConsumer(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.bind();

		robot.clickOn(widget1);

		ctx.oneCmdProduced(StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnSingleButtonFunction(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.buttonBinder()
			.on(widget1)
			.toProduce(i -> new StubCmd())
			.bind();

		robot.clickOn(widget1);

		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testIsOnUIThread(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.buttonBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.end(c -> assertTrue(Platform.isFxApplicationThread()))
			.bind();

		robot.clickOn(widget1);

		ctx.oneCmdProduced(StubCmd.class);
	}

	@Test
	public void testCommandExecutedOnTwoButtons(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.on(widget1, widget2)
			.bind();

		robot.clickOn(widget2);
		robot.clickOn(widget1);

		ctx.cmdsProduced(2)
			.listAssert()
			.extracting(elt -> elt.getCommand())
			.allMatch(cmd -> cmd.isDone())
			.doesNotHaveDuplicates();
	}

	@Test
	public void testCollectionButtons(final FxRobot robot, final BindingsContext ctx) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1, widget2);

		binding = Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.on(buttons)
			.bind();

		robot.clickOn(widget2);
		robot.clickOn(widget1);
		ctx.cmdsProduced(2);
		assertEquals(2, binding.getTimesEnded());
	}

	@Test
	public void testCollectionButtonsRemove(final FxRobot robot, final BindingsContext ctx) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1, widget2);

		binding = Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.on(buttons)
			.bind();

		buttons.remove(widget1);

		robot.clickOn(widget2);
		robot.clickOn(widget1);
		ctx.oneCmdProduced(StubCmd.class);
		assertEquals(1, binding.getTimesEnded());
	}

	@Test
	public void testCollectionButtonsAdd(final FxRobot robot, final BindingsContext ctx) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1);

		binding = Bindings.buttonBinder()
			.on(buttons)
			.toProduce(StubCmd::new)
			.bind();

		buttons.add(widget2);

		robot.clickOn(widget2);
		ctx.oneCmdProduced(StubCmd.class);
		assertEquals(1, binding.getTimesEnded());
	}


	@Test
	public void testInit1Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.buttonBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.first(c -> c.exec.set(10))
			.bind();
		robot.clickOn(widget1);
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testInit2Executed(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.first((i, c) -> c.exec.set(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		ctx.oneCmdProduced(StubCmd.class, cmd -> assertEquals(11, cmd.exec.get()));
	}

	@Test
	public void testCheckFalse(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.when(i -> false)
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		assertEquals(0, binding.getTimesEnded());
		ctx.noCmdProduced();
	}

	@Test
	public void testCommandExecutedOnTwoButtonsSame(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.buttonBinder()
			.on(widget1, widget1)
			.toProduce(StubCmd::new)
			.bind();

		robot.clickOn(widget1);
		assertEquals(1, binding.getTimesEnded());
		ctx.oneCmdProduced(StubCmd.class);
	}


	@Test
	public void testBuilderCloned() {
		final var binder = Bindings.buttonBinder();
		assertNotSame(binder, Bindings.buttonBinder());
		assertNotSame(binder, Bindings.buttonBinder().toProduce(i -> new StubCmd()));
		assertNotSame(binder, Bindings.buttonBinder().toProduce(() -> new StubCmd()));
		assertNotSame(binder, Bindings.buttonBinder().toProduce(() -> new StubCmd()).first((i, c) -> { }));
		assertNotSame(binder, Bindings.buttonBinder().toProduce(() -> new StubCmd()).first(c -> { }));
		assertNotSame(binder, Bindings.buttonBinder().on(widget1));
		assertNotSame(binder, Bindings.buttonBinder().on(FXCollections.observableArrayList(widget1)));
		assertNotSame(binder, Bindings.buttonBinder().when(() -> false));
		assertNotSame(binder, Bindings.buttonBinder().when(i -> false));
		assertNotSame(binder, Bindings.buttonBinder().toProduce(i -> new StubCmd()).end(c -> { }));
		assertNotSame(binder, Bindings.buttonBinder().help((Pane) null));
		assertNotSame(binder, Bindings.buttonBinder().help((HelpAnimation) null));
		assertNotSame(binder, Bindings.buttonBinder().async(widget1, null, null));
		assertNotSame(binder, Bindings.buttonBinder().log(LogLevel.COMMAND));
	}

	@Test
	void testClonedBuildersSameWidgetCmdOK(final FxRobot robot, final BindingsContext ctx) {
		final var binder = Bindings
			.buttonBinder()
			.toProduce(StubCmd::new)
			.on(widget1);
		final var binding1 = binder.bind();
		final var binding2 = binder.bind();

		robot.clickOn(widget1);

		assertNotSame(binding1, binding2);
		ctx
			.cmdsProduced(2)
			.listAssert()
			.extracting(cmd -> cmd.getBinding())
			.doesNotHaveDuplicates();
	}

	@Test
	void testClonedBuildersDiffWidgetsCmdOK(final FxRobot robot, final BindingsContext ctx) {
		final var binder = Bindings
			.buttonBinder()
			.toProduce(StubCmd::new);
		final var binding1 = binder
			.on(widget1)
			.bind();
		final var binding2 = binder
			.on(widget2)
			.bind();

		robot.clickOn(widget1);

		assertNotSame(binding1, binding2);
		ctx
			.oneCmdProduced(StubCmd.class)
			.producedBy(binding1);
	}
}

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

import io.github.interacto.binding.WidgetBinding;
import io.github.interacto.command.Command;
import io.github.interacto.command.CommandImpl;
import io.github.interacto.error.ErrorCatcher;
import io.github.interacto.jfx.interaction.library.Click;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(WidgetBindingExtension.class)
public class TestNodeBinder {
	Pane widget1;
	WidgetBinding<?> binding;
	List<Throwable> errors;
	IllegalArgumentException ex;
	Disposable disposable;

	@Start
	public void start(final Stage stage) {
		widget1 = new Pane();
		widget1.setMinHeight(20);
		widget1.setMinWidth(20);
		widget1.setMaxHeight(20);
		widget1.setMaxWidth(20);
		final VBox parent = new VBox();
		parent.getChildren().add(widget1);
		final Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
		stage.toFront();
		stage.centerOnScreen();
		stage.requestFocus();
	}

	@BeforeEach
	void setUp() {
		errors = new ArrayList<>();
		ex = new IllegalArgumentException();
		disposable = ErrorCatcher.getInstance().getErrors().subscribe(errors::add);
	}

	@AfterEach
	void tearDown() {
		if(binding != null) {
			binding.uninstallBinding();
		}
		disposable.dispose();
	}

	@Test
	void testCatchExceptionToProduce(final FxRobot robot, final BindingsContext ctx) {
		final Supplier<Command> supplier = () -> {
			throw ex;
		};

		Bindings.nodeBinder()
			.toProduce(supplier)
			.on(widget1)
			.usingInteraction(Click::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		// 2 tries for creating the command
		assertEquals(2, errors.size());
		assertEquals(List.of(ex, ex), errors);
		ctx.noCmdProduced();
	}

	@Test
	void testCatchExceptionFirstConsumer(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.first(c -> {
				throw ex;
			})
			.on(widget1)
			.usingInteraction(Click::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		// The exception does not prevent the command
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testCatchExceptionFirstBiConsumer(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(Click::new)
			.first((i, c) -> {
				throw ex;
			})
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testCatchExceptionThenBiConsumer(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(Click::new)
			.then((i, c) -> {
				throw ex;
			})
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testCatchExceptionThenConsumer(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.then(c -> {
				throw ex;
			})
			.usingInteraction(Click::new)
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testCatchExceptionEndConsumer(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.end(c -> {
				throw ex;
			})
			.usingInteraction(Click::new)
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testCatchExceptionEndRunnable(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.end(() -> {
				throw ex;
			})
			.usingInteraction(Click::new)
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testCatchExceptionEndBiConsumer(final FxRobot robot, final BindingsContext ctx) {
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(Click::new)
			.end((i, c) -> {
				throw ex;
			})
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testCatchExceptionCancel(final FxRobot robot, final BindingsContext ctx) {
		final AtomicBoolean ok = new AtomicBoolean();
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(() -> new DnD(false, true))
			.cancel(i -> {
				ok.set(true);
				throw ex;
			})
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
		robot.moveTo(widget1).press(MouseButton.PRIMARY).moveBy(2, 2).type(KeyCode.ESCAPE)
			.release(MouseButton.PRIMARY);
		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(ok.get());
		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.noCmdProduced();
	}

	@Test
	void testCatchExceptionEndOrCancel(final FxRobot robot, final BindingsContext ctx) {
		final AtomicBoolean ok = new AtomicBoolean();
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(() -> new DnD(false, true))
			.endOrCancel(i -> {
				ok.set(true);
				throw ex;
			})
			.on(widget1)
			.bind();

		Platform.runLater(() -> widget1.requestFocus());
		WaitForAsyncUtils.waitForFxEvents();
		robot.moveTo(widget1).press(MouseButton.PRIMARY).moveBy(2, 2).type(KeyCode.ESCAPE)
			.release(MouseButton.PRIMARY);
		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(ok.get());
		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.noCmdProduced();
	}

	@Test
	void testCatchExceptionWhenSupplier(final FxRobot robot, final BindingsContext ctx) {
		final AtomicBoolean ok = new AtomicBoolean();
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(Click::new)
			.when(() -> {
				ok.set(true);
				throw ex;
			})
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(ok.get());
		assertEquals(2, errors.size());
		assertEquals(List.of(ex, ex), errors);
		ctx.noCmdProduced();
	}

	@Test
	void testCatchExceptionWhenPredicate(final FxRobot robot, final BindingsContext ctx) {
		final AtomicBoolean ok = new AtomicBoolean();
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(Click::new)
			.when(i -> {
				ok.set(true);
				throw ex;
			})
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(ok.get());
		assertEquals(2, errors.size());
		assertEquals(List.of(ex, ex), errors);
		ctx.noCmdProduced();
	}

	@Test
	void testCatchExceptionIfHadEffects(final FxRobot robot, final BindingsContext ctx) {
		final AtomicBoolean ok = new AtomicBoolean();
		Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(Click::new)
			.ifHadEffects((i, c) -> {
				ok.set(true);
				throw ex;
			})
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(ok.get());
		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.oneCmdProduced(TestBinder.StubCmd.class);
	}

	@Test
	void testCatchExceptionIfHadNoEffect(final FxRobot robot, final BindingsContext ctx) {
		final AtomicBoolean ok = new AtomicBoolean();
		Bindings.nodeBinder()
			.toProduce(() -> new CommandImpl() {
				@Override
				protected void doCmdBody() {
				}

				@Override
				public boolean hadEffect() {
					return false;
				}
			})
			.usingInteraction(Click::new)
			.ifHadNoEffect((i, c) -> {
				ok.set(true);
				throw ex;
			})
			.on(widget1)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		assertTrue(ok.get());
		assertEquals(1, errors.size());
		assertSame(ex, errors.get(0));
		ctx.oneCmdProduced(CommandImpl.class);
	}

	@Test
	void testDifferentOrderBuilder1(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.usingInteraction(Click::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	void testDifferentOrderBuilder2(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.on(widget1)
			.toProduce(TestBinder.StubCmd::new)
			.usingInteraction(Click::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	void testDifferentOrderBuilder3(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.on(widget1)
			.usingInteraction(Click::new)
			.toProduce(TestBinder.StubCmd::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
		assertNotNull(binding);
	}

	@Test
	void testDifferentOrderBuilder4(final FxRobot robot, final BindingsContext ctx) {
		binding = Bindings.nodeBinder()
			.toProduce(TestBinder.StubCmd::new)
			.on(widget1)
			.usingInteraction(Click::new)
			.bind();

		robot.clickOn(widget1);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(TestBinder.StubCmd.class);
		assertNotNull(binding);
	}
}

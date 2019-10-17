package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.TimeoutWaiter;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.SpinnerChangedFSM;
import io.github.interacto.jfx.robot.FxRobotSpinner;
import io.github.interacto.jfx.ui.SpinnerFixed;
import io.github.interacto.logging.LogLevel;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class TestSpinnerBinder extends TestNodeBinder<Spinner<Double>> implements FxRobotSpinner, TimeoutWaiter {
	long timeout;

	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new SpinnerFixed<>(0, 100, 50);
		widget2 = new SpinnerFixed<>(0, 100, 50);
		super.start(stage);
	}

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		timeout = SpinnerChangedFSM.getTimeGap();
	}

	@AfterEach
	void tearDown() {
		SpinnerChangedFSM.setTimeGap(timeout);
	}

	@Test
	public void testCommandExecutedOnSingleSpinnerConsumer(final FxRobot robot) {
		binding = Bindings.spinnerBinder()
			.toProduce(i -> cmd)
			.on(widget1)
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
		assertEquals(1, cmd.exec.get());
	}

	@Test
	public void testEndsOnEnd(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.spinnerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.end(i -> cpt.incrementAndGet())
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
		assertEquals(1, cpt.get());
	}

	@Test
	public void testEndsOnFirst(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.spinnerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.first(i -> cpt.incrementAndGet())
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
		assertEquals(1, cpt.get());
	}

	@Test
	public void testEndsOnThen(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.spinnerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.then(i -> cpt.incrementAndGet())
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
		assertEquals(2, cpt.get());
	}

	@Test
	public void testContinuousThen(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();
		SpinnerChangedFSM.setTimeGap(2000L);

		binding = Bindings.spinnerBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.then(i -> cpt.incrementAndGet())
			.bind();

		incrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		decrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
		assertEquals(5, cpt.get());
	}

	@Test
	public void testContinuousThenTimeOut(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();
		final AtomicInteger cpt2 = new AtomicInteger();
		SpinnerChangedFSM.setTimeGap(2000L);

		binding = Bindings.spinnerBinder()
			.toProduce(i -> new StubCmd())
			.on(widget1)
			.first(i -> cpt.incrementAndGet())
			.end(i -> cpt2.incrementAndGet())
			.bind();

		incrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		decrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
		assertEquals(2, cpt.get());
		assertEquals(2, cpt2.get());
	}

	@Test
	public void testEndsOnUIThread(final FxRobot robot) {
		binding = Bindings.spinnerBinder()
			.on(widget1)
			.toProduce(StubCmd::new)
			.end(i -> assertTrue(Platform.isFxApplicationThread()))
			.bind();

		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
	}

	@Test
	public void testupdatesOnUIThread(final FxRobot robot) {
		binding = Bindings.spinnerBinder()
			.toProduce(StubCmd::new)
			.on(widget1)
			.then((i, c) -> assertTrue(Platform.isFxApplicationThread()))
			.bind();

		incrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		incrementSpinner(robot, widget1);
		waitForTimeoutTransitions();

		assertNotNull(binding);
	}

	@Test
	public void testBuilderCloned() {
		final var binder = Bindings.spinnerBinder();
		assertNotSame(binder, Bindings.spinnerBinder());
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> cmd));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> cmd).first(c -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> cmd).first((i, c) -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> cmd).then(i -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> cmd).then((i, c) -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> cmd).strictStart());
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> cmd).throttle(10L));
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(i -> cmd).continuousExecution());
		assertNotSame(binder, Bindings.spinnerBinder().toProduce(() -> cmd));
		assertNotSame(binder, Bindings.spinnerBinder().on(widget1));
		assertNotSame(binder, Bindings.spinnerBinder().on(FXCollections.observableArrayList(widget1)));
		assertNotSame(binder, Bindings.spinnerBinder().when(() -> false));
		assertNotSame(binder, Bindings.spinnerBinder().when(i -> false));
		assertNotSame(binder, Bindings.spinnerBinder().end(i -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().help((Pane) null));
		assertNotSame(binder, Bindings.spinnerBinder().help((HelpAnimation) null));
		assertNotSame(binder, Bindings.spinnerBinder().async(null, null, null));
		assertNotSame(binder, Bindings.spinnerBinder().log(LogLevel.COMMAND));
		assertNotSame(binder, Bindings.spinnerBinder().cancel(i -> { }));
		assertNotSame(binder, Bindings.spinnerBinder().endOrCancel(i -> { }));
	}
}

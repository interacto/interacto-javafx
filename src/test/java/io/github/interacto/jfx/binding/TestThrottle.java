package io.github.interacto.jfx.binding;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.interaction.library.SrcTgtPointsData;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestThrottle extends TestNodeBinder<Canvas> {
	JfXWidgetBinding<StubCmd, DnD, SrcTgtPointsData> binding;
	AtomicInteger counter;

	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Canvas();
		widget2 = new Canvas();
		widget1.setWidth(50);
		widget1.setHeight(50);
		super.start(stage);
	}

	@Override
	@BeforeEach
	void setUp() {
		counter = new AtomicInteger();
		binding = new NodeUpdateBinder<>(instrument)
			.usingInteraction(DnD::new)
			.toProduce(StubCmd::new)
			.throttle(500L)
			.on(widget1)
			.then((i, c) -> counter.incrementAndGet())
			.bind();
	}

	@AfterEach
	void tearDown() {
		binding.uninstallBinding();
	}

	@Test
	void testMoveTriggered(final FxRobot robot) {
		robot.drag(widget1).moveBy(1, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, counter.get());
	}

	@Test
	void testMoveFirstDragTriggered(final FxRobot robot) {
		robot.drag(widget1).moveBy(1, 0).moveBy(0, 1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, counter.get());
	}

	@Test
	void testMoveDragSecondDragNotTriggered(final FxRobot robot) {
		robot.drag(widget1).moveBy(1, 0).moveBy(0, 1).moveBy(1, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, counter.get());
	}

	@Test
	void testMoveDragDragDragTriggeredOnTimeout(final FxRobot robot) {
		robot.drag(widget1).moveBy(1, 0).moveBy(0, 1).moveBy(1, 0).sleep(600L);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, counter.get());
	}

	@Test
	void testMoveDragDragDragReleaseTriggersASingleDrag(final FxRobot robot) {
		robot.drag(widget1).moveBy(1, 0).moveBy(0, 1).moveBy(1, 0).moveBy(1, 0).drop();
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, counter.get());
	}

	@Test
	void testSeveralTimeouts(final FxRobot robot) {
		robot.drag(widget1).moveBy(1, 0).moveBy(0, 1).moveBy(1, 0).sleep(600L).
			moveBy(0, 5).moveBy(5, 0).sleep(600L).
			moveBy(0, -5).moveBy(-5, 0).sleep(600L);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(6, counter.get());
	}
}

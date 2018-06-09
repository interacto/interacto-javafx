package org.malai.javafx.interaction.binding;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.binding.NodeBinder;
import org.malai.javafx.interaction.library.DnD;
import org.malai.javafx.interaction.library.SrcTgtPointsData;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestThrottle extends TestNodeBinder<Canvas> {
	JfXWidgetBinding<StubCmd, DnD, ?, SrcTgtPointsData> binding;
	AtomicInteger counter;

	@Override
	public void start(final Stage stage) {
		widget1 = new Canvas();
		widget2 = new Canvas();
		widget1.setWidth(50);
		widget1.setHeight(50);
		super.start(stage);
	}

	@BeforeEach
	void setUp() {
		counter = new AtomicInteger();
		binding = new NodeBinder<>(new DnD(), StubCmd::new, instrument).
			throttle(500L).
			on(widget1).
			then((i, c) -> counter.incrementAndGet()).
			bind();
	}

	@AfterEach
	void tearDown() {
		binding.uninstallBinding();
	}

	@Test
	void testMoveTriggered() {
		drag(widget1).moveBy(1, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, counter.get());
	}

	@Test
	void testMoveFirstDragTriggered() {
		drag(widget1).moveBy(1, 0).moveBy(0, 1);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, counter.get());
	}

	@Test
	void testMoveDragSecondDragNotTriggered() {
		drag(widget1).moveBy(1, 0).moveBy(0, 1).moveBy(1, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, counter.get());
	}

	@Test
	void testMoveDragDragDragTriggeredOnTimeout() {
		drag(widget1).moveBy(1, 0).moveBy(0, 1).moveBy(1, 0).sleep(600L);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, counter.get());
	}

	@Test
	void testMoveDragDragDragReleaseTriggersASingleDrag() {
		drag(widget1).moveBy(1, 0).moveBy(0, 1).moveBy(1, 0).moveBy(1, 0).drop();
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(2, counter.get());
	}

	@Test
	void testSeveralTimeouts() {
		drag(widget1).moveBy(1, 0).moveBy(0, 1).moveBy(1, 0).sleep(600L).
			moveBy(0, 5).moveBy(5, 0).sleep(600L).
			moveBy(0, -5).moveBy(-5, 0).sleep(600L);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(6, counter.get());
	}
}

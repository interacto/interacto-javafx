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

import io.github.interacto.jfx.interaction.library.DnD;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestThrottle extends TestNodeBinder<Canvas> {
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
		binding = Bindings.nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(StubCmd::new)
			.throttle(500L)
			.on(widget1)
			.then((i, c) -> counter.incrementAndGet())
			.bind();
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

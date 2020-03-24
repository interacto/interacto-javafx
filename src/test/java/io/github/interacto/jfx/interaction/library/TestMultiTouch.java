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
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.CancelFSMException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestMultiTouch extends BaseJfXInteractionTest<MultiTouch> {
	@Override
	MultiTouch createInteraction() {
		return new MultiTouch(3);
	}

	private void checkSrcTouchPoint(final TouchData data, final double lx, final double ly, final double sx, final double sy,
		final int id, final Node o) {
		assertEquals(lx, data.getSrcLocalPoint().getX(), 0.0001d);
		assertEquals(ly, data.getSrcLocalPoint().getY(), 0.0001d);
		assertEquals(sx, data.getSrcScenePoint().getX(), 0.0001d);
		assertEquals(sy, data.getSrcScenePoint().getY(), 0.0001d);
		assertEquals(MouseButton.NONE, data.getButton());
		assertEquals(id, data.getTouchId());
		assertSame(o, data.getSrcObject().orElse(null));
	}

	private void checkTgtTouchPoint(final TouchData data, final double lx, final double ly, final double sx, final double sy,
		final int id, final Node o) {
		assertEquals(lx, data.getTgtLocalPoint().getX(), 0.0001d);
		assertEquals(ly, data.getTgtLocalPoint().getY(), 0.0001d);
		assertEquals(sx, data.getTgtScenePoint().getX(), 0.0001d);
		assertEquals(sy, data.getTgtScenePoint().getY(), 0.0001d);
		assertEquals(MouseButton.NONE, data.getButton());
		assertEquals(id, data.getTouchId());
		assertSame(o, data.getTgtObject().orElse(null));
	}

	@Test
	void testTouch1() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		assertEquals(1, interaction.getFsm().getConccurFSMs().stream().filter(fsm -> fsm.isStarted()).count());
		assertFalse(interaction.isRunning());
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testTouch1Data() {
		final Node node1 = new Button();
		interaction.processEvent(createTouchPressEvent(11, 23, 3, node1));
		assertEquals(1, interaction.getData().touchData().size());
		checkSrcTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 3, node1);
		checkTgtTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 3, node1);
	}

	@Test
	void testTouch2() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchPressEvent(21, 13, 2, null));
		assertEquals(2, interaction.getFsm().getConccurFSMs().stream().filter(fsm -> fsm.isStarted()).count());
		assertFalse(interaction.isRunning());
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testTouch2Data() {
		final Node node1 = new Button();
		final Node node2 = new Button();
		interaction.processEvent(createTouchPressEvent(11, 23, 1, node1));
		interaction.processEvent(createTouchPressEvent(21, 13, 2, node2));
		assertEquals(2, interaction.getData().touchData().size());
		checkSrcTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 1, node1);
		checkSrcTouchPoint(interaction.getData().touchData().get(1), 21, 13, 21, 13, 2, node2);
		checkTgtTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 1, node1);
		checkTgtTouchPoint(interaction.getData().touchData().get(1), 21, 13, 21, 13, 2, node2);
	}

	@Test
	void testTouch3() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchPressEvent(21, 13, 3, null));
		interaction.processEvent(createTouchPressEvent(210, 130, 2, null));
		assertTrue(interaction.getFsm().getConccurFSMs().stream().allMatch(fsm -> fsm.isStarted()));
		assertTrue(interaction.getFsm().isStarted());
		assertTrue(interaction.isRunning());
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testTouch3Data() {
		final Node node1 = new Button();
		final Node node2 = new Button();
		final Node node3 = new Button();
		interaction.processEvent(createTouchPressEvent(11, 23, 1, node1));
		interaction.processEvent(createTouchPressEvent(21, 13, 3, node2));
		interaction.processEvent(createTouchPressEvent(210, 130, 2, node3));
		assertEquals(3, interaction.getData().touchData().size());
		checkSrcTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 1, node1);
		checkSrcTouchPoint(interaction.getData().touchData().get(1), 210, 130, 210, 130, 2, node3);
		checkSrcTouchPoint(interaction.getData().touchData().get(2), 21, 13, 21, 13, 3, node2);
		checkTgtTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 1, node1);
		checkTgtTouchPoint(interaction.getData().touchData().get(1), 210, 130, 210, 130, 2, node3);
		checkTgtTouchPoint(interaction.getData().touchData().get(2), 21, 13, 21, 13, 3, node2);
	}

	@Test
	void testTouch3ButWithOneError() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchPressEvent(21, 13, 3, null));
		interaction.processEvent(createTouchPressEvent(210, 130, 3, null));
		assertEquals(2, interaction.getFsm().getConccurFSMs().stream().filter(fsm -> fsm.isStarted()).count());
		assertFalse(interaction.isRunning());
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testTouch1Touch2Touch3Move3() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchPressEvent(21, 13, 3, null));
		interaction.processEvent(createTouchPressEvent(210, 130, 2, null));
		interaction.processEvent(createTouchMoveEvent(210, 130, 3, null));
		assertTrue(interaction.isRunning());
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testTouch1Touch2Touch3Move3Data() {
		final Node node1 = new Button();
		final Node node2 = new Button();
		final Node node3 = new Button();
		interaction.processEvent(createTouchPressEvent(11, 23, 1, node1));
		interaction.processEvent(createTouchPressEvent(21, 13, 3, node3));
		interaction.processEvent(createTouchPressEvent(210, 130, 2, node2));
		interaction.processEvent(createTouchMoveEvent(2100, 1300, 3, node3));
		assertEquals(3, interaction.getData().touchData().size());
		checkSrcTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 1, node1);
		checkSrcTouchPoint(interaction.getData().touchData().get(1), 210, 130, 210, 130, 2, node2);
		checkSrcTouchPoint(interaction.getData().touchData().get(2), 21, 13, 21, 13, 3, node3);
		checkTgtTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 1, node1);
		checkTgtTouchPoint(interaction.getData().touchData().get(1), 210, 130, 210, 130, 2, node2);
		checkTgtTouchPoint(interaction.getData().touchData().get(2), 2100, 1300, 2100, 1300, 3, node3);
	}

	@Test
	void testTouchRelease() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 2, null));
		interaction.processEvent(createTouchReleaseEvent(11, 23, 2, null));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testTouchReleaseData() {
		final Button button = new Button();
		interaction.processEvent(createTouchPressEvent(11, 23, 1, button));
		interaction.processEvent(createTouchPressEvent(110, 230, 2, button));
		interaction.processEvent(createTouchPressEvent(111, 231, 3, button));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				checkSrcTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 1, button);
				checkTgtTouchPoint(interaction.getData().touchData().get(0), 11, 23, 11, 23, 1, button);

				checkSrcTouchPoint(interaction.getData().touchData().get(1), 110, 230, 110, 230, 2, button);
				checkTgtTouchPoint(interaction.getData().touchData().get(1), 11, 23, 11, 23, 2, button);

				checkSrcTouchPoint(interaction.getData().touchData().get(2), 111, 231, 111, 231, 3, button);
				checkTgtTouchPoint(interaction.getData().touchData().get(2), 111, 231, 111, 231, 3, button);
			}
		});
		interaction.processEvent(createTouchReleaseEvent(11, 23, 2, button));
	}
}

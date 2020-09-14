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
import io.github.interacto.jfx.interaction.JfxFSM;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(ApplicationExtension.class)
public class TestDragLock extends BaseJfXInteractionTest<DragLock> {
	@Override
	DragLock createInteraction() {
		return new DragLock();
	}

	@Test
	void testDataHandlerNotNull() {
		assertNotNull(((JfxFSM<?>) interaction.getFsm()).getDataHandler());
	}

	@Test
	void testSubDataHandlerNotNull() {
		assertNotNull(((JfxFSM<?>) interaction.firstClick.firstClick.getFsm()).getDataHandler());
	}

	@Test
	void testNormalExecution() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.MIDDLE, null));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testNormalExecutionData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11d, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23d, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(22d, interaction.getData().getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(33d, interaction.getData().getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
			}
		});

		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(22, 33, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(22, 33, MouseButton.MIDDLE, null));
	}

	@Test
	void testUpdateDuringMove() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));

		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(30d, interaction.getData().getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(40d, interaction.getData().getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
			}
		});

		interaction.processEvent(createMouseMoveEvent(30, 40, MouseButton.MIDDLE));
	}

	@Test
	void testDataDuringMove() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(30, 40, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}

	@Test
	void testDataReinit() {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(22, 33, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(22, 33, MouseButton.MIDDLE, null));
		assertNull(interaction.getData().getSrcLocalPoint());
		assertNull(interaction.getData().getTgtLocalPoint());
	}

	@Test
	void testNoMoveCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testESCCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testESCAfterMoveCancels() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createKeyPressEvent("ESC", KeyCode.ESCAPE));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testWrongButtonSecondDbleNoEnds() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(20, 30, MouseButton.PRIMARY, null));

		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testMultipleMoves() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));
		interaction.processEvent(createMouseMoveEvent(20, 30, MouseButton.MIDDLE));

		Mockito.verify(handler, Mockito.times(5)).fsmUpdates();
	}
}

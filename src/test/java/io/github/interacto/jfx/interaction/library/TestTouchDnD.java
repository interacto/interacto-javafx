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
import io.github.interacto.fsm.FSMHandler;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class TestTouchDnD extends BaseJfXInteractionTest<TouchDnD> {
	@Override
	TouchDnD createInteraction() {
		return new TouchDnD();
	}

	@Test
	void testPressExecution() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 2, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(11, interaction.getData().getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.NONE, interaction.getData().getButton());
				assertEquals(3, interaction.getData().getTouchId());
			}
		});
		interaction.processEvent(createTouchPressEvent(11, 23, 3, null));
	}

	@Test
	void testPressReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 2, null));
		interaction.processEvent(createTouchReleaseEvent(11, 23, 2, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragExecution() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 2, null));
		interaction.processEvent(createTouchMoveEvent(11, 23, 2, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragData() {
		final Button button = new Button();
		interaction.processEvent(createTouchPressEvent(11, 23, 2, null));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getData().getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(24, interaction.getData().getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.NONE, interaction.getData().getButton());
				assertEquals(2, interaction.getData().getTouchId());
				assertEquals(button, interaction.getData().getTgtObject().get());
				assertEquals(button, interaction.getData().tgtObjectProperty().get());
			}
		});
		interaction.processEvent(createTouchMoveEvent(12, 24, 2, button));
	}

	@Test
	void testPressDragDragKOExecution() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 2, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 1, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragDragExecution() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 1, null));
		interaction.processEvent(createTouchMoveEvent(14, 24, 1, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragDragData() {
		interaction.processEvent(createTouchPressEvent(11, 23, 3, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 3, null));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getData().getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(24, interaction.getData().getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.NONE, interaction.getData().getButton());
				assertEquals(3, interaction.getData().getTouchId());
			}
		});
		interaction.processEvent(createTouchMoveEvent(12, 24, 3, null));
	}

	@Test
	void testPressDragReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 1, null));
		interaction.processEvent(createTouchReleaseEvent(12, 22, 1, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragReleaseData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(15, interaction.getData().getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(25, interaction.getData().getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.NONE, interaction.getData().getButton());
				assertEquals(1, interaction.getData().getTouchId());
			}
		});
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 1, null));
		interaction.processEvent(createTouchReleaseEvent(15, 25, 1, null));
	}

	@Test
	void testPressDragReleaseKOExecution() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 1, null));
		interaction.processEvent(createTouchReleaseEvent(12, 22, 2, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressDragDragReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchMoveEvent(14, 24, 1, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 1, null));
		interaction.processEvent(createTouchReleaseEvent(12, 22, 1, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragDragReleaseData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getData().srcLocalPointProperty().get().getX(), 0.0001d);
				assertEquals(23, interaction.getData().srcLocalPointProperty().get().getY(), 0.0001d);
				assertEquals(15, interaction.getData().tgtLocalPointProperty().get().getX(), 0.0001d);
				assertEquals(25, interaction.getData().tgtLocalPointProperty().get().getY(), 0.0001d);
				assertEquals(MouseButton.NONE, interaction.getData().getButton());
				assertEquals(1, interaction.getData().getTouchId());
			}
		});
		interaction.processEvent(createTouchPressEvent(11, 23, 1, null));
		interaction.processEvent(createTouchMoveEvent(12, 22, 1, null));
		interaction.processEvent(createTouchReleaseEvent(15, 25, 1, null));
	}

	@Test
	void testDnDOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createTouchPressEvent(11, 23, 1, null));
		pane.fireEvent(createTouchMoveEvent(11, 23, 1, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}

	@Test
	void testDnDOnUnRegWidgetKO() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createTouchPressEvent(11, 23, 1, null));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testTouchRestart() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createTouchPressEvent(11, 23, 1, null));
		pane.fireEvent(createTouchMoveEvent(12, 25, 1, null));
		pane.fireEvent(createTouchReleaseEvent(12, 25, 1, null));
		pane.fireEvent(createTouchPressEvent(11, 23, 1, null));
		pane.fireEvent(createTouchMoveEvent(11, 23, 1, null));
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
	}

	@Test
	void testEventConsumed() throws CancelFSMException {
		final Pane pane1 = new Pane();
		final Pane pane2 = new Pane();
		pane1.getChildren().add(pane2);

		final FSMHandler handler2 = Mockito.mock(FSMHandler.class);
		final TouchDnD i2 = createInteraction();
		i2.getFsm().addHandler(handler2);

		interaction.registerToNodes(List.of(pane1));
		i2.registerToNodes(List.of(pane2));
		i2.setConsumeEvents(true);
		pane2.fireEvent(createTouchPressEvent(11, 23, 1, null));
		pane2.fireEvent(createTouchMoveEvent(12, 25, 1, null));
		pane2.fireEvent(createTouchReleaseEvent(12, 25, 1, null));

		Mockito.verify(handler2, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler2, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testGetData() {
		assertNotNull(interaction.getData());
	}

	@Test
	void testIsAltPressed() {
		assertFalse(interaction.getData().isAltPressed());
	}

	@Test
	void testIsCtrlPressed() {
		assertFalse(interaction.getData().isCtrlPressed());
	}

	@Test
	void testIsShiftPressed() {
		assertFalse(interaction.getData().isShiftPressed());
	}

	@Test
	void testIsMetaPressed() {
		assertFalse(interaction.getData().isMetaPressed());
	}

	@Test
	void testGetButton() {
		assertEquals(MouseButton.NONE, interaction.getData().getButton());
	}
}

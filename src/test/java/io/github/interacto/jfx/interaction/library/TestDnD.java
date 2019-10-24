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
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.CancelFSMException;
import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestDnD extends BaseJfXInteractionTest<DnD> {
	@Override
	DnD createInteraction() {
		return new DnD();
	}

	@Test
	void testPressExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(11, interaction.getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.PRIMARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
	}

	@Test
	void testPressReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressReleaseKOExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseReleaseEvent(11, 23, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(11, 23, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragData() {
		final Button button = new Button();
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.SECONDARY));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(24, interaction.getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getButton());
				assertEquals(button, interaction.getTgtObject().get());
			}
		});
		interaction.processEvent(createMouseDragEvent(12, 24, MouseButton.SECONDARY, button));
	}

	@Test
	void testPressDragKOExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.SECONDARY, null));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragDragKOExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseDragEvent(14, 24, MouseButton.SECONDARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragDragExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseDragEvent(14, 24, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragDragData() {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.MIDDLE));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.MIDDLE, null));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(24, interaction.getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getButton());
			}
		});
		interaction.processEvent(createMouseDragEvent(12, 24, MouseButton.MIDDLE, null));
	}

	@Test
	void testPressDragReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseReleaseEvent(12, 22, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragReleaseData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(15, interaction.getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(25, interaction.getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.PRIMARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseReleaseEvent(15, 25, MouseButton.PRIMARY));
	}

	@Test
	void testPressDragReleaseKOExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseReleaseEvent(12, 22, MouseButton.SECONDARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	void testPressDragDragReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(14, 24, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseReleaseEvent(12, 22, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressDragDragReleaseData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(15, interaction.getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(25, interaction.getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.PRIMARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(12, 22, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseReleaseEvent(15, 25, MouseButton.PRIMARY));
	}

	@Test
	void testDnDOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(11, 23, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
	}

	@Test
	void testDnDOnUnRegWidgetKO() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNewKindEventRegistered() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
	}

	@Test
	void testNewKindEvent2Registered() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY, null));
		pane.fireEvent(createMouseReleaseEvent(12, 25, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testNewKindEventOnTerminalRegistered() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(12, 25, MouseButton.PRIMARY, null));
		pane.fireEvent(createMouseReleaseEvent(12, 25, MouseButton.PRIMARY));
		pane.fireEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		pane.fireEvent(createMouseDragEvent(11, 23, MouseButton.PRIMARY, null));
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
	}
}

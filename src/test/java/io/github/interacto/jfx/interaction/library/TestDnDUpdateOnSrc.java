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
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestDnDUpdateOnSrc extends BaseJfXInteractionTest<DnD> {
	@Override
	DnD createInteraction() {
		return new DnD(true, false);
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
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.SECONDARY));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(24, interaction.getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMouseDragEvent(12, 24, MouseButton.SECONDARY, null));
	}

	@Test
	void testPressDragDragData() {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.SECONDARY));
		interaction.processEvent(createMouseDragEvent(10, 22, MouseButton.SECONDARY, null));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(10, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(22, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getTgtLocalPoint().getX(), 0.0001d);
				assertEquals(24, interaction.getTgtLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMouseDragEvent(12, 24, MouseButton.SECONDARY, null));
	}
}

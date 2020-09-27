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
package io.github.interacto.jfx.interaction;

import io.github.interacto.fsm.InputState;
import io.github.interacto.fsm.OutputState;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.input.TouchEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTouchMoveTransition {
	TouchMoveTransition tr;

	@BeforeEach
	void setUp() {
		tr = new TouchMoveTransition(Mockito.mock(OutputState.class), Mockito.mock(InputState.class));
	}

	@Test
	void testAcceptEventKONULL() {
		assertNull(tr.accept(null));
	}

	@Test
	void testAcceptEventKO() {
		assertNull(tr.accept(Mockito.mock(Event.class)));
	}

	@Test
	void testAcceptEventOK() {
		final TouchEvent mock = Mockito.mock(TouchEvent.class);
		Mockito.when(mock.getEventType()).thenReturn(TouchEvent.TOUCH_MOVED);
		assertNotNull(tr.accept(mock));
	}

	@Test
	void testGuardOK() {
		assertTrue(tr.isGuardOK(null));
	}

	@Test
	void testGetAcceptedEvents() {
		assertEquals(Set.of(TouchEvent.TOUCH_MOVED), tr.getAcceptedEvents());
	}
}

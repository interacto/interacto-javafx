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
import java.util.Collections;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

/**
 * This transition corresponds to a pressure of a button of a pointing device.
 * @author Arnaud BLOUIN
 */
public class PressureTransition extends InputEventTransition<MouseEvent> {
	/**
	 * Creates the transition.
	 */
	public PressureTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected MouseEvent accept(final Event event) {
		return event instanceof MouseEvent && event.getEventType() == MouseEvent.MOUSE_PRESSED ? (MouseEvent) event : null;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(MouseEvent.MOUSE_PRESSED);
	}
}

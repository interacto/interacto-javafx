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
import javafx.scene.input.TouchEvent;

/**
 * This transition corresponds to a touch move.
 * @author Arnaud BLOUIN
 */
public class TouchMoveTransition extends InputEventTransition<TouchEvent> {
	/**
	 * Creates the transition.
	 */
	public TouchMoveTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected TouchEvent accept(final Event event) {
		return event instanceof TouchEvent && event.getEventType() == TouchEvent.TOUCH_MOVED ? (TouchEvent) event : null;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(TouchEvent.TOUCH_MOVED);
	}
}

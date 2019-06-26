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
package io.interacto.jfx.interaction;

import io.interacto.fsm.InputState;
import io.interacto.fsm.OutputState;
import java.util.Collections;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

/**
 * This transition corresponds to a pressure of a button of a pointing device.
 * @author Arnaud BLOUIN
 */
public class ClickTransition extends InputEventTransition<MouseEvent> {
	/**
	 * Creates the transition.
	 */
	public ClickTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected boolean accept(final Event event) {
		return event != null && event.getEventType() == MouseEvent.MOUSE_CLICKED;
	}

	@Override
	protected boolean isGuardOK(final Event event) {
		return true;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(MouseEvent.MOUSE_CLICKED);
	}
}

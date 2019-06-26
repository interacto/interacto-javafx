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
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * An FSM transition for windows to be closed.
 * @author Arnaud BLOUIN
 */
public class JfxWindowClosedTransition extends JfxWidgetTransition<Window> {
	public JfxWindowClosedTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected boolean accept(final Event e) {
		return e != null && e.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(WindowEvent.WINDOW_CLOSE_REQUEST);
	}
}

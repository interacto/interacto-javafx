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
import io.github.interacto.fsm.Transition;
import java.util.Collections;
import java.util.Set;
import javafx.event.Event;
import javafx.stage.WindowEvent;

/**
 * An FSM transition for windows to be closed.
 * @author Arnaud BLOUIN
 */
public class JfxWindowClosedTransition extends Transition<WindowEvent, Event> {
	public JfxWindowClosedTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected WindowEvent accept(final Event evt) {
		return evt instanceof WindowEvent && evt.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST ? (WindowEvent) evt : null;
	}

	@Override
	protected boolean isGuardOK(final WindowEvent windowEvent) {
		return true;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(WindowEvent.WINDOW_CLOSE_REQUEST);
	}
}

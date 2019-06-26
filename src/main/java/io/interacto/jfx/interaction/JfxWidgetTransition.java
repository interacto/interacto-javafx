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
import io.interacto.fsm.WidgetTransition;
import java.util.Collections;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.Event;

/**
 * A transition based on a JFX widget.
 * @param <T> The type of the widget.
 * @author Arnaud Blouin
 */
public abstract class JfxWidgetTransition<T> extends WidgetTransition<Event, T> {
	public JfxWidgetTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected boolean accept(final Event e) {
		return e != null && e.getEventType() == ActionEvent.ACTION;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(ActionEvent.ACTION);
	}

	@Override
	protected boolean isGuardOK(final Event event) {
		return true;
	}
}

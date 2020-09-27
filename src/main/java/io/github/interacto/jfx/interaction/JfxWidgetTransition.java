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
import io.github.interacto.fsm.WidgetTransition;
import java.util.Collections;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.Event;

/**
 * A transition based on a JFX widget.
 * @param <T> The type of the widget.
 * @author Arnaud Blouin
 */
public abstract class JfxWidgetTransition<T> extends WidgetTransition<ActionEvent, Event, T> {
	public JfxWidgetTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected ActionEvent accept(final Event evt) {
		return evt instanceof ActionEvent && evt.getEventType() == ActionEvent.ACTION ? (ActionEvent) evt : null;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(ActionEvent.ACTION);
	}

	@Override
	protected boolean isGuardOK(final ActionEvent event) {
		return true;
	}
}

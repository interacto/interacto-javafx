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
package io.github.interacto.jfx.interaction;

import io.github.interacto.fsm.InputState;
import io.github.interacto.fsm.OutputState;
import io.github.interacto.fsm.Transition;
import javafx.event.Event;
import javafx.scene.input.InputEvent;

/**
 * This abstract transition defines a model for transitions based on input events.
 * @author Arnaud Blouin
 */
public abstract class InputEventTransition<T extends InputEvent> extends Transition<Event> {
	protected T event;

	/**
	 * Creates a transition.
	 * @param srcState The source state of the transition.
	 * @param tgtState The srcObject state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public InputEventTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	/**
	 * @return The events at the origin of the transition. Cannot be null.
	 */
	public T getEvent() {
		return event;
	}

	/**
	 * Sets the event of the transition.
	 * @param evt The new transition. Cannot be null.
	 */
	public void setEvent(final T evt) {
		if(evt != null) {
			event = evt;
		}
	}
}

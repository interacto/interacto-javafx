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
package io.github.interacto.jfx.interaction.library;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * A stub tab event.
 * @author Arnaud Blouin
 */
public class TabEvent extends Event {
	public static final EventType<TabEvent> SELECTED = new EventType<>(Event.ANY, "SELECTED");

	public TabEvent() {
		super(SELECTED);
	}

	public TabEvent(final Object source, final EventTarget target) {
		super(source, target, SELECTED);
	}

	@Override
	public EventType<? extends TabEvent> getEventType() {
		return (EventType<? extends TabEvent>) super.getEventType();
	}
}

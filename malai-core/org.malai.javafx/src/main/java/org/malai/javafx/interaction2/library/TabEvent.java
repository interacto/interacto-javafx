/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction2.library;

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

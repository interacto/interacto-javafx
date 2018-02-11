/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction;

import java.util.Collections;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.input.ScrollEvent;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;

/**
 * A scroll event transition.
 * @author Arnaud Blouin
 */
public class ScrollTransition extends InputEventTransition<ScrollEvent> {
	public ScrollTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected boolean accept(final Event event) {
		return event != null && event.getEventType() == ScrollEvent.SCROLL;
	}

	@Override
	protected boolean isGuardOK(final Event event) {
		return true;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(ScrollEvent.SCROLL);
	}
}

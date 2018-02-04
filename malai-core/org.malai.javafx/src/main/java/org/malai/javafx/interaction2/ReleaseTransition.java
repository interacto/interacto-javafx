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
package org.malai.javafx.interaction2;

import java.util.Collections;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;

/**
 * This transition corresponds to a release of a button of a pointing device.
 * @author Arnaud BLOUIN
 */
public class ReleaseTransition extends InputEventTransition<MouseEvent> {
	/**
	 * Defines a transition.
	 * @param srcState The source state of the transition.
	 * @param tgtState The srcObject state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public ReleaseTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected boolean accept(final Event event) {
		return event != null && event.getEventType() == MouseEvent.MOUSE_RELEASED;
	}

	@Override
	protected boolean isGuardOK(final Event event) {
		return true;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(MouseEvent.MOUSE_RELEASED);
	}
}

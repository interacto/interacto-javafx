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
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;

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

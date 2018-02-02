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
import javafx.event.ActionEvent;
import javafx.event.Event;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;
import org.malai.fsm.WidgetTransition;

/**
 * A transition based on a JFX widget.
 * @param <T> The type of the widget.
 * @author Arnaud Blouin
 */
public abstract class JfxWidgetTransition<T> extends WidgetTransition<Event, T> {
	protected final JfxInteraction<?, T> interaction;

	public JfxWidgetTransition(final JfxInteraction<?, T> interaction, final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
		this.interaction = interaction;
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

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

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition corresponds to a drag (move after a pressure) using a pointing device.
 * @author Arnaud BLOUIN
 */
public class DragTransition extends InputEventTransition<MouseEvent> {
	/**
	 * Defines a transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The srcObject state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public DragTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	@Override
	public EventType<MouseEvent> getEventType() {
		return MouseEvent.MOUSE_DRAGGED;
	}
}

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
package org.malai.javafx.interaction;

import javafx.scene.input.InputEvent;
import org.malai.interaction.TransitionImpl;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This abstract transition defines a model for transitions based on input events.
 */
public abstract class InputEventTransition<T extends InputEvent> extends TransitionImpl {
	protected T event;

	/**
	 * Creates a transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The srcObject state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public InputEventTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
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

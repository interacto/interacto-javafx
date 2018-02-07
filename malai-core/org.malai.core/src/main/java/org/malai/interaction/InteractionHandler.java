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
package org.malai.interaction;

import org.malai.stateMachine.MustCancelStateMachineException;

/**
 * Defines an interaction for objects that want to by notified when the state of an interaction changed.
 * @author Arnaud BLOUIN
 */
public interface InteractionHandler {
	/**
	 * When the interaction quits its initial state.
	 * @throws MustCancelStateMachineException If the interaction must be cancelled.
	 */
	void interactionStarts() throws MustCancelStateMachineException;

	/**
	 * When the interaction goes to standard state.
	 * @throws MustCancelStateMachineException If the interaction must be cancelled.
	 */
	void interactionUpdates() throws MustCancelStateMachineException;

	/**
	 * When the interaction goes to a terminal state.
	 * @throws MustCancelStateMachineException If the interaction must be cancelled.
	 */
	void interactionStops() throws MustCancelStateMachineException;

	/**
	 * When the interaction goes to a cancelling state.
	 */
	void interactionCancels();
}

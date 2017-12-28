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
package org.malai.stateMachine;

/**
 * This interface defines a state that can be the source state of a transition.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public interface SourceableState extends State {
	/**
	 * @throws MustCancelStateMachineException To launch when the state machine must stop.
	 */
	void onOutgoing() throws MustCancelStateMachineException;
}

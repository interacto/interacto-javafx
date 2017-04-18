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
 * This interface defines the notion of state machine.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public interface StateMachine {
	/** Terminates the state machine.
	 * @throws MustAbortStateMachineException If something happens requiring the interaction to abort.
	 * */
	void onTerminating() throws MustAbortStateMachineException;

	/** Aborts the state machine. */
	void onAborting();

	/** Starts the state machine.
	 * @throws MustAbortStateMachineException If something happens requiring the interaction to abort.
	 * */
	void onStarting() throws MustAbortStateMachineException;

	/** Updates the state machine.
	 * @throws MustAbortStateMachineException If something happens requiring the interaction to abort.
	 * */
	void onUpdating() throws MustAbortStateMachineException;

	/**
	 * Adds a state to the state machine.
	 * @param state The state to add. Must not be null.
	 * @since 0.2
	 */
	void addState(final State state);

	/**
	 * Reinits the state machine.
	 * @since 0.2
	 */
	void reinit();

	/**
	 * Defines if the state machine is activated.
	 * @param activated True: the state machine will be activated.
	 * @since 0.2
	 */
	void setActivated(final boolean activated);

	/**
	 * @return The current state of the running state machine. Or null when the machine is not running.
	 * @since 2.0
	 */
	State getCurrentState();

	/**
	 * @return True or false depending on whether the state machine is activated.
	 * @since 2.0
	 */
	boolean isActivated();

	/**
	 * Checks whether the transition can be executed and executes it when possible.
	 * @param transition The transition to check.
	 * @return True: the transition has been executed. False otherwise.
	 */
	boolean checkTransition(final Transition transition);

	/**
	* @return True: the state machine is running.
	* @since 0.2
	*/
	boolean isRunning();
}

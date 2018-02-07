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
package org.malai.stateMachine;

/**
 * This interface defines the notion of state machine.
 * @author Arnaud BLOUIN
 */
public interface StateMachine {
	/**
	 * Terminates the state machine.
	 * @throws MustCancelStateMachineException If something happens requiring the interaction to cancel.
	 */
	void onTerminating() throws MustCancelStateMachineException;

	/** Cancels the state machine. */
	void onCancelling();

	/**
	 * Starts the state machine.
	 * @throws MustCancelStateMachineException If something happens requiring the interaction to cancel.
	 */
	void onStarting() throws MustCancelStateMachineException;

	/**
	 * Updates the state machine.
	 * @throws MustCancelStateMachineException If something happens requiring the interaction to cancel.
	 */
	void onUpdating() throws MustCancelStateMachineException;

	/**
	 * Adds a state to the state machine.
	 * @param state The state to add. Must not be null.
	 */
	void addState(final State state);

	/**
	 * Reinits the state machine.
	 */
	void reinit();

	/**
	 * @return The current state of the running state machine. Or null when the machine is not running.
	 */
	State getCurrentState();

	/**
	 * @return True or false depending on whether the state machine is activated.
	 */
	boolean isActivated();

	/**
	 * Defines if the state machine is activated.
	 * @param activated True: the state machine will be activated.
	 */
	void setActivated(final boolean activated);

	/**
	 * Checks whether the transition can be executed and executes it when possible.
	 * @param transition The transition to check.
	 * @return True: the transition has been executed. False otherwise.
	 */
	boolean checkTransition(final Transition transition);

	/**
	 * @return True: the state machine is running.
	 */
	boolean isRunning();
}

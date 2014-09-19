package org.malai.interaction;

import java.util.List;

import org.malai.stateMachine.State;
import org.malai.stateMachine.StateMachine;
import org.malai.stateMachine.MustAbortStateMachineException;

/**
 * An interface defining the concept of interaction and its related services.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud BLOUIN
 * @date 2014-09-19
 * @version 2.0
 */
public interface Interaction extends StateMachine, EventHandler {
	@Override
	void setActivated(boolean activated);

	@Override
	void reinit();

	/**
	 * @return The handlers that listens to the interaction.
	 */
	List<InteractionHandler> getHandlers();

	/**
	 * Adds an interaction handler.
	 * @param handler The handler to add.
	 * @since 0.1
	 */
	void addHandler(InteractionHandler handler);

	@Override
	void addState(State state);

	/**
	 * Links the interaction to an eventable object (e.g. a MPanel or a MButton).
	 * @param eventable The Eventable object.
	 * @since 0.2
	 */
	void linkToEventable(Eventable eventable);

	@Override
	boolean isRunning();

	@Override
	void onUpdating() throws MustAbortStateMachineException;

	/**
	 * @return The ID of last HID that has been used by the interaction. If the interaction has stopped or is
	 * aborted, the value of the attribute is -1.
	 * @since 0.2
	 */
	int getLastHIDUsed();

	/**
	 * @param hid The ID of last HID that has been used by the interaction. If the interaction has stopped or is
	 * aborted, the value of the attribute is -1.
	 * @since 0.2
	 */
	void setLastHIDUsed(int hid);

	/**
	 * Clears the events of the interaction still in process.
	 * @since 0.2
	 */
	void clearEventsStillInProcess();

	/**
	 * Stops the interaction and clears all its events waiting for a process.
	 * @since  0.2
	 */
	void clearEvents();
}

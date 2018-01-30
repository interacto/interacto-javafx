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
package org.malai.interaction;

import java.util.List;
import org.malai.stateMachine.StateMachine;

/**
 * An interface defining the concept of interaction and its related services.
 * @author Arnaud BLOUIN
 */
public interface Interaction extends StateMachine, EventProcessor {
	/**
	 * @return The handler that listens to the interaction.
	 */
	List<InteractionHandler> getHandlers();

	/**
	 * Adds an interaction handler.
	 * @param handler The handler to add.
	 */
	void addHandler(final InteractionHandler handler);

	/**
	 * @return The ID of last HID that has been used by the interaction. If the interaction has stopped or is
	 * aborted, the value of the attribute is -1.
	 */
	int getLastHIDUsed();

	/**
	 * @param hid The ID of last HID that has been used by the interaction. If the interaction has stopped or is
	 * aborted, the value of the attribute is -1.
	 */
	void setLastHIDUsed(final int hid);

	/**
	 * Clears the events of the interaction still in process.
	 */
	void clearEventsStillInProcess();

	/**
	 * Stops the interaction and clears all its events waiting for a process.
	 */
	void clearEvents();

	void log(final boolean log);
}

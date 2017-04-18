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
package org.malai.instrument;

import org.malai.action.Action;
import org.malai.interaction.Eventable;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;

/**
 * The concept of interactor and its related services.
 * @author Arnaud BLOUIN
 */
public interface Interactor extends InteractionHandler {
	/**
	 * Binds the interaction of the interactor to a Eventable object that produces
	 * events used by the interaction.
	 * @param eventable The eventable object that gathers event used by the interaction.
	 * @since 0.2
	 */
	void addEventable(Eventable eventable);

	/**
	 * Stops the interaction and clears all its events waiting for a process.
	 * @since 0.2
	 */
	void clearEvents();

	/**
	 * After being created by method createAction, the action must be initialised
	 * by this method.
	 * @since 0.2
	 */
	void initAction();

	/**
	 * Updates the current action. To override.
	 * @since 0.2
	 */
	void updateAction();

	/**
	 * @return True if the condition of the interactor is respected.
	 */
	boolean isConditionRespected();

	/**
	 * @return The interaction.
	 */
	Interaction getInteraction();

	/**
	 * @return The action in progress or null.
	 */
	Action getAction();

	/**
	 * @return True if the interactor is activated.
	 */
	boolean isActivated();

	/**
	 * @return True: if the interactor is currently used.
	 * since 0.2
	 */
	boolean isRunning();

	/**
	 * Sometimes the interaction of two different interactors can overlap themselves. It provokes
	 * that the first interaction can stops while the second is blocked in a intermediary state.
	 * Two solutions are possible to avoid such a problem:<br>
	 * - the use of this function that perform some tests. If the test fails, the starting interaction
	 * is aborted and the resulting action is never created;<br>
	 * - the modification of one of the interactions to avoid the overlapping.
	 * @return True: if the starting interaction must be aborted so that the action is never created.
	 * @since 0.2
	 */
	boolean isInteractionMustBeAborted();

	/**
	 * @return True if the action is executed on each evolution
	 * of the interaction.
	 */
	boolean isExecute();

	/**
	 * Defines the interim feedback of the interactor. If overridden, the interim
	 * feedback of its instrument should be define too.
	 */
	void interimFeedback();

	/**
	 * Activates the interactor.
	 * @param activ True: the interactor is activated. Otherwise, it is desactivated.
	 * @since 3.0
	 */
	void setActivated(boolean activ);

	/**
	 * @return The instrument that contains the interactor.
	 * @since 0.1
	 */
	Instrument getInstrument();
}

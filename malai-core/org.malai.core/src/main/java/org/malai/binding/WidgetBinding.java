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
package org.malai.binding;

import org.malai.command.Command;
import org.malai.fsm.FSMHandler;
import org.malai.instrument.Instrument;
import org.malai.interaction.InteractionImpl;

/**
 * The concept of widget binding and its related services.
 * @author Arnaud BLOUIN
 */
public interface WidgetBinding extends FSMHandler {
	/**
	 * Stops the interaction and clears all its events waiting for a process.
	 */
	void clearEvents();

	/**
	 * After being created by method map, the command must be initialised by this method.
	 */
	void first();

	/**
	 * Updates the current command. To override.
	 */
	void then();

	/**
	 * @return True if the condition of the widget binding is respected.
	 */
	boolean when();

	/**
	 * @return The interaction.
	 */
	InteractionImpl<?, ?, ?> getInteraction();

	/**
	 * @return The command in progress or null.
	 */
	Command getCommand();

	/**
	 * @return True if the widget binding is activated.
	 */
	boolean isActivated();

	/**
	 * Activates the widget binding.
	 * @param activ True: the widget binding is activated. Otherwise, it is desactivated.
	 */
	void setActivated(final boolean activ);

	/**
	 * @return True: if the widget binding is currently used.
	 */
	boolean isRunning();

	/**
	 * States whether the interaction must continue to run while the condition of the binding is not fulfilled at the interaction start.
	 */
	boolean isStrictStart();

	/**
	 * @return True if the command is executed on each evolution of the interaction.
	 */
	boolean isExecute();

	/**
	 * Defines the interim feedback of the widget binding. If overridden, the interim
	 * feedback of its instrument should be define too.
	 */
	void feedback();

	/**
	 * @return The instrument that contains the widget binding.
	 */
	Instrument<?> getInstrument();

	void uninstallBinding();
}

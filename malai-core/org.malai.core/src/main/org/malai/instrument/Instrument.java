package org.malai.instrument;

import java.util.List;

import org.malai.action.ActionHandler;
import org.malai.interaction.Eventable;
import org.malai.preferences.Preferenciable;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;

/**
 * An interface defining the concept of instrument and its related services.<br>
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
public interface Instrument extends Preferenciable, Modifiable, Reinitialisable, ActionHandler {
	/**
	 * @return The number of interactors that compose the instrument.
	 * @since 0.2
	 */
	int getNbInteractors();

	/**
	 * @return True: the instrument has at least one interactor. False otherwise.
	 * @since 0.2
	 */
	boolean hasInteractors();

	/**
	 * @return The interactors that compose the instrument. Cannot be null.
	 * @since 0.2
	 */
	List<Interactor> getInteractors();

	/**
	 * Binds the interaction of the interactors of the instrument to a Eventable object that produces
	 * events used by the interactions.
	 * @param eventable The eventable object that gathers event used by the interactions.
	 * @since 0.2
	 */
	void addEventable(Eventable eventable);

	/**
	 * Stops the interactions of the instrument and clears all its events waiting for a process.
	 * @since 0.2
	 */
	void clearEvents();

	/**
	 * @return True if the instrument is activated.
	 */
	boolean isActivated();

	/**
	 * Activates or deactivates the instrument.
	 * @param activated True = activation.
	 */
	void setActivated(boolean activated);

	/**
	 * Reinitialises the interim feedback of the instrument.
	 * Must be overridden.
	 */
	void interimFeedback();
}

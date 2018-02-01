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

import java.util.List;
import org.malai.action.ActionHandler;
import org.malai.binding.WidgetBinding;
import org.malai.properties.Preferenciable;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;

/**
 * The concept of instrument and its related services.
 * @author Arnaud BLOUIN
 */
public interface Instrument<T extends WidgetBinding> extends Preferenciable, Modifiable, Reinitialisable, ActionHandler {
	/**
	 * @return The number of widget bindings that compose the instrument.
	 */
	int getNbWidgetBindings();

	/**
	 * @return True: the instrument has at least one widget binding. False otherwise.
	 */
	boolean hasWidgetBindings();

	/**
	 * @return The widget bindings that compose the instrument. Cannot be null.
	 */
	List<T> getWidgetBindings();

	/**
	 * Stops the interactions of the instrument and clears all its events waiting for a process.
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
	void setActivated(final boolean activated);

	/**
	 * Reinitialises the interim feedback of the instrument.
	 * Must be overridden.
	 */
	void interimFeedback();
}

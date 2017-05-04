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
import org.malai.interaction.Eventable;
import org.malai.preferences.Preferenciable;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;

/**
 * The concept of instrument and its related services.
 * @author Arnaud BLOUIN
 */
public interface Instrument extends Preferenciable, Modifiable, Reinitialisable, ActionHandler {
	/**
	 * @return The number of widget bindings that compose the instrument.
	 * @since 0.2
	 */
	int getNbWidgetBindings();

	/**
	 * @return True: the instrument has at least one widget binding. False otherwise.
	 * @since 0.2
	 */
	boolean hasWidgetBindings();

	/**
	 * @return The widget bindings that compose the instrument. Cannot be null.
	 * @since 0.2
	 */
	List<WidgetBinding> getWidgetBindings();

	/**
	 * Binds the interaction of the widget bindings of the instrument to a Eventable object that produces
	 * events used by the interactions.
	 * @param eventable The eventable object that gathers event used by the interactions.
	 * @since 0.2
	 */
	void addEventable(final Eventable eventable);

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
	void setActivated(final boolean activated);

	/**
	 * Reinitialises the interim feedback of the instrument.
	 * Must be overridden.
	 */
	void interimFeedback();
}

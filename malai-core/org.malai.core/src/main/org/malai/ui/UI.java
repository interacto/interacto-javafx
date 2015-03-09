package org.malai.ui;

import java.util.List;

import org.malai.instrument.Instrument;
import org.malai.preferences.Preferenciable;
import org.malai.presentation.AbstractPresentation;
import org.malai.presentation.ConcretePresentation;
import org.malai.presentation.Presentation;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;

/**
 * Defines the concept of User Interface.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud BLOUIN
 */
public interface UI<S extends UIComposer<?,?,?>> extends Modifiable, Reinitialisable, Preferenciable {
	//FIXME java 8: use default implementation to factorise the code with the other components.
	/**
	 * @return The instruments of the interactive system.
	 * @since 0.2
	 */
	Instrument[] getInstruments();

	/**
	 * Initialises the presentations of the UI.
	 * @since 0.2
	 */
	void initialisePresentations();


	/**
	 * Updates the presentations.
	 * @since 0.2
	 */
	void updatePresentations();


	/**
	 * Allows to get the presentation which abstract and concrete presentations match the given classes.
	 * @param absPresClass The class of the abstract presentation to find.
	 * @param concPresClass The class of the concrete presentation to find.
	 * @return The found presentation or null.
	 * @since 0.2
	 */
	<A extends AbstractPresentation, C extends ConcretePresentation>
	Presentation<A, C> getPresentation(final Class<A> absPresClass, final Class<C> concPresClass);

	/**
	 * @return The presentations of the interactive system.
	 * @since 0.2
	 */
	List<Presentation<?,?>> getPresentations();


	/**
	 * @return The composer that composes the UI.
	 * @since 0.2
	 */
	S getComposer();
}

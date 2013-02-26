package org.malai.swing.instrument.library;

import java.awt.Component;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.Interaction;
import org.malai.swing.action.library.ShowWidget;

/**
 * This link maps an interaction to an action that shows a JComponent.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-10-23<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 * @param <N> The type of the instrument that will contain this link.
 * @param <I> The type of the interaction.
 */
public abstract class Interaction2ShowComponentLink<I extends Interaction, N extends Instrument> extends Link<ShowWidget, I, N> {
	/** The component to show. */
	protected Component component;

	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @param component The component to show/hide.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param interactionClass The class corresponding to the interaction of the link.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 */
	public Interaction2ShowComponentLink(final N ins, final boolean exec, final Class<I> interactionClass, final Component component)
			throws InstantiationException, IllegalAccessException {
		super(ins, exec, ShowWidget.class, interactionClass);
		this.component	= component;
	}

	@Override
	public void initAction() {
		action.setComponent(component);
		action.setVisible(true);
	}
}

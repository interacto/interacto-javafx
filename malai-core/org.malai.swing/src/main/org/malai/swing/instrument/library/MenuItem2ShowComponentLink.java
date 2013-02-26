package org.malai.swing.instrument.library;

import java.awt.Component;

import org.malai.instrument.Instrument;
import org.malai.swing.interaction.library.MenuItemPressed;
import org.malai.swing.widget.MMenuItem;

/**
 * This link links a menu item interaction to an action that shows a JComponent.<br>
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
 * 11/20/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 * @param <N> The type of the instrument that will contain this link.
 */
public class MenuItem2ShowComponentLink<N extends Instrument> extends Interaction2ShowComponentLink<MenuItemPressed, N> {
	/** The menu item used to shows the component. */
	protected MMenuItem menuItem;

	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @param component The component to show/hide.
	 * @param menuItem The menu item used to show/hide to component.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @since 0.2
	 */
	public MenuItem2ShowComponentLink(final N ins, final Component component, final MMenuItem menuItem) throws InstantiationException, IllegalAccessException {
		super(ins, false, MenuItemPressed.class, component);
		this.menuItem	= menuItem;
	}


	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==menuItem;
	}
}

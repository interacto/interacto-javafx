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
package org.malai.javafx.binding;

import java.util.List;
import javafx.scene.control.MenuItem;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemInteraction;

/**
 * Base of a menu item binding for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfxMenuItemBinding<A extends ActionImpl, I extends MenuItemInteraction<MenuItem>, N extends JfxInstrument> extends JfXWidgetBinding<A, I, N> {

	/**
	 * Creates a widget binding for menu items. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param menuItems The menu items concerned by the binding. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfxMenuItemBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction, final List<MenuItem> menuItems)
		throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction, false, null);

		if(menuItems != null) {
			interaction.registerToMenuItems(menuItems);
		}
	}
}

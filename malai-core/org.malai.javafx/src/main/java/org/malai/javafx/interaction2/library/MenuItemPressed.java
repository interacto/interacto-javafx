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
package org.malai.javafx.interaction2.library;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

/**
 * A user interaction for menu items.
 * @author Arnaud BLOUIN
 */
public class MenuItemPressed extends MenuItemInteraction<MenuItemPressedFSM, MenuItem> {
	/**
	 * Creates the interaction.
	 */
	public MenuItemPressed() {
		super(new MenuItemPressedFSM());
		fsm.buildFSM(this);
	}

	@Override
	public void processMenuItemData(final Object menubutton) {
		if(menubutton instanceof MenuItem) {
			widget = (MenuItem) menubutton;
		}
	}

	@Override
	protected void onMenuItemUnregistered(final MenuItem menuItem) {
		menuItem.removeEventHandler(ActionEvent.ACTION, getActionHandler());
	}

	@Override
	protected void onMenuItemRegistered(final MenuItem menuItem) {
		menuItem.addEventHandler(ActionEvent.ACTION, getActionHandler());
	}
}

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
package org.malai.javafx.interaction2.library;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

/**
 * A user interaction for menu items.
 * @author Arnaud BLOUIN
 */
public class MenuItemPressed extends MenuItemInteraction<MenuItemPressedFSM, MenuItem> {
	private final MenuItemPressedFSM.MenuItemPressedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public MenuItemPressed() {
		super(new MenuItemPressedFSM());

		handler = new MenuItemPressedFSM.MenuItemPressedFSMHandler() {
			@Override
			public void initToPressedHandler(final ActionEvent event) {
				if(event.getSource() instanceof MenuItem) {
					widget = (MenuItem) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				MenuItemPressed.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
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

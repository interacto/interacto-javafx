/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

/**
 * A user interaction for menu items.
 * @author Arnaud BLOUIN
 */
public class MenuItemPressed extends MenuItemInteraction<WidgetData<MenuItem>, MenuItemPressedFSM> {
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
					((WidgetDataImpl<MenuItem>) data).setWidget((MenuItem) event.getSource());
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

	@Override
	protected WidgetDataImpl<MenuItem> createDataObject() {
		return new WidgetDataImpl<>();
	}
}

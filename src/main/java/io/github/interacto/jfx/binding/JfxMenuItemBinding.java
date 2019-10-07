/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
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
package io.github.interacto.jfx.binding;

import io.github.interacto.command.CommandImpl;
import io.github.interacto.jfx.interaction.library.MenuItemInteraction;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.List;
import java.util.function.Function;
import javafx.scene.control.MenuItem;

/**
 * Base of a menu item binding for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfxMenuItemBinding<C extends CommandImpl, I extends MenuItemInteraction<WidgetData<MenuItem>, ?, MenuItem>>
			extends JfXWidgetBinding<C, I, WidgetData<MenuItem>> {
	/**
	 * Creates a widget binding for menu items. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
	 * @param cmdClass The type of the command that will be created. Used to instantiate the command by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param menuItems The menu items concerned by the binding. Can be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfxMenuItemBinding(final boolean exec, final I interaction, final Function<WidgetData<MenuItem>, C> cmdClass, final List<MenuItem> menuItems) {
		super(exec, interaction, cmdClass, false, null);

		if(menuItems != null) {
			interaction.registerToMenuItems(menuItems);
		}
	}
}

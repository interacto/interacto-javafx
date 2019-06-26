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
package io.interacto.jfx.binding;

import io.interacto.command.CommandImpl;
import io.interacto.jfx.instrument.JfxInstrument;
import io.interacto.jfx.interaction.library.MenuItemPressed;
import io.interacto.jfx.interaction.library.WidgetData;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

/**
 * The binding builder to create bindings between a menu item interaction and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class MenuItemBinder<C extends CommandImpl> extends Binder<MenuItem, C, MenuItemPressed, WidgetData<MenuItem>, MenuItemBinder<C>> {
	protected List<ObservableList<? extends MenuItem>> additionalMenus;

	public MenuItemBinder(final Supplier<C> cmdClass, final JfxInstrument instrument) {
		this(i -> cmdClass.get(), instrument);
	}

	public MenuItemBinder(final Function<WidgetData<MenuItem>, C> cmdCreation, final JfxInstrument instrument) {
		super(new MenuItemPressed(), cmdCreation, instrument);
	}

	/**
	 * Specifies the observable list that will contain the menu items on which the binding must operate.
	 * When a menu item is added to this list, the added menu item is binded to this binding.
	 * When a menu item is removed from this list, this menu item is unbinded from this binding.
	 * @param menuItems The observable list of the menu items involved in the bindings.
	 * @return The builder to chain the buiding configuration.
	 */
	public MenuItemBinder<C> onMenu(final ObservableList<? extends MenuItem> menuItems) {
		if(additionalMenus == null) {
			additionalMenus = new ArrayList<>();
		}
		additionalMenus.add(menuItems);
		return this;
	}

	@Override
	public JfXWidgetBinding<C, MenuItemPressed, ?, WidgetData<MenuItem>> bind() {
		final JFxAnonMenuBinding<C, MenuItemPressed, JfxInstrument> binding = new JFxAnonMenuBinding<>(instrument, false, interaction, cmdProducer,
			initCmd, checkConditions, onEnd, widgets, additionalMenus);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

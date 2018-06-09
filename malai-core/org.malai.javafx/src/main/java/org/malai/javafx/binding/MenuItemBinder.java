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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemPressed;
import org.malai.javafx.interaction.library.WidgetData;

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

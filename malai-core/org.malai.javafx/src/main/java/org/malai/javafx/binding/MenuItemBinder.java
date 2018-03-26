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
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemPressed;

/**
 * The binding builder to create bindings between a menu item interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class MenuItemBinder<A extends ActionImpl> extends Binder<MenuItem, A, MenuItemPressed, MenuItemBinder<A>> {
	protected List<ObservableList<? extends MenuItem>> additionalMenus;

	public MenuItemBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new MenuItemPressed(), instrument);
	}

	/**
	 * Specifies the observable list that will contain the menu items on which the binding must operate.
	 * When a menu item is added to this list, the added menu item is binded to this binding.
	 * When a menu item is removed from this list, this menu item is unbinded from this binding.
	 * @param menuItems The observable list of the menu items involved in the bindings.
	 * @return The builder to chain the buiding configuration.
	 */
	public MenuItemBinder<A> onMenu(final ObservableList<? extends MenuItem> menuItems) {
		if(additionalMenus == null) {
			additionalMenus = new ArrayList<>();
		}
		additionalMenus.add(menuItems);
		return this;
	}

	@Override
	public JfXWidgetBinding<A, MenuItemPressed, ?> bind() {
		final JFxAnonMenuBinding<A, MenuItemPressed, JfxInstrument> binding = new JFxAnonMenuBinding<>(instrument, false, actionClass, interaction,
			initAction, checkConditions, onEnd, actionProducer, widgets, additionalMenus);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelActionButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

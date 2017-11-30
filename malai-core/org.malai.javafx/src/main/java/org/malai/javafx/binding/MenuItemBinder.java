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
package org.malai.javafx.binding;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.scene.control.MenuItem;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemPressed;

/**
 * The binding builder to create bindings between a menu item interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class MenuItemBinder<A extends ActionImpl> extends Binder<MenuItem, A, MenuItemPressed> {
	public MenuItemBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new MenuItemPressed(), instrument);
	}

	@Override
	public MenuItemBinder<A> on(final MenuItem... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public MenuItemBinder<A> first(final Consumer<A> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public MenuItemBinder<A> first(final BiConsumer<A, MenuItemPressed> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public MenuItemBinder<A> when(final Predicate<MenuItemPressed> checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public MenuItemBinder<A> when(final BooleanSupplier checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public MenuItemBinder<A> async() {
		super.async();
		return this;
	}

	@Override
	public MenuItemBinder<A> end(final BiConsumer<A, MenuItemPressed> onEndFct) {
		super.end(onEndFct);
		return this;
	}

	@Override
	public void bind() throws IllegalAccessException, InstantiationException {
		instrument.addBinding(new JFxAnonMenuBinding<>(instrument, false, actionClass, interaction, initAction,
			checkConditions, onEnd, widgets));
	}
}

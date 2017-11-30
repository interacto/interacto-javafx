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
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.ComboBoxSelected;

/**
 * The binding builder to create bindings between a combobox interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class ComboBoxBinder<A extends ActionImpl> extends Binder<ComboBox<?>, A, ComboBoxSelected> {
	public ComboBoxBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new ComboBoxSelected(), instrument);
	}

	@Override
	public ComboBoxBinder<A> on(final ComboBox<?>... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public ComboBoxBinder<A> first(final Consumer<A> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public ComboBoxBinder<A> first(final BiConsumer<A, ComboBoxSelected> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public ComboBoxBinder<A> when(final Predicate<ComboBoxSelected> checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public ComboBoxBinder<A> when(final BooleanSupplier checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public ComboBoxBinder<A> async() {
		super.async();
		return this;
	}

	@Override
	public ComboBoxBinder<A> end(final BiConsumer<A, ComboBoxSelected> onEndFct) {
		super.end(onEndFct);
		return this;
	}

	@Override
	public void bind() throws IllegalAccessException, InstantiationException {
		instrument.addBinding(new JFxAnonNodeBinding<>(instrument, false, actionClass, interaction,
			initAction, null, checkConditions, onEnd, null, null, widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), async));
	}
}

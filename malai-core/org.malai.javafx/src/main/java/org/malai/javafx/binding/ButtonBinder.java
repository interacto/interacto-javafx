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
import javafx.scene.control.Button;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.ButtonPressed;

/**
 * The binding builder to create bindings between a button interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class ButtonBinder<A extends ActionImpl> extends Binder<Button, A, ButtonPressed> {
	public ButtonBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new ButtonPressed(), instrument);
	}

	@Override
	public ButtonBinder<A> on(final Button... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public ButtonBinder<A> first(final Consumer<A> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public ButtonBinder<A> first(final BiConsumer<A, ButtonPressed> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public ButtonBinder<A> when(final Predicate<ButtonPressed> checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public ButtonBinder<A> when(final BooleanSupplier checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public ButtonBinder<A> end(final BiConsumer<A, ButtonPressed> onEndFct) {
		super.end(onEndFct);
		return this;
	}

	@Override
	public ButtonBinder<A> async() {
		super.async();
		return this;
	}

	@Override
	public void bind() throws IllegalAccessException, InstantiationException {
		instrument.addBinding(new JFxAnonNodeBinding<>(instrument, false, actionClass, interaction,
			initAction, null, checkConditions, onEnd, null, null, widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), async));
	}
}

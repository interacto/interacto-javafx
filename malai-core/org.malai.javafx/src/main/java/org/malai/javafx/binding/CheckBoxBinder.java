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
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.BoxChecked;

/**
 * The binding builder to create bindings between a checkbox interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class CheckBoxBinder<A extends ActionImpl> extends Binder<CheckBox, A, BoxChecked> {
	public CheckBoxBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new BoxChecked(), instrument);
	}

	@Override
	public CheckBoxBinder<A> on(final CheckBox... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public CheckBoxBinder<A> first(final Consumer<A> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public CheckBoxBinder<A> first(final BiConsumer<A, BoxChecked> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public CheckBoxBinder<A> when(final Predicate<BoxChecked> checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public CheckBoxBinder<A> async() {
		super.async();
		return this;
	}

	@Override
	public CheckBoxBinder<A> end(final BiConsumer<A, BoxChecked> onEndFct) {
		super.end(onEndFct);
		return this;
	}

	@Override
	public void bind() throws IllegalAccessException, InstantiationException {
		instrument.addBinding(new JFxAnonNodeBinding<>(instrument, false, actionClass, interaction,
			initAction, null, checkConditions, onEnd, null, null, widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), async));
	}
}

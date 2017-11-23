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
import javafx.scene.control.TextField;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.KeysTyped;

/**
 * The binding builder to create bindings between a text field interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class TextFieldBinder<A extends ActionImpl> extends UpdateBinder<TextField, A, KeysTyped> {
	public TextFieldBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, KeysTyped.class, instrument);
	}

	@Override
	public TextFieldBinder<A> update(final BiConsumer<A, KeysTyped> update) {
		super.update(update);
		return this;
	}

	@Override
	public TextFieldBinder<A> update(final Consumer<A> update) {
		super.update(update);
		return this;
	}

	@Override
	public TextFieldBinder<A> abort(final Runnable abort) {
		super.abort(abort);
		return this;
	}

	@Override
	public TextFieldBinder<A> feedback(final Runnable feedback) {
		super.feedback(feedback);
		return this;
	}

	@Override
	public TextFieldBinder<A> exec(final boolean execActionOnChanges) {
		super.exec(execActionOnChanges);
		return this;
	}

	@Override
	public TextFieldBinder<A> on(final TextField... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public TextFieldBinder<A> init(final Consumer<A> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public TextFieldBinder<A> init(final BiConsumer<A, KeysTyped> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public TextFieldBinder<A> check(final Predicate<KeysTyped> checkAction) {
		super.check(checkAction);
		return this;
	}

	@Override
	public TextFieldBinder<A> async() {
		super.async();
		return this;
	}

	@Override
	public void bind() throws IllegalAccessException, InstantiationException {
		instrument.addBinding(new JFxAnonNodeBinding<>(instrument, execOnChanges, actionClass, KeysTyped.class,
			initAction, updateFct, checkConditions, abortFct, feedbackFct, widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), async));
	}
}

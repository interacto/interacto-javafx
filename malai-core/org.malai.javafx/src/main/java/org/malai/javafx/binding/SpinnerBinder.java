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
import javafx.scene.control.Spinner;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.SpinnerValueChanged;

/**
 * The binding builder to create bindings between a spinner interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class SpinnerBinder<A extends ActionImpl> extends UpdateBinder<Spinner<?>, A, SpinnerValueChanged> {
	public SpinnerBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, SpinnerValueChanged.class, instrument);
	}

	@Override
	public SpinnerBinder<A> update(final BiConsumer<A, SpinnerValueChanged> update) {
		super.update(update);
		return this;
	}

	@Override
	public SpinnerBinder<A> update(final Consumer<A> update) {
		super.update(update);
		return this;
	}

	@Override
	public SpinnerBinder<A> abort(final Runnable abort) {
		super.abort(abort);
		return this;
	}

	@Override
	public SpinnerBinder<A> feedback(final Runnable feedback) {
		super.feedback(feedback);
		return this;
	}

	@Override
	public SpinnerBinder<A> exec(final boolean execActionOnChanges) {
		super.exec(execActionOnChanges);
		return this;
	}

	@Override
	public SpinnerBinder<A> on(final Spinner<?>... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public SpinnerBinder<A> init(final Consumer<A> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public SpinnerBinder<A> init(final BiConsumer<A, SpinnerValueChanged> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public SpinnerBinder<A> check(final Predicate<SpinnerValueChanged> checkAction) {
		super.check(checkAction);
		return this;
	}

	@Override
	public SpinnerBinder<A> async() {
		super.async();
		return this;
	}

	@Override
	public void bind() throws IllegalAccessException, InstantiationException {
		instrument.addBinding(new JFxAnonNodeBinding<>(instrument, execOnChanges, actionClass, SpinnerValueChanged.class,
			initAction, updateFct, checkConditions, abortFct, feedbackFct, widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), async));
	}
}

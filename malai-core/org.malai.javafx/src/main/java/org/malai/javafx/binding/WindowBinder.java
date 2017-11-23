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
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * The binding builder to create bindings between a given user interaction on a window and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class WindowBinder<A extends ActionImpl, I extends JfxInteraction> extends UpdateBinder<Window, A, I> {
	public WindowBinder(final Class<A> action, final Class<I> interaction, final JfxInstrument instrument) {
		super(action, interaction, instrument);
	}

	@Override
	public WindowBinder<A, I> update(final BiConsumer<A, I> update) {
		super.update(update);
		return this;
	}

	@Override
	public WindowBinder<A, I> update(final Consumer<A> update) {
		super.update(update);
		return this;
	}

	@Override
	public WindowBinder<A, I> abort(final Runnable abort) {
		super.abort(abort);
		return this;
	}

	@Override
	public WindowBinder<A, I> feedback(final Runnable feedback) {
		super.feedback(feedback);
		return this;
	}

	@Override
	public WindowBinder<A, I> exec(final boolean execActionOnChanges) {
		super.exec(execActionOnChanges);
		return this;
	}

	@Override
	public WindowBinder<A, I> on(final Window... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public WindowBinder<A, I> init(final Consumer<A> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public WindowBinder<A, I> init(final BiConsumer<A, I> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public WindowBinder<A, I> check(final Predicate<I> checkAction) {
		super.check(checkAction);
		return this;
	}

	@Override
	public WindowBinder<A, I> async() {
		super.async();
		return this;
	}

	@Override
	public void bind() throws IllegalAccessException, InstantiationException {
		instrument.addBinding(new JFxAnonNodeBinding<>(instrument, execOnChanges, actionClass, interactionClass, widgets, initAction, updateFct,
			checkConditions, abortFct, feedbackFct, async));
	}
}

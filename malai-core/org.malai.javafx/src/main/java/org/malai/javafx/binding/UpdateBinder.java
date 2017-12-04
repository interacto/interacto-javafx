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
import java.util.function.Function;
import java.util.function.Predicate;
import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * The base binding builder for bindings where actions can be updated while the user interaction is running.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public abstract class UpdateBinder<W, A extends Action, I extends JfxInteraction> extends Binder<W, A, I> {
	protected BiConsumer<A, I> updateFct;
	protected Runnable abortFct;
	protected Runnable feedbackFct;
	protected boolean execOnChanges;

	public UpdateBinder(final Class<A> action, final I interaction, final JfxInstrument instrument) {
		super(action, interaction, instrument);
		updateFct = null;
		execOnChanges = false;
	}

	/**
	 * Specifies the update of the action on interaction updates.
	 * @param update The callback method that updates the action.
	 * This callback takes as arguments the action to update and the ongoing interactions (and its parameters).
	 * @return The builder to chain the buiding configuration.
	 */
	public UpdateBinder<W, A, I> then(final BiConsumer<A, I> update) {
		updateFct = update;
		return this;
	}

	@Override
	public UpdateBinder<W, A, I> map(final Function<I, A> actionFunction) {
		actionProducer = actionFunction;
		return this;
	}

	@Override
	public UpdateBinder<W, A, I> first(final BiConsumer<A, I> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	/**
	 * Specifies the update of the action on interaction updates.
	 * @param update The callback method that updates the action.
	 * This callback takes as arguments the action to update.
	 * @return The builder to chain the buiding configuration.
	 */
	public UpdateBinder<W, A, I> then(final Consumer<A> update) {
		if(update != null) {
			updateFct = (a, i) -> update.accept(a);
		}
		return this;
	}

	/**
	 * Defines whether the action must be executed on each interaction updates (if 'when' predicate is ok).
	 * @param execActionOnChanges True: the action is executed on each interaction updates. The action must be undoable in this case
	 * (to cancel the action).
	 * @return The builder to chain the buiding configuration.
	 */
	public UpdateBinder<W, A, I> exec(final boolean execActionOnChanges) {
		execOnChanges = execActionOnChanges;
		return this;
	}

	@Override
	public UpdateBinder<W, A, I> on(final W... widget) {
		super.on(widget);
		return this;
	}

	/**
	 * Defines what to do when an action is aborted (because the interaction is abord map).
	 * The undoable action is autmatically cancelled so that nothing must be done on the action.
	 * @return The builder to chain the buiding configuration.
	 */
	public UpdateBinder<W, A, I> abort(final Runnable abort) {
		abortFct = abort;
		return this;
	}

	/**
	 * Defines interim feedback provided to users on each interaction updates.
	 * @return The builder to chain the buiding configuration.
	 */
	public UpdateBinder<W, A, I> feedback(final Runnable feedback) {
		feedbackFct = feedback;
		return this;
	}

	@Override
	public UpdateBinder<W, A, I> first(final Consumer<A> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public UpdateBinder<W, A, I> when(final Predicate<I> checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public UpdateBinder<W, A, I> when(final BooleanSupplier checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public UpdateBinder<W, A, I> end(final BiConsumer<A, I> onEndFct) {
		super.end(onEndFct);
		return this;
	}

	@Override
	public UpdateBinder<W, A, I> async() {
		super.async();
		return this;
	}
}

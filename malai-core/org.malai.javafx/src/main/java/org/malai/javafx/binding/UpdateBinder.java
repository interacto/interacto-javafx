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

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.scene.Node;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * The base binding builder for bindings where actions can be updated while the user interaction is running.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public abstract class UpdateBinder<W, A extends ActionImpl, I extends JfxInteraction, B extends UpdateBinder<W, A, I, B>> extends Binder<W, A, I, B> {
	protected BiConsumer<A, I> updateFct;
	protected BiConsumer<A, I> cancelFct;
	protected BiConsumer<A, I> endOrCancelFct;
	protected Runnable feedbackFct;
	protected boolean execOnChanges;
	protected boolean strictStart;

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
	public B then(final BiConsumer<A, I> update) {
		updateFct = update;
		return (B) this;
	}

	/**
	 * Specifies the update of the action on interaction updates.
	 * @param update The callback method that updates the action.
	 * This callback takes as arguments the action to update.
	 * @return The builder to chain the buiding configuration.
	 */
	public B then(final Consumer<A> update) {
		if(update != null) {
			updateFct = (a, i) -> update.accept(a);
		}
		return (B) this;
	}

	/**
	 * Defines whether the action must be executed on each interaction updates (if 'when' predicate is ok).
	 * @return The builder to chain the buiding configuration.
	 */
	public B exec() {
		execOnChanges = true;
		return (B) this;
	}

	/**
	 * Defines what to do when an action is aborted (because the interaction is abord map).
	 * The undoable action is autmatically cancelled so that nothing must be done on the action.
	 * @return The builder to chain the buiding configuration.
	 */
	public B cancel(final BiConsumer<A, I> cancel) {
		cancelFct = cancel;
		return (B) this;
	}

	/**
	 * Defines what to do when an action is aborted (because the interaction is abord map).
	 * The undoable action is autmatically cancelled so that nothing must be done on the action.
	 * @return The builder to chain the buiding configuration.
	 */
	public B endOrCancel(final BiConsumer<A, I> endOrCancel) {
		endOrCancelFct = endOrCancel;
		return (B) this;
	}

	/**
	 * Defines interim feedback provided to users on each interaction updates.
	 * @return The builder to chain the buiding configuration.
	 */
	public B feedback(final Runnable feedback) {
		feedbackFct = feedback;
		return (B) this;
	}

	/**
	 * The interaction does not start if the condition of the binding ('when') is not fulfilled.
	 * @return The builder to chain the buiding configuration.
	 */
	public B strictStart() {
		strictStart = true;
		return (B) this;
	}

	@Override
	public JfXWidgetBinding<A, I, ?> bind() throws IllegalAccessException, InstantiationException {
		final JFxAnonNodeBinding<A, I, JfxInstrument> binding = new JFxAnonNodeBinding<>
			(instrument, execOnChanges, actionClass, interaction, initAction, updateFct, checkConditions, onEnd, actionProducer, cancelFct,
				endOrCancelFct, feedbackFct, widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async,
				strictStart, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelActionButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

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
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.InteractionData;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * The base binding builder for bindings where commands can be updated while the user interaction is running.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public abstract class UpdateBinder<W, C extends CommandImpl, I extends JfxInteraction<D, ?, ?>, D extends InteractionData,
				B extends UpdateBinder<W, C, I, D, B>> extends Binder<W, C, I, D, B> {
	protected BiConsumer<D, C> updateFct;
	protected BiConsumer<D, C> cancelFct;
	protected BiConsumer<D, C> endOrCancelFct;
	protected Runnable feedbackFct;
	protected boolean execOnChanges;
	protected boolean strictStart;

	public UpdateBinder(final I interaction, final Class<C> cmdClass, final JfxInstrument instrument) {
		super(interaction, cmdClass, instrument);
		updateFct = null;
		execOnChanges = false;
	}

	/**
	 * Specifies the update of the command on interaction command.
	 * @param update The callback method that updates the action.
	 * This callback takes as arguments the command to update and the ongoing interactions (and its parameters).
	 * @return The builder to chain the buiding configuration.
	 */
	public B then(final BiConsumer<D, C> update) {
		updateFct = update;
		return (B) this;
	}

	/**
	 * Specifies the update of the command on interaction updates.
	 * @param update The callback method that updates the command.
	 * This callback takes as arguments the command to update.
	 * @return The builder to chain the buiding configuration.
	 */
	public B then(final Consumer<C> update) {
		if(update != null) {
			updateFct = (i, c) -> update.accept(c);
		}
		return (B) this;
	}

	/**
	 * Defines whether the command must be executed on each interaction updates (if 'when' predicate is ok).
	 * @return The builder to chain the buiding configuration.
	 */
	public B exec() {
		execOnChanges = true;
		return (B) this;
	}

	/**
	 * Defines what to do when a command is aborted (because the interaction is abord map).
	 * The undoable command is autmatically cancelled so that nothing must be done on the command.
	 * @return The builder to chain the buiding configuration.
	 */
	public B cancel(final BiConsumer<D, C> cancel) {
		cancelFct = cancel;
		return (B) this;
	}

	/**
	 * Defines what to do when ancommand is aborted (because the interaction is abord map).
	 * The undoable command is autmatically cancelled so that nothing must be done on the command.
	 * @return The builder to chain the buiding configuration.
	 */
	public B endOrCancel(final BiConsumer<D, C> endOrCancel) {
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
	public JfXWidgetBinding<C, I, ?, D> bind() {
		final JFxAnonNodeBinding<C, I, JfxInstrument, D> binding = new JFxAnonNodeBinding<>
			(instrument, execOnChanges, interaction, cmdClass, initCmd, updateFct, checkConditions, onEnd, cmdProducer, cancelFct,
				endOrCancelFct, feedbackFct, widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async,
				strictStart, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

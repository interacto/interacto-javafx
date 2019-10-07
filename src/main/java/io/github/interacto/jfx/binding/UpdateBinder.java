/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.binding;

import io.github.interacto.command.CommandImpl;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.JfxInteraction;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.scene.Node;

/**
 * The base binding builder for bindings where commands can be updated while the user interaction is running.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public abstract class UpdateBinder<W, C extends CommandImpl, I extends JfxInteraction<D, ?, ?>, D extends InteractionData,
				B extends UpdateBinder<W, C, I, D, B>> extends Binder<W, C, I, D, B> {
	protected BiConsumer<D, C> updateFct;
	protected Consumer<D> cancelFct;
	protected Consumer<D> endOrCancelFct;
	protected boolean continuousCmdExecution;
	protected boolean strictStart;
	protected long throttleTimeout;

	public UpdateBinder(final I interaction, final Function<D, C> cmdCreation, final JfxInstrument instrument) {
		super(interaction, cmdCreation, instrument);
		updateFct = null;
		continuousCmdExecution = false;
		throttleTimeout = 0L;
	}

	/**
	 * Specifies the update of the command on interaction command.
	 * @param update The callback method that updates the action.
	 * This callback takes as arguments the command to update and the ongoing interactions (and its parameters).
	 * @return The builder to chain the building configuration.
	 */
	public B then(final BiConsumer<D, C> update) {
		updateFct = update;
		return (B) this;
	}

	/**
	 * Specifies the update of the command on interaction updates.
	 * @param update The callback method that updates the command.
	 * This callback takes as arguments the command to update.
	 * @return The builder to chain the building configuration.
	 */
	public B then(final Consumer<C> update) {
		if(update != null) {
			updateFct = (i, c) -> update.accept(c);
		}
		return (B) this;
	}

	/**
	 * Specifies whether the command must be executed on each evolution of the interaction (if 'when' predicate is ok).
	 * @return The builder to chain the building configuration.
	 */
	public B continuousExecution() {
		continuousCmdExecution = true;
		return (B) this;
	}

	/**
	 * Defines what to do when a command is cancelled (because the interaction is cancelled).
	 * The undoable command is automatically cancelled so that nothing must be done on the command.
	 * @return The builder to chain the building configuration.
	 */
	public B cancel(final Consumer<D> cancel) {
		cancelFct = cancel;
		return (B) this;
	}

	/**
	 * Defines what to do when a command is cancelled (because the interaction is cancelled).
	 * The undoable command is automatically cancelled so that nothing must be done on the command.
	 * @return The builder to chain the building configuration.
	 */
	public B endOrCancel(final Consumer<D> endOrCancel) {
		endOrCancelFct = endOrCancel;
		return (B) this;
	}


	/**
	 * The interaction does not start if the condition of the binding ('when') is not fulfilled.
	 * @return The builder to chain the building configuration.
	 */
	public B strictStart() {
		strictStart = true;
		return (B) this;
	}

	/**
	 * Backpressure operation. Instead of emitting all the events, successive events of the same type are factorized modulo a timeout.
	 * The timeout is used to send at max one event of the same type in a given duration (the timeout).
	 * For example with three mouse moves and a time out of 10ms.
	 * The first move is received and processed. The timer starts. A second mouse moves is received at T+5ms.
	 * It is for the moment not processed. A third mouse move is received at T+8ms. The second move is finally ignored and this third one not processed yet.
	 * At T+10s the third event is finally processed.
	 * Based on our own experiments, the given timeout value should be greater than 10ms to throttle some UI events.
	 * @param timeout The timeout used by the throttle operation. In ms.
	 * @return The builder to chain the building configuration.
	 */
	public B throttle(final long timeout) {
		throttleTimeout = timeout;
		return (B) this;
	}

	@Override
	public JfXWidgetBinding<C, I, D> bind() {
		final var binding = new JFxAnonNodeBinding<>(continuousCmdExecution, interaction, initCmd, updateFct, checkConditions, onEnd, cmdProducer, cancelFct,
				endOrCancelFct, widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async,
				strictStart, throttleTimeout, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

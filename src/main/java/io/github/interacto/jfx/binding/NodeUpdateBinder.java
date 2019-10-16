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

import io.github.interacto.command.Command;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.binding.api.BaseUpdateBinder;
import io.github.interacto.jfx.binding.api.CmdUpdateBinder;
import io.github.interacto.jfx.binding.api.InteractionCmdUpdateBinder;
import io.github.interacto.jfx.binding.api.InteractionUpdateBinder;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * The base binding builder for bindings where commands can be updated while the user interaction is running.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */

class NodeUpdateBinder<W extends Node, C extends Command, I extends JfxInteraction<D, ?, ?>, D extends InteractionData>
			extends Binder<W, C, I, D> implements InteractionUpdateBinder<W, I, D>, CmdUpdateBinder<W, C>,
				InteractionCmdUpdateBinder<W, C, I, D>, BaseUpdateBinder<W> {
	private BiConsumer<D, C> updateFct;
	private Consumer<D> cancelFct;
	private Consumer<D> endOrCancelFct;
	private boolean continuousCmdExecution;
	private boolean strictStart;
	private long throttleTimeout;

	NodeUpdateBinder(final JfxInstrument instrument) {
		super(instrument);
		updateFct = null;
		continuousCmdExecution = false;
		throttleTimeout = 0L;
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> then(final BiConsumer<D, C> update) {
		updateFct = update;
		return this;
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> then(final Consumer<C> update) {
		if(update != null) {
			updateFct = (i, c) -> update.accept(c);
		}
		return this;
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> continuousExecution() {
		continuousCmdExecution = true;
		return this;
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> cancel(final Consumer<D> cancel) {
		cancelFct = cancel;
		return this;
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> endOrCancel(final Consumer<D> endOrCancel) {
		endOrCancelFct = endOrCancel;
		return this;
	}


	@Override
	public NodeUpdateBinder<W, C, I, D> strictStart() {
		strictStart = true;
		return this;
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> throttle(final long timeout) {
		throttleTimeout = timeout;
		return this;
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> on(final W... widgets) {
		return (NodeUpdateBinder<W, C, I, D>) super.on(widgets);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> on(final ObservableList<? extends W> widgets) {
		return (NodeUpdateBinder<W, C, I, D>) super.on(widgets);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> first(final Consumer<C> initCmdFct) {
		return (NodeUpdateBinder<W, C, I, D>) super.first(initCmdFct);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> first(final BiConsumer<D, C> initCmdFct) {
		return (NodeUpdateBinder<W, C, I, D>) super.first(initCmdFct);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> when(final Predicate<D> checkCmd) {
		return (NodeUpdateBinder<W, C, I, D>) super.when(checkCmd);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> when(final BooleanSupplier checkCmd) {
		return (NodeUpdateBinder<W, C, I, D>) super.when(checkCmd);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp) {
		return (NodeUpdateBinder<W, C, I, D>) super.async(cancel, progressProp, msgProp);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> end(final Consumer<D> onEndFct) {
		return (NodeUpdateBinder<W, C, I, D>) super.end(onEndFct);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> log(final LogLevel level) {
		return (NodeUpdateBinder<W, C, I, D>) super.log(level);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> help(final HelpAnimation animation) {
		return (NodeUpdateBinder<W, C, I, D>) super.help(animation);
	}

	@Override
	public NodeUpdateBinder<W, C, I, D> help(final Pane helpPane) {
		return (NodeUpdateBinder<W, C, I, D>) super.help(helpPane);
	}

	@Override
	public <I2 extends JfxInteraction<D2, ?, ?>, D2 extends InteractionData> NodeUpdateBinder<W, C, I2, D2> usingInteraction(final Supplier<I2> interactionSupplier) {
		this.interactionSupplier = (Supplier<I>) interactionSupplier;
		return (NodeUpdateBinder<W, C, I2, D2>) this;
	}

	@Override
	public <C2 extends Command> NodeUpdateBinder<W, C2, I, D> toProduce(final Supplier<C2> cmdSupplier) {
		cmdProducer = i -> (C) cmdSupplier.get();
		return (NodeUpdateBinder<W, C2, I, D>) this;
	}

	@Override
	public <C2 extends Command> NodeUpdateBinder<W, C2, I, D> toProduce(final Function<D, C2> cmdCreation) {
		cmdProducer = (Function<D, C>) cmdCreation;
		return (NodeUpdateBinder<W, C2, I, D>) this;
	}

	@Override
	public JfXWidgetBinding<C, I, D> bind() {
		final List<ObservableList<? extends Node>> adds = additionalWidgets == null ? null :
			additionalWidgets.stream().map(l -> (ObservableList<? extends Node>) l).collect(Collectors.toList());

		final var binding = new JFxAnonNodeBinding<>(continuousCmdExecution, interactionSupplier.get(), initCmd, updateFct,
				checkConditions, onEnd, cmdProducer, cancelFct, endOrCancelFct,
				widgets.stream().map(elt -> (Node) elt).collect(Collectors.toList()), adds,
				async, strictStart, throttleTimeout, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		if(instrument != null) {
			instrument.addBinding(binding);
		}
		return binding;
	}
}

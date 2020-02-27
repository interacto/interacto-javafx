/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
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

import io.github.interacto.command.AnonCommand;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.binding.api.LogLevel;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;

class AnonCmdBinder<W, I extends JfxInteraction<D, ?, ?>, D extends InteractionData> extends Binder<W, AnonCommand, I, D> {
	AnonCmdBinder(final Runnable anonCmd, final JfxInstrument ins, final BindingsObserver observer) {
		super(ins, observer);
		if(anonCmd == null) {
			throw new IllegalArgumentException();
		}
		cmdProducer = i -> new AnonCommand(anonCmd);
	}

	AnonCmdBinder(final BiConsumer<D, AnonCommand> initCmd, final Predicate<D> checkConditions, final Function<D, AnonCommand> cmdProducer,
		final List<W> widgets, final Supplier<I> interactionSupplier, final JfxInstrument instrument, final boolean async, final BiConsumer<D, AnonCommand> onEnd,
		final List<ObservableList<? extends W>> additionalWidgets, final EnumSet<LogLevel> logLevels, final HelpAnimation helpAnimation,
		final boolean withHelp, final DoubleProperty progressProp, final StringProperty msgProp, final Button cancel,
		final BiConsumer<D, AnonCommand> hadNoEffectFct, final BiConsumer<D, AnonCommand> hadEffectsFct, final BiConsumer<D, AnonCommand> cannotExecFct,
		final BindingsObserver observer, final boolean consumeEvents) {
		super(initCmd, checkConditions, cmdProducer, widgets, interactionSupplier, instrument, async, onEnd, additionalWidgets, logLevels, helpAnimation,
			withHelp, progressProp, msgProp, cancel, hadNoEffectFct, hadEffectsFct, cannotExecFct, observer, consumeEvents);
	}

	@Override
	protected AnonCmdBinder<W, I, D> duplicate() {
		return new AnonCmdBinder<>(initCmd, checkConditions, cmdProducer, widgets, interactionSupplier, instrument, async,
			onEnd, additionalWidgets, logLevels, helpAnimation, withHelp, progressProp, msgProp, cancel, hadNoEffectFct, hadEffectsFct, cannotExecFct,
			observer, consumeEvents);
	}

	@Override
	public JfxWidgetBinding<AnonCommand, I, D> bind() {
		final List<ObservableList<? extends Node>> adds = additionalWidgets == null ? null :
			additionalWidgets.stream().map(l -> (ObservableList<? extends Node>) l).collect(Collectors.toList());

		final JfxAnonNodeBinding<AnonCommand, I, D> binding = new JfxAnonNodeBinding<>(false, interactionSupplier.get(),
			null, null, checkConditions, onEnd, cmdProducer, null, null,
			widgets.stream().map(elt -> (Node) elt).collect(Collectors.toList()), adds,
			async, false, 0L, logLevels, withHelp, helpAnimation, hadNoEffectFct, hadEffectsFct, cannotExecFct, consumeEvents);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		if(instrument != null) {
			instrument.addBinding(binding);
		}
		if(observer != null) {
			observer.observeBinding(binding);
		}
		return binding;
	}
}

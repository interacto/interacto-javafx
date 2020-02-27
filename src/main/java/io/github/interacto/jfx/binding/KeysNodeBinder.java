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

import io.github.interacto.command.Command;
import io.github.interacto.jfx.binding.api.LogLevel;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.KeysData;
import io.github.interacto.jfx.interaction.library.KeysPressed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

/**
 * The binding builder to create bindings between a key interaction (eg shortcuts) on a node and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
class KeysNodeBinder<C extends Command> extends KeysBinder<Node, C> {
	KeysNodeBinder(final JfxInstrument instrument, final BindingsObserver observer) {
		this(null, null, null, Collections.emptyList(),
			instrument, false, null, Collections.emptyList(), EnumSet.noneOf(LogLevel.class),
			null, false, null, null, null, Collections.emptyList(), null, null,
			null, observer, false);
	}

	KeysNodeBinder(final BiConsumer<KeysData, C> initCmd, final Predicate<KeysData> checkConditions, final Function<KeysData, C> cmdProducer,
		final List<Node> widgets, final JfxInstrument instrument, final boolean async, final BiConsumer<KeysData, C> onEnd, final List<ObservableList<?
		extends Node>> additionalWidgets, final EnumSet<LogLevel> logLevels, final HelpAnimation helpAnimation, final boolean withHelp,
		final DoubleProperty progressProp, final StringProperty msgProp, final Button cancel, final Collection<KeyCode> codes,
		final BiConsumer<KeysData, C> hadNoEffectFct, final BiConsumer<KeysData, C> hadEffectsFct, final BiConsumer<KeysData, C> cannotExecFct,
		final BindingsObserver observer, final boolean consumeEvents) {
		super(initCmd, checkConditions, cmdProducer, widgets, instrument, async, onEnd, additionalWidgets, logLevels, helpAnimation, withHelp, progressProp,
			msgProp, cancel, codes, hadNoEffectFct, hadEffectsFct, cannotExecFct, observer, consumeEvents);
	}

	@Override
	protected KeysNodeBinder<C> duplicate() {
		return new KeysNodeBinder<>(initCmd, checkConditions, cmdProducer, widgets, instrument, async,
			onEnd, additionalWidgets, logLevels, helpAnimation, withHelp, progressProp, msgProp, cancel, new ArrayList<>(codes),
			hadNoEffectFct, hadEffectsFct, cannotExecFct, observer, consumeEvents);
	}

	@Override
	public JfxWidgetBinding<C, KeysPressed, KeysData> bind() {
		final JfxAnonNodeBinding<C, KeysPressed, KeysData> binding = new JfxAnonNodeBinding<>(false, interactionSupplier.get(),
			initCmd, null, checkCode, onEnd, cmdProducer, null, null, widgets, additionalWidgets,
			async, false, 0L, logLevels, withHelp, helpAnimation, hadNoEffectFct, hadEffectsFct,
			cannotExecFct, consumeEvents);
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

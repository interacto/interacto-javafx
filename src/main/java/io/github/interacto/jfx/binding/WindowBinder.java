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
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.WidgetData;
import io.github.interacto.logging.LogLevel;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Window;

/**
 * The binding builder to create bindings between a given user interaction on a window and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
class WindowBinder<C extends Command, I extends JfxInteraction<WidgetData<Window>, ?, ?>> extends Binder<Window, C, I, WidgetData<Window>> {
	WindowBinder(final JfxInstrument instrument, final BindingsObserver observer) {
		super(instrument, observer);
	}

	WindowBinder(final BiConsumer<WidgetData<Window>, C> initCmd, final Predicate<WidgetData<Window>> checkConditions,
		final Function<WidgetData<Window>, C> cmdProducer, final List<Window> widgets, final Supplier<I> interactionSupplier, final JfxInstrument instrument,
		final boolean async, final BiConsumer<WidgetData<Window>, C> onEnd, final List<ObservableList<? extends Window>> additionalWidgets,
		final EnumSet<LogLevel> logLevels, final HelpAnimation helpAnimation, final boolean withHelp, final DoubleProperty progressProp,
		final StringProperty msgProp, final Button cancel, final BiConsumer<WidgetData<Window>, C> hadNoEffectFct,
		final BiConsumer<WidgetData<Window>, C> hadEffectsFct, final BiConsumer<WidgetData<Window>, C> cannotExecFct, final BindingsObserver observer) {
		super(initCmd, checkConditions, cmdProducer, widgets, interactionSupplier, instrument, async, onEnd, additionalWidgets, logLevels, helpAnimation,
			withHelp, progressProp, msgProp, cancel, hadNoEffectFct, hadEffectsFct, cannotExecFct, observer);
	}

	@Override
	protected WindowBinder<C, I> duplicate() {
		return new WindowBinder<>(initCmd, checkConditions, cmdProducer, widgets, interactionSupplier, instrument, async,
			onEnd, additionalWidgets, logLevels, helpAnimation, withHelp, progressProp, msgProp, cancel, hadNoEffectFct,
			hadEffectsFct, cannotExecFct, observer);
	}

	@Override
	public JfxWidgetBinding<C, I, WidgetData<Window>> bind() {
		final JfxAnonWindowBinding<C, I, WidgetData<Window>> binding = new JfxAnonWindowBinding<>(false, interactionSupplier.get(),
			initCmd, null, checkConditions, onEnd, cmdProducer, null, null, widgets, false, false,
			10L, Collections.emptySet(), false, null, hadNoEffectFct, hadEffectsFct, cannotExecFct);
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

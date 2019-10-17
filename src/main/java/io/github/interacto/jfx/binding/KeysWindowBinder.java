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
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.KeysData;
import io.github.interacto.jfx.interaction.library.KeysPressed;
import io.github.interacto.logging.LogLevel;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;

/**
 * The binding builder to create bindings between a key interaction (eg shortcuts) on a window and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
class KeysWindowBinder<C extends Command> extends KeysBinder<Window, C> {
	KeysWindowBinder(final JfxInstrument instrument) {
		super(instrument);
	}

	KeysWindowBinder(final BiConsumer<KeysData, C> initCmd, final Predicate<KeysData> checkConditions, final Function<KeysData, C> cmdProducer,
		final List<Window> widgets, final JfxInstrument instrument, final boolean async,
		final Consumer<KeysData> onEnd, final List<ObservableList<? extends Window>> additionalWidgets, final EnumSet<LogLevel> logLevels,
		final HelpAnimation helpAnimation, final boolean withHelp, final DoubleProperty progressProp, final StringProperty msgProp, final Button cancel,
		final Collection<KeyCode> codes) {
		super(initCmd, checkConditions, cmdProducer, widgets, instrument, async, onEnd, additionalWidgets, logLevels, helpAnimation,
			withHelp, progressProp, msgProp, cancel, codes);
	}

	@Override
	protected KeysWindowBinder<C> duplicate() {
		return new KeysWindowBinder<>(initCmd, checkConditions, cmdProducer, widgets, instrument, async,
			onEnd, additionalWidgets, logLevels, helpAnimation, withHelp, progressProp, msgProp, cancel, codes);
	}

	@Override
	public JfXWidgetBinding<C, KeysPressed, KeysData> bind() {
		final JFxAnonNodeBinding<C, KeysPressed, KeysData> binding = new JFxAnonNodeBinding<>(false, interactionSupplier.get(),
			widgets, initCmd, null, checkCode, onEnd, cmdProducer, null, null, async, false, 0L,
			logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		if(instrument != null) {
			instrument.addBinding(binding);
		}
		return binding;
	}
}

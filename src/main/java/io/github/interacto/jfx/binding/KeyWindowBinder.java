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
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.library.KeysData;
import io.github.interacto.jfx.interaction.library.KeysPressed;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.stage.Window;

/**
 * The binding builder to create bindings between a key interaction (eg shorcuts) on a window and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class KeyWindowBinder<C extends CommandImpl> extends KeyBinder<Window, C, KeyWindowBinder<C>> {
	public KeyWindowBinder(final Supplier<C> cmdClass, final JfxInstrument instrument) {
		this(i -> cmdClass.get(), instrument);
	}

	public KeyWindowBinder(final Function<KeysData, C> cmdCreation, final JfxInstrument instrument) {
		super(cmdCreation, instrument);
	}

	@Override
	public JfXWidgetBinding<C, KeysPressed, ?, KeysData> bind() {
		final JFxAnonNodeBinding<C, KeysPressed, JfxInstrument, KeysData> binding = new JFxAnonNodeBinding<>(instrument, false, interaction,
			widgets, initCmd, null, checkCode, onEnd, cmdProducer, null, null, null, async, false, 0L,
			logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

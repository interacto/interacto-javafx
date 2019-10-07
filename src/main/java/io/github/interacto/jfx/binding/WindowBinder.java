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
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.stage.Window;

/**
 * The binding builder to create bindings between a given user interaction on a window and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class WindowBinder<C extends CommandImpl, I extends JfxInteraction<WidgetData<Window>, ?, ?>>
				extends UpdateBinder<Window, C, I, WidgetData<Window>, WindowBinder<C, I>> {
	public WindowBinder(final I interaction, final Supplier<C> cmdClass, final JfxInstrument instrument) {
		this(interaction, i -> cmdClass.get(), instrument);
	}

	public WindowBinder(final I interaction, final Function<WidgetData<Window>, C> cmdCreation, final JfxInstrument instrument) {
		super(interaction, cmdCreation, instrument);
	}

	@Override
	public JfXWidgetBinding<C, I, WidgetData<Window>> bind() {
		final JFxAnonNodeBinding<C, I, WidgetData<Window>> binding = new JFxAnonNodeBinding<>(continuousCmdExecution, interaction,
			widgets, initCmd, updateFct, checkConditions, onEnd, cmdProducer, cancelFct, endOrCancelFct, async, strictStart, 0L,
			logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

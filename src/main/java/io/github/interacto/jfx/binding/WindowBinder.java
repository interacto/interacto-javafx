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
import io.github.interacto.jfx.interaction.library.WidgetData;
import javafx.stage.Window;

/**
 * The binding builder to create bindings between a given user interaction on a window and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
class WindowBinder<C extends Command, I extends JfxInteraction<WidgetData<Window>, ?, ?>> extends Binder<Window, C, I, WidgetData<Window>> {
	WindowBinder(final JfxInstrument instrument) {
		super(instrument);
	}

	@Override
	public JfXWidgetBinding<C, I, WidgetData<Window>> bind() {
		final JFxAnonNodeBinding<C, I, WidgetData<Window>> binding = new JFxAnonNodeBinding<>(false, interactionSupplier.get(),
			widgets, initCmd, null, checkConditions, onEnd, cmdProducer, null, null, async,
			false, 0L, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		if(instrument != null) {
			instrument.addBinding(binding);
		}
		return binding;
	}
}
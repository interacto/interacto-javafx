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

import io.github.interacto.command.AnonCommand;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.JfxInteraction;
import java.util.stream.Collectors;
import javafx.scene.Node;

public class AnonCmdBinder<W, I extends JfxInteraction<D, ?, ?>, D extends InteractionData> extends Binder<W, AnonCommand, I, D, AnonCmdBinder<W, I, D>> {
	public AnonCmdBinder(final I interaction, final Runnable anonCmd, final JfxInstrument ins) {
		super(interaction, i -> new AnonCommand(anonCmd), ins);
		if(anonCmd == null) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public JfXWidgetBinding<AnonCommand, I, D> bind() {
		final JFxAnonNodeBinding<AnonCommand, I, D> binding = new JFxAnonNodeBinding<>(false, interaction,
			null, null, checkConditions, onEnd, cmdProducer, null, null,
			widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async, false, 0L, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		if(instrument != null) {
			instrument.addBinding(binding);
		}
		return binding;
	}
}

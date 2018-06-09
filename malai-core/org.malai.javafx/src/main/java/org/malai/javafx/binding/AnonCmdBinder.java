/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.binding;

import java.util.stream.Collectors;
import javafx.scene.Node;
import org.malai.command.AnonCommand;
import org.malai.interaction.InteractionData;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

public class AnonCmdBinder<W, I extends JfxInteraction<D, ?, ?>, D extends InteractionData> extends Binder<W, AnonCommand, I, D, AnonCmdBinder<W, I, D>> {
	public AnonCmdBinder(final I interaction, final Runnable anonCmd, final JfxInstrument ins) {
		super(interaction, i -> new AnonCommand(anonCmd), ins);
		if(anonCmd == null) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public JfXWidgetBinding<AnonCommand, I, ?, D> bind() {
		final JFxAnonNodeBinding<AnonCommand, I, JfxInstrument, D> binding = new JFxAnonNodeBinding<>(instrument, false, interaction,
			null, null, checkConditions, onEnd, cmdProducer, null, null, null,
			widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async, false, 0L, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

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

import java.util.Objects;
import java.util.stream.Collectors;
import javafx.scene.Node;
import org.malai.command.AnonCommand;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

public class AnonCmdBinder<W, I extends JfxInteraction<?, ?>> extends Binder<W, AnonCommand, I, AnonCmdBinder<W, I>> {
	private final Runnable cmd;

	public AnonCmdBinder(final Runnable anonCmd, final I interaction, final JfxInstrument ins) {
		super(AnonCommand.class, interaction, ins);
		cmd = Objects.requireNonNull(anonCmd);
	}

	@Override
	public JfXWidgetBinding<AnonCommand, I, ?> bind() {
		final AnonJfxWidgetBinding<I, JfxInstrument> binding = new AnonJfxWidgetBinding<>(instrument, false, cmd, interaction,
			null, null, checkConditions, onEnd, cmdProducer, null, null, null,
			widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async, false, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

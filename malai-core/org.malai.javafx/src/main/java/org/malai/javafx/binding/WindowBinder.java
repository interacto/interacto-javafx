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

import javafx.stage.Window;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * The binding builder to create bindings between a given user interaction on a window and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class WindowBinder<C extends CommandImpl, I extends JfxInteraction<?, ?>> extends UpdateBinder<Window, C, I, WindowBinder<C, I>> {
	public WindowBinder(final Class<C> cmdClass, final I interaction, final JfxInstrument instrument) {
		super(cmdClass, interaction, instrument);
	}

	@Override
	public JfXWidgetBinding<C, I, ?> bind() {
		final JFxAnonNodeBinding<C, I, JfxInstrument> binding = new JFxAnonNodeBinding<>
			(instrument, execOnChanges, cmdClass, interaction, widgets, initCmd, updateFct, checkConditions, onEnd, cmdProducer, cancelFct,
				endOrCancelFct, feedbackFct, async, strictStart, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

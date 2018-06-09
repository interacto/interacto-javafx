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

import java.util.function.Function;
import java.util.function.Supplier;
import javafx.stage.Window;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.library.WidgetData;

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
	public JfXWidgetBinding<C, I, ?, WidgetData<Window>> bind() {
		final JFxAnonNodeBinding<C, I, JfxInstrument, WidgetData<Window>> binding = new JFxAnonNodeBinding<>(instrument, execOnChanges, interaction,
			widgets, initCmd, updateFct, checkConditions, onEnd, cmdProducer, cancelFct, endOrCancelFct, feedbackFct, async, strictStart, 0L,
			logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

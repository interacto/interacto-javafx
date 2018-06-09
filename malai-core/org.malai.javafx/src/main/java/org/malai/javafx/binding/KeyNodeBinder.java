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
import javafx.scene.Node;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.KeysData;
import org.malai.javafx.interaction.library.KeysPressed;

/**
 * The binding builder to create bindings between a key interaction (eg shortcuts) on a node and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class KeyNodeBinder<C extends CommandImpl> extends KeyBinder<Node, C, KeyNodeBinder<C>> {
	public KeyNodeBinder(final Supplier<C> cmdCreation, final JfxInstrument instrument) {
		this(i -> cmdCreation.get(), instrument);
	}

	public KeyNodeBinder(final Function<KeysData, C> cmdCreation, final JfxInstrument instrument) {
		super(cmdCreation, instrument);
	}

	@Override
	public JfXWidgetBinding<C, KeysPressed, ?, KeysData> bind() {
		final JFxAnonNodeBinding<C, KeysPressed, JfxInstrument, KeysData> binding = new JFxAnonNodeBinding<>(instrument, false, interaction,
			initCmd, null, checkCode, onEnd, cmdProducer, null, null, null, widgets, additionalWidgets,
			async, false, 0L, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

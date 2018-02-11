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

import javafx.scene.Node;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.KeysPressed;

/**
 * The binding builder to create bindings between a key interaction (eg shortcuts) on a node and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class KeyNodeBinder<A extends ActionImpl> extends KeyBinder<Node, A, KeyNodeBinder<A>> {
	public KeyNodeBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, instrument);
	}

	@Override
	public JfXWidgetBinding<A, KeysPressed, ?> bind() throws IllegalAccessException, InstantiationException {
		final JFxAnonNodeBinding<A, KeysPressed, JfxInstrument> binding = new JFxAnonNodeBinding<>(instrument, false, actionClass, interaction,
			initAction, null, checkCode, onEnd, actionProducer, null, null, null,
			widgets, additionalWidgets, async, false, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelActionButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

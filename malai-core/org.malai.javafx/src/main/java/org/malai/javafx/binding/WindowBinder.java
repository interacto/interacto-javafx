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
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * The binding builder to create bindings between a given user interaction on a window and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class WindowBinder<A extends ActionImpl, I extends JfxInteraction> extends UpdateBinder<Window, A, I, WindowBinder<A, I>> {
	public WindowBinder(final Class<A> action, final I interaction, final JfxInstrument instrument) {
		super(action, interaction, instrument);
	}

	@Override
	public JfXWidgetBinding<A, I, ?> bind() throws IllegalAccessException, InstantiationException {
		final JFxAnonNodeBinding<A, I, JfxInstrument> binding = new JFxAnonNodeBinding<>
			(instrument, execOnChanges, actionClass, interaction, widgets, initAction, updateFct, checkConditions, onEnd, actionProducer, cancelFct,
				endOrCancelFct, feedbackFct, async, strictStart, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelActionButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

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
import org.malai.action.AnonAction;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

public class AnonActionBinder<W, I extends JfxInteraction> extends Binder<W, AnonAction, I, AnonActionBinder<W, I>> {
	private final Runnable action;

	public AnonActionBinder(final Runnable anonAction, final I interaction, final JfxInstrument ins) {
		super(AnonAction.class, interaction, ins);
		action = Objects.requireNonNull(anonAction);
	}

	@Override
	public JfXWidgetBinding<AnonAction, I, ?> bind() throws IllegalAccessException, InstantiationException {
		final AnonJfxWidgetBinding<I, JfxInstrument> binding = new AnonJfxWidgetBinding<>(instrument, false, action, interaction,
			null, null, checkConditions, onEnd, actionProducer, null, null, null,
			widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async, false, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelActionButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}

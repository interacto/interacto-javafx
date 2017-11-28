/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxColorPickedTransition;

/**
 * An interaction dedicated to the use of colour pickers.
 * @author Arnaud BLOUIN
 */
public class ColorPicked extends NodeInteraction<ColorPicker> {
	/**
	 * Creates the interaction.
	 */
	public ColorPicked() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("picked"); //$NON-NLS-1$

		addState(pressed);

		new JfxColorPickedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ColorPicked.this.widget = this.widget;
			}
		};
	}

	@Override
	public void registerToNodes(final Collection<Node> widgets) {
		super.registerToNodes(widgets);

		if (widgets != null) {
			widgets.stream().filter(w -> w instanceof ColorPicker).forEach(w ->
					w.addEventHandler(ActionEvent.ACTION, evt -> onJfxColorPicked((ColorPicker) evt.getSource())));
		}
	}
}

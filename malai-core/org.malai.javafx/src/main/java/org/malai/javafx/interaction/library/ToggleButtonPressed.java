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
import javafx.scene.control.ToggleButton;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxToggleButtonPressedTransition;

/**
 * This interaction occurs when a button is pressed.
 * @author Arnaud BLOUIN
 */
public class ToggleButtonPressed extends NodeInteraction<ToggleButton> {
	/**
	 * Creates the interaction.
	 */
	public ToggleButtonPressed() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new JfxToggleButtonPressedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ToggleButtonPressed.this.widget = this.widget;
			}
		};
	}

	@Override
	public void registerToNodes(final Collection<Node> widgets) {
		super.registerToNodes(widgets);
		if(widgets != null) {
			widgets.stream().filter(w -> w instanceof ToggleButton).forEach(w ->
					w.addEventHandler(ActionEvent.ACTION, evt -> onJfxToggleButtonPressed((ToggleButton) evt.getSource())));
		}
	}
}

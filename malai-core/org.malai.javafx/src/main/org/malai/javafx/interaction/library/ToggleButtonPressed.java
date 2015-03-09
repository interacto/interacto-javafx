package org.malai.javafx.interaction.library;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxToggleButtonPressedTransition;

/**
 * This interaction occurs when a button is pressed.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-09-18<br>
 * @author Arnaud BLOUIN
 * @since 2.0
 */
public class ToggleButtonPressed extends WidgetInteraction<ToggleButton> {
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
	public void registerToWidgets(final List<Node> widgets) {
		widgets.stream().filter(w -> w instanceof ToggleButton).forEach(w -> 
			((ToggleButton)w).addEventHandler(ActionEvent.ACTION, evt -> onJfxToggleButtonPressed((ToggleButton)evt.getSource())));
	}
}

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
package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxBoxCheckedTransition;

/**
 * This interaction occurs when a when box is selected.
 * @author Arnaud BLOUIN
 */
public class BoxChecked extends NodeInteraction<CheckBox> {
	private final EventHandler<ActionEvent> event;

	/**
	 * Creates the interaction.
	 */
	public BoxChecked() {
		super();
		initStateMachine();
		event = evt -> onJfxBoxChecked((CheckBox) evt.getSource());
	}


	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("checked");

		addState(pressed);

		new JfxBoxCheckedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				BoxChecked.this.widget = this.widget;
			}
		};
	}

	@Override
	public void onNewNodeRegistered(final Node node) {
		if(node instanceof CheckBox) {
			node.addEventHandler(ActionEvent.ACTION, event);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof CheckBox) {
			node.removeEventHandler(ActionEvent.ACTION, event);
		}
	}
}

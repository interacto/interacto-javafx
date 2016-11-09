/*
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
 */
package org.malai.javafx.interaction.library;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxBoxCheckedTransition;

/**
 * This interaction occurs when a check box is selected.<br>
 * 2014-12-11<br>
 * @author Arnaud BLOUIN
 */
public class BoxChecked extends NodeInteraction<CheckBox> {
	/**
	 * Creates the interaction.
	 */
	public BoxChecked() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("checked"); //$NON-NLS-1$

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
	public void registerToNodes(final List<Node> widgets) {
		widgets.stream().filter(w -> w instanceof CheckBox).forEach(w -> w.addEventHandler(ActionEvent.ACTION, evt -> onJfxBoxChecked((CheckBox)evt.getSource())));
	}
}

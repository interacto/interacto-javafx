/*
 * This file is part of Malai
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxTextChangedTransition;


/**
 * This interaction is performed when the text of a text field is modified.
 * @author Arnaud Blouin
 */
public class TextChanged extends NodeInteraction<TextInputControl> {
	/**
	 * Creates the interaction.
	 */
	public TextChanged() {
		super();
		initStateMachine();
	}

	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		TerminalState changed = new TerminalState("changed"); //$NON-NLS-1$

		addState(changed);

		new JfxTextChangedTransition(initState, changed) {
			@Override
			public void action() {
				super.action();
				TextChanged.this.widget = this.widget;
			}
		};
	}

	@Override
	public void registerToNodes(final Collection<Node> widgets) {
		widgets.stream().filter(w -> w instanceof TextInputControl).forEach(w ->
			w.addEventHandler(ActionEvent.ACTION, evt -> onTextChanged((TextInputControl)evt.getSource())));
	}
}

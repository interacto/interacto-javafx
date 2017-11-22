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
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxTabSelectedTransition;

/**
 * A JFX interaction for clicking on tabs.
 * @author Arnaud Blouin
 */
public class TabSelected extends NodeInteraction<TabPane> {
	/**
	 * Creates the interaction.
	 */
	public TabSelected() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new JfxTabSelectedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				TabSelected.this.widget = this.widget;
			}
		};
	}

	@Override
	public void registerToNodes(final Collection<Node> widgets) {
		widgets.stream().filter(w -> w instanceof TabPane).forEach(w -> ((TabPane) w).getSelectionModel().selectedItemProperty().
			addListener(evt -> onJfXTabSelected((TabPane) w)));
	}
}

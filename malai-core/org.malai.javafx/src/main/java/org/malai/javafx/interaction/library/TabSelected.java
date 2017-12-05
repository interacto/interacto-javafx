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

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxTabSelectedTransition;

/**
 * A JFX interaction for clicking on tabs.
 * @author Arnaud Blouin
 */
public class TabSelected extends NodeInteraction<TabPane> {
	private final ChangeListener<Tab> event;
	/**
	 * Creates the interaction.
	 */
	public TabSelected() {
		super();
		initStateMachine();
		event = (observable, oldValue, newValue) -> onJfXTabSelected(newValue.getTabPane());
	}


	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed");

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
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof TabPane) {
			((TabPane) node).getSelectionModel().selectedItemProperty().removeListener(event);
		}
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof TabPane) {
			((TabPane) node).getSelectionModel().selectedItemProperty().addListener(event);
		}
	}
}

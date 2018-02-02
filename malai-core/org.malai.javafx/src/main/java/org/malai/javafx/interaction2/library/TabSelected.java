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
package org.malai.javafx.interaction2.library;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for tabs.
 * @author Arnaud BLOUIN
 */
public class TabSelected extends JfxInteraction<TabSelectedFSM, TabPane> {
	private final ChangeListener<Tab> event;

	/**
	 * Creates the interaction.
	 */
	public TabSelected() {
		super(new TabSelectedFSM());
		fsm.buildFSM(this);
		event = (observable, oldValue, newValue) -> processEvent(new TabEvent(widget, null));
	}

	@Override
	public void processTabData(final Object tab) {
		if(tab instanceof TabPane) {
			widget = (TabPane) tab;
		}
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof TabPane) {
			((TabPane) node).getSelectionModel().selectedItemProperty().addListener(event);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof TabPane) {
			((TabPane) node).getSelectionModel().selectedItemProperty().removeListener(event);
		}
	}
}

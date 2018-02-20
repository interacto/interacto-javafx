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

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * A user interaction for tabs.
 * @author Arnaud BLOUIN
 */
public class TabSelected extends JfxInteraction<TabSelectedFSM, TabPane> {
	private final ChangeListener<Tab> event;
	private final TabSelectedFSM.TabSelectedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public TabSelected() {
		super(new TabSelectedFSM());

		handler = new TabSelectedFSM.TabSelectedFSMHandler() {
			@Override
			public void initToSelectedHandler(final TabEvent event) {
				if(event.getSource() instanceof TabPane) {
					widget = (TabPane) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				TabSelected.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
		event = (observable, oldValue, newValue) -> processEvent(new TabEvent(newValue.getTabPane(), null));
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

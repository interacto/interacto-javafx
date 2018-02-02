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

import javafx.scene.Node;
import javafx.scene.control.TabPane;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for tabs.
 * @author Arnaud BLOUIN
 */
public class TabSelected extends JfxInteraction<TabSelectedFSM, TabPane> {
	/**
	 * Creates the interaction.
	 */
	public TabSelected() {
		super(new TabSelectedFSM());
		fsm.buildFSM(this);
	}

	@Override
	public void processTabData(final Object togglebutton) {
		if(togglebutton instanceof TabPane) {
			widget = (TabPane) togglebutton;
		}
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof TabPane) {
			registerActionHandler(node);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof TabPane) {
			unregisterActionHandler(node);
		}
	}
}

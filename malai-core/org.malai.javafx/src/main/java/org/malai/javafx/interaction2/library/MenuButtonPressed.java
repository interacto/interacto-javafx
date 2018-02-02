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
import javafx.scene.control.MenuButton;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for menu buttons.
 * @author Arnaud BLOUIN
 */
public class MenuButtonPressed extends JfxInteraction<MenuButtonPressedFSM, MenuButton> {
	/**
	 * Creates the interaction.
	 */
	public MenuButtonPressed() {
		super(new MenuButtonPressedFSM());
		fsm.buildFSM(this);
	}

	@Override
	public void processMenuButtonData(final Object menubutton) {
		if(menubutton instanceof MenuButton) {
			widget = (MenuButton) menubutton;
		}
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof MenuButton) {
			registerActionHandler(node);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof MenuButton) {
			unregisterActionHandler(node);
		}
	}
}

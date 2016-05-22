package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxMenuItemPressedTransition;

import java.util.List;

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
public class MenuItemPressed extends MenuItemInteraction<MenuItem> {
	/**
	 * Creates the interaction.
	 */
	public MenuItemPressed() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new JfxMenuItemPressedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				MenuItemPressed.this.widget = this.widget;
			}
		};
	}
	
	@Override
	public void registerToMenuItems(final List<MenuItem> widgets) {
		widgets.stream().forEach(w ->
			w.addEventHandler(ActionEvent.ACTION, evt -> onJfxMenuItemPressed((MenuItem)evt.getSource())));
	}
}

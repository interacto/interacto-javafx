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
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * A user interaction for buttons.
 * @author Arnaud BLOUIN
 */
public class ButtonPressed extends JfxInteraction<ButtonPressedFSM, Button> {
	private final ButtonPressedFSM.ButtonPressedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public ButtonPressed() {
		super(new ButtonPressedFSM());

		handler = new ButtonPressedFSM.ButtonPressedFSMHandler() {
			@Override
			public void initToPressedHandler(final ActionEvent event) {
				if(event.getSource() instanceof Button) {
					widget = (Button) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				ButtonPressed.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof Button) {
			registerActionHandler(node);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof Button) {
			unregisterActionHandler(node);
		}
	}
}

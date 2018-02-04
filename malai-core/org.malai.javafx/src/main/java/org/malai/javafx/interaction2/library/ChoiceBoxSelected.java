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

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for combo boxes.
 * @author Arnaud BLOUIN
 */
public class ChoiceBoxSelected extends JfxInteraction<ChoiceBoxSelectedFSM, ChoiceBox<?>> {
	private final ChoiceBoxSelectedFSM.ChoiceBoxSelectedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public ChoiceBoxSelected() {
		super(new ChoiceBoxSelectedFSM());

		handler = new ChoiceBoxSelectedFSM.ChoiceBoxSelectedFSMHandler() {
			@Override
			public void initToSelectedHandler(final ActionEvent event) {
				if(event.getSource() instanceof ChoiceBox) {
					widget = (ChoiceBox<?>) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				ChoiceBoxSelected.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof ChoiceBox<?>) {
			registerActionHandler(node);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof ChoiceBox<?>) {
			unregisterActionHandler(node);
		}
	}
}

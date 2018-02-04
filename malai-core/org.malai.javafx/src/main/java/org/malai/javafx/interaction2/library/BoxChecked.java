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
import javafx.scene.control.CheckBox;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for checkboxes.
 * @author Arnaud BLOUIN
 */
public class BoxChecked extends JfxInteraction<BoxCheckedFSM, CheckBox> {
	private final BoxCheckedFSM.BoxCheckedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public BoxChecked() {
		super(new BoxCheckedFSM());

		handler = new BoxCheckedFSM.BoxCheckedFSMHandler() {
			@Override
			public void initToCheckedHandler(final ActionEvent event) {
				if(event.getSource() instanceof CheckBox) {
					widget = (CheckBox) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				BoxChecked.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof CheckBox) {
			registerActionHandler(node);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof CheckBox) {
			unregisterActionHandler(node);
		}
	}
}

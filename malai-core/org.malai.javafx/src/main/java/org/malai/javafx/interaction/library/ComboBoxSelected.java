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
import javafx.scene.control.ComboBox;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * A user interaction for combo boxes.
 * @author Arnaud BLOUIN
 */
public class ComboBoxSelected extends JfxInteraction<WidgetData<ComboBox<?>>, ComboBoxSelectedFSM, ComboBox<?>> {
	private final ComboBoxSelectedFSM.ComboBoxSelectedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public ComboBoxSelected() {
		super(new ComboBoxSelectedFSM());

		handler = new ComboBoxSelectedFSM.ComboBoxSelectedFSMHandler() {
			@Override
			public void initToSelectedHandler(final ActionEvent event) {
				if(event.getSource() instanceof ComboBox<?>) {
					widget = (ComboBox<?>) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				ComboBoxSelected.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof ComboBox<?>) {
			registerActionHandler(node);
		}
	}

	@Override
	public WidgetData<ComboBox<?>> getData() {
		return this;
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof ComboBox<?>) {
			unregisterActionHandler(node);
		}
	}
}

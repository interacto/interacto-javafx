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
import javafx.scene.control.Spinner;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * A user interaction for spinners.
 * @author Arnaud BLOUIN
 */
public class SpinnerChanged extends JfxInteraction<SpinnerChangedFSM, Spinner<?>> {
	final SpinnerChangedFSM.SpinnerChangedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public SpinnerChanged() {
		super(new SpinnerChangedFSM());

		handler = new SpinnerChangedFSM.SpinnerChangedFSMHandler() {
			@Override
			public void initToChangedHandler(final ActionEvent event) {
				if(event.getSource() instanceof Spinner<?>) {
					widget = (Spinner<?>) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				SpinnerChanged.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof Spinner<?>) {
			registerActionHandler(node);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof Spinner<?>) {
			unregisterActionHandler(node);
		}
	}
}

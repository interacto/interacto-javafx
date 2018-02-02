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
import javafx.scene.control.Spinner;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for spinners.
 * @author Arnaud BLOUIN
 */
public class SpinnerChanged extends JfxInteraction<SpinnerChangedFSM, Spinner<?>> {
	/**
	 * Creates the interaction.
	 */
	public SpinnerChanged() {
		super(new SpinnerChangedFSM());
		fsm.buildFSM(this);
	}

	@Override
	public void processSpinnerData(final Object spinner) {
		if(spinner instanceof Spinner<?>) {
			widget = (Spinner<?>) spinner;
		}
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

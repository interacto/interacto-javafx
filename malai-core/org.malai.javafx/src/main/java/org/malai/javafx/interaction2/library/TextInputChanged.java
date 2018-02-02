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
import javafx.scene.control.TextInputControl;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for text input controls.
 * @author Arnaud BLOUIN
 */
public class TextInputChanged extends JfxInteraction<TextInputChangedFSM, TextInputControl> {
	/**
	 * Creates the interaction.
	 */
	public TextInputChanged() {
		super(new TextInputChangedFSM());
		fsm.buildFSM(this);
	}

	@Override
	public void processTextInputData(final Object textInputCtrl) {
		if(textInputCtrl instanceof TextInputControl) {
			widget = (TextInputControl) textInputCtrl;
		}
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof TextInputControl) {
			registerActionHandler(node);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof TextInputControl) {
			unregisterActionHandler(node);
		}
	}
}

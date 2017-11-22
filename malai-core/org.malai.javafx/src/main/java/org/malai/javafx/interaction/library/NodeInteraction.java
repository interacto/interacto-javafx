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
package org.malai.javafx.interaction.library;

import javafx.scene.Node;
import org.malai.javafx.interaction.JfxInteractionImpl;

/**
 * A JavaFX interaction that uses a single widget.
 * @author Arnaud BLOUIN
 */
public abstract class NodeInteraction<T extends Node> extends JfxInteractionImpl {
	/** The widget used during the interaction. */
	protected T widget;

	/**
	 * Creates the interaction.
	 */
	public NodeInteraction() {
		super();
	}

	@Override
	public void reinit() {
		super.reinit();
		widget = null;
	}

	/**
	 * @return The widget used during the interaction.
	 */
	public T getWidget() {
		return widget;
	}
}

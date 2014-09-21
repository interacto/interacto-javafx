package org.malai.javafx.interaction;

import java.util.List;

import javafx.scene.Node;

import org.malai.interaction.InteractionImpl;

/**
 * The core class for defining interactions using the JavaFX library.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public abstract class JfxInteraction extends InteractionImpl implements JfxDefaultEventProcessor {
	/**
	 * Creates a JavaFX interaction.
	 */
	public JfxInteraction() {
		super();
	}
	
	/**
	 * Registers the given listeners to the interaction.
	 * The given widgets must be the ones used that the interaction.
	 * This operation has to be overridden by each interaction to register the widgets.
	 * @param widgets The widgets that will produce events used by the interaction.
	 */
	public void registerToWidgets(List<Node> widgets) {
		// Should be overridden.
	}
}

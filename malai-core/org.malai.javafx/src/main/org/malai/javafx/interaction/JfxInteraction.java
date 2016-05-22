package org.malai.javafx.interaction;

import javafx.scene.Node;

import java.util.List;

/**
 * The core interface for defining interactions using the JavaFX library.<br>
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
 * 2014-09-22<br>
 * @author Arnaud BLOUIN
 * @since 2.0
 */
public interface JfxInteraction extends JfxDefaultEventProcessor {
	/**
	 * Registers the given listeners to the interaction.
	 * The given widgets must be the ones used that the interaction.
	 * This operation has to be overridden by each interaction to register the widgets.
	 * @param widgets The widgets that will produce events used by the interaction. Cannot be null.
	 * @throws NullPointerException When the given <code>widgets</code> is null.
	 */
	default void registerToNodes(final List<Node> widgets) {
		// Should be overriden.
	}
}
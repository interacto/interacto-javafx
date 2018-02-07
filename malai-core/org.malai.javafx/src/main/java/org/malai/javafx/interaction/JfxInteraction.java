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
package org.malai.javafx.interaction;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Window;
import org.malai.javafx.interaction.help.HelpAnimation;

/**
 * The core interface for defining interactions using the JavaFX library.
 * @author Arnaud BLOUIN
 */
public interface JfxInteraction extends JfxDefaultEventProcessor {
	/**
	 * Registers the given registeredNodes to the interaction.
	 * This operation has to be overridden by each interaction to register the registeredNodes.
	 * @param widgets The registeredNodes that will produce events used by the interaction. Cannot be null.
	 */
	void registerToNodes(final Collection<Node> widgets);

	/**
	 * Registers the given windows to the interaction.
	 * This operation has to be overridden by each interaction to register the windows.
	 * @param windows The windows that will produce events used by the interaction. Cannot be null.
	 */
	void registerToWindows(final Collection<Window> windows);

	void registerToObservableNodeList(final ObservableList<? extends Node> nodes);

	/**
	 * @return The JFX nodes registered to the interaction. Cannot be null. Unmodifiable list.
	 */
	Set<Node> getRegisteredNodes();

	/**
	 * @return The JFX windows registered to the interaction. Cannot be null. Unmodifiable list.
	 */
	Set<Window> getRegisteredWindows();

	Optional<HelpAnimation> getHelpAnimation();
}
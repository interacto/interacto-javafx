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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.control.MenuItem;
import org.malai.javafx.interaction.JfxInteractionImpl;

/**
 * A JavaFX interaction that uses a single menu item.
 * @author Arnaud BLOUIN
 */
public abstract class MenuItemInteraction<T extends MenuItem> extends JfxInteractionImpl {
	/** The widget used during the interaction. */
	protected T widget;
	protected final ObservableSet<MenuItem> registeredItems;

	/**
	 * Creates the interaction.
	 */
	public MenuItemInteraction() {
		super();
		registeredItems = FXCollections.observableSet();

		// Listener to any changes in the list of registered nodes
		registeredItems.addListener((SetChangeListener<MenuItem>) change -> {
			if(change.wasAdded()) {
				onMenuItemRegistered(change.getElementAdded());
			}
			if(change.wasRemoved()) {
				onMenuItemUnregistered(change.getElementRemoved());
			}
		});
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


	protected void onMenuItemUnregistered(final MenuItem menuItem) {
		// Should be overriden
	}

	protected void onMenuItemRegistered(final MenuItem menuItem) {
		// Should be overriden
	}

	public final void registerToMenuItems(final List<MenuItem> widgets) {
		if(widgets != null) {
			registeredItems.addAll(widgets.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}
}

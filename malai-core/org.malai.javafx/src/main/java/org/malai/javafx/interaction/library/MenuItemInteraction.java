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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.Event;
import javafx.scene.control.MenuItem;
import org.malai.fsm.FSM;
import org.malai.interaction.InteractionData;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * A JavaFX interaction that uses a single menu item.
 * @author Arnaud BLOUIN
 */
public abstract class MenuItemInteraction<D extends InteractionData, F extends FSM<Event>, T extends MenuItem> extends JfxInteraction<D, F, T> {
	protected final ObservableSet<MenuItem> registeredItems;
	protected List<ObservableList<? extends MenuItem>> additionalMenus;

	/**
	 * Creates the interaction.
	 */
	public MenuItemInteraction(final F fsm) {
		super(fsm);
		registeredItems = FXCollections.observableSet();
		additionalMenus = null;

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

	public void registerToObservableMenuList(final ObservableList<? extends MenuItem> menuItems) {
		if(menuItems != null) {
			if(additionalMenus == null) {
				additionalMenus = new ArrayList<>();
			}
			additionalMenus.add(menuItems);

			menuItems.forEach(menuitem -> onMenuItemRegistered(menuitem));

			// Listener to any changes in the list
			menuItems.addListener((ListChangeListener<MenuItem>) change -> {
				while(change.next()) {
					if(change.wasAdded()) {
						change.getAddedSubList().forEach(elt -> onMenuItemRegistered(elt));
					}
					if(change.wasRemoved()) {
						change.getRemoved().forEach(elt -> onMenuItemUnregistered(elt));
					}
				}
			});
		}
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

	public final void unregisterFromMenuItems(final List<MenuItem> widgets) {
		if(widgets != null) {
			registeredItems.removeAll(widgets.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}
}

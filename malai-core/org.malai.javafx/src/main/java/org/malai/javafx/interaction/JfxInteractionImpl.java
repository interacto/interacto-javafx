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
package org.malai.javafx.interaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import org.malai.interaction.InitState;
import org.malai.interaction.InteractionImpl;

/**
 * The core class for defining interactions using the JavaFX library.
 * @author Arnaud BLOUIN
 */
public abstract class JfxInteractionImpl extends InteractionImpl implements JfxInteraction {
	protected final ObservableSet<Node> registeredWidgets;
	protected final ObservableSet<Window> registeredWindows;

	/**
	 * Creates a JavaFX interaction.
	 */
	public JfxInteractionImpl() {
		super();
		registeredWidgets = FXCollections.observableSet();
		registeredWindows = FXCollections.observableSet();

		// Listener to any changes in the list of registered nodes
		registeredWidgets.addListener((SetChangeListener<Node>) change -> {
			if(change.wasAdded()) {
				onNewNodeRegistered(change.getElementAdded());
			}
			if(change.wasRemoved()) {
				onNodeUnregistered(change.getElementRemoved());
			}
		});

		// Listener to any changes in the list of registered windows
		registeredWindows.addListener((SetChangeListener<Window>) change -> {
			if(change.wasAdded()) {
				onNewWindowRegistered(change.getElementAdded());
			}
			if(change.wasRemoved()) {
				onWindowUnregistered(change.getElementRemoved());
			}
		});
	}

	protected void onNodeUnregistered(final Node node) {
		// Should be overriden
	}

	protected void onWindowUnregistered(final Window window) {
		// Should be overriden
	}

	protected void onNewNodeRegistered(final Node node) {
		// Should be overriden
	}

	protected void onNewWindowRegistered(final Window window) {
		// Should be overriden
	}

	@Override
	public final void registerToNodes(final Collection<Node> widgets) {
		if(widgets != null) {
			registeredWidgets.addAll(widgets.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}

	@Override
	public final void registerToWindows(final Collection<Window> windows) {
		if(windows != null) {
			registeredWindows.addAll(windows.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}

	@Override
	public Set<Node> getRegisteredWidgets() {
		return Collections.unmodifiableSet(registeredWidgets);
	}

	@Override
	public Set<Window> getRegisteredWindows() {
		return Collections.unmodifiableSet(registeredWindows);
	}

	@Override
	protected void processEvent(final Event event) {
		if(event instanceof KeyPressEvent) {
			final KeyPressEvent key = (KeyPressEvent) event;
			onKeyPressure(key.evt, key.getIdHID());
		}
	}

	@Override
	public void onKeyPressure(final KeyEvent event, final int idHID) {
		if(!isActivated()) return;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof KeyPressureTransition).filter(tr -> {
			final KeyPressureTransition pt = (KeyPressureTransition) tr;
			pt.setEvent(event);
			pt.setHid(idHID);
			boolean found = checkTransition(tr);

			if(found && !(currentState instanceof InitState) && !stillInProgressContainsKey(idHID, event.getCode())) {
				// Adding an event 'still in process'
				addEvent(new KeyPressEvent(idHID, event));
			}
			return found;
		}).findFirst();
	}

	@Override
	public void onKeyRelease(final KeyEvent event, final int idHID) {
		if(!isActivated()) return;
		boolean found = getCurrentState().getTransitions().stream().filter(tr -> tr instanceof KeyReleaseTransition).anyMatch(tr -> {
			final KeyReleaseTransition pt = (KeyReleaseTransition) tr;
			pt.setEvent(event);
			pt.setHid(idHID);

			if(tr.isGuardRespected()) {
				// Removing from the 'still in process' list
				removeKeyEvent(idHID, event.getCode());
				return checkTransition(tr);
			}
			return false;
		});

		if(!found) {
			removeKeyEvent(idHID, event.getCode());
		}
	}

	private void addEvent(final Event event) {
		if(stillProcessingEvents == null) {
			stillProcessingEvents = new ArrayList<>(2);
		}

		synchronized(stillProcessingEvents) {
			stillProcessingEvents.add(event);
		}
	}

	/**
	 * Removes the given KeyPress event from the events 'still in process' list.
	 * @param idHID The identifier of the HID which produced the event.
	 * @param key The key code of the event to remove.
	 * @since 0.2
	 */
	private void removeKeyEvent(final int idHID, final KeyCode key) {
		if(stillProcessingEvents == null) return;

		synchronized(stillProcessingEvents) {
			boolean removed = false;
			Event event;

			for(int i = 0, size = stillProcessingEvents.size(); i < size && !removed; i++) {
				event = stillProcessingEvents.get(i);

				if(event.getIdHID() == idHID && event instanceof KeyPressEvent && ((KeyPressEvent) event).evt.getCode() == key) {
					removed = true;
					stillProcessingEvents.remove(i);
				}
			}
		}
	}


	/**
	 * Checks that the list stillProcessingEvents does not contains a keyEvent corresponding to the given one.
	 */
	private boolean stillInProgressContainsKey(final int idHID, final KeyCode key) {
		if(stillProcessingEvents == null) return false;
		synchronized(stillProcessingEvents) {
			return stillProcessingEvents.stream().
				anyMatch(evt -> idHID == evt.getIdHID() && evt instanceof KeyPressEvent && ((KeyPressEvent)evt).evt.getCode() == key);
		}
	}

	private static class KeyPressEvent extends Event {
		KeyEvent evt;

		KeyPressEvent(final int idHID, final KeyEvent evt) {
			super(idHID);
			this.evt = evt;
		}

		@Override
		public String toString() {
			return "KeyPressEvent [evt=" + evt + "]";
		}
	}
}

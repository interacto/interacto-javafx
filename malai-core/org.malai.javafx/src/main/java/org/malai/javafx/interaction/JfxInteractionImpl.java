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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Window;
import org.malai.interaction.CancellingState;
import org.malai.interaction.InitState;
import org.malai.interaction.InteractionImpl;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.stateMachine.State;

/**
 * The core class for defining interactions using the JavaFX library.
 * @author Arnaud BLOUIN
 */
public abstract class JfxInteractionImpl extends InteractionImpl implements JfxInteraction {
	protected final ObservableSet<Node> registeredNodes;
	protected final ObservableSet<Window> registeredWindows;
	protected final List<ObservableList<? extends Node>> additionalNodes;
	protected HelpAnimation helpAnimation;

	private EventHandler<MouseEvent> pressure;
	private EventHandler<MouseEvent> release;
	private EventHandler<MouseEvent> drag;
	private EventHandler<KeyEvent> keyPress;
	private EventHandler<KeyEvent> keyRelease;
	private EventHandler<MouseEvent> move;
	private EventHandler<ScrollEvent> scroll;

	/**
	 * Creates a JavaFX interaction.
	 */
	public JfxInteractionImpl() {
		super();
		registeredNodes = FXCollections.observableSet();
		registeredWindows = FXCollections.observableSet();
		additionalNodes = new ArrayList<>();

		// Listener to any changes in the list of registered nodes
		registeredNodes.addListener((SetChangeListener<Node>) change -> {
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

	@Override
	public Optional<HelpAnimation> getHelpAnimation() {
		return Optional.ofNullable(helpAnimation);
	}

	@Override
	protected void changeEventsRegistered(final State oldState) {
		// Do nothing when the interaction has only two nodes: init node and terminal node (this is a single-event interaction).
		if(oldState == currentState || states.size() == 2 || currentState instanceof InitState) {
			return;
		}

		final State nextState;
		if(currentState instanceof TerminalState || currentState instanceof CancellingState) {
			nextState = initState;
		}else {
			nextState = currentState;
		}

		final List<EventType<?>> currEvents = getEventTypesOf(nextState);
		final List<EventType<?>> events = getEventTypesOf(oldState);
		final List<EventType<?>> eventsToRemove = new ArrayList<>(events);
		final List<EventType<?>> eventsToAdd = new ArrayList<>(currEvents);

		eventsToRemove.removeAll(currEvents);
		eventsToAdd.removeAll(events);

		registeredNodes.forEach(n -> {
			eventsToRemove.forEach(type -> unregisterEventToNode(type, n));
			eventsToAdd.forEach(type -> registerEventToNode(type, n));
		});
		registeredWindows.forEach(w -> {
			eventsToRemove.forEach(type -> unregisterEventToWindow(type, w));
			eventsToAdd.forEach(type -> registerEventToWindow(type, w));
		});
		additionalNodes.forEach(nodes -> nodes.forEach(n -> {
			eventsToRemove.forEach(type -> unregisterEventToNode(type, (Node) n));
			eventsToAdd.forEach(type -> registerEventToNode(type, (Node) n));
		}));
	}

	private List<EventType<?>> getEventTypesOf(final State state) {
		return state.getTransitions().stream().map(t -> (EventType<?>) t.getEventType()).distinct().collect(Collectors.toList());
	}

	private void unregisterEventToNode(final EventType<?> eventType, final Node node) {
		if(eventType == MouseEvent.MOUSE_PRESSED) {
			node.removeEventHandler(MouseEvent.MOUSE_PRESSED, getMousePressedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_RELEASED) {
			node.removeEventHandler(MouseEvent.MOUSE_RELEASED, getMouseReleasedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED) {
			node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, getMouseDraggedHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_PRESSED) {
			node.removeEventHandler(KeyEvent.KEY_PRESSED, getKeyPressedHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_RELEASED) {
			node.removeEventHandler(KeyEvent.KEY_RELEASED, getKeyReleasedHandler());
		}
	}

	private void registerEventToNode(final EventType<?> eventType, final Node node) {
		if(eventType == MouseEvent.MOUSE_PRESSED) {
			node.addEventHandler(MouseEvent.MOUSE_PRESSED, getMousePressedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_RELEASED) {
			node.addEventHandler(MouseEvent.MOUSE_RELEASED, getMouseReleasedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED) {
			node.addEventHandler(MouseEvent.MOUSE_DRAGGED, getMouseDraggedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_MOVED) {
			node.addEventHandler(MouseEvent.MOUSE_MOVED, getMouseMovedHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			node.addEventHandler(ScrollEvent.SCROLL, getMouseScrolledHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_PRESSED) {
			node.addEventHandler(KeyEvent.KEY_PRESSED, getKeyPressedHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_RELEASED) {
			node.addEventHandler(KeyEvent.KEY_RELEASED, getKeyReleasedHandler());
		}
	}

	private void unregisterEventToWindow(final EventType<?> eventType, final Window window) {
		if(eventType == MouseEvent.MOUSE_PRESSED) {
			window.removeEventHandler(MouseEvent.MOUSE_PRESSED, getMousePressedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_RELEASED) {
			window.removeEventHandler(MouseEvent.MOUSE_RELEASED, getMouseReleasedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED) {
			window.removeEventHandler(MouseEvent.MOUSE_DRAGGED, getMouseDraggedHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_PRESSED) {
			window.removeEventHandler(KeyEvent.KEY_PRESSED, getKeyPressedHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_RELEASED) {
			window.removeEventHandler(KeyEvent.KEY_RELEASED, getKeyReleasedHandler());
		}
	}

	private void registerEventToWindow(final EventType<?> eventType, final Window window) {
		if(eventType == MouseEvent.MOUSE_PRESSED) {
			window.addEventHandler(MouseEvent.MOUSE_PRESSED, getMousePressedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_RELEASED) {
			window.addEventHandler(MouseEvent.MOUSE_RELEASED, getMouseReleasedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED) {
			window.addEventHandler(MouseEvent.MOUSE_DRAGGED, getMouseDraggedHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_MOVED) {
			window.addEventHandler(MouseEvent.MOUSE_MOVED, getMouseMovedHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			window.addEventHandler(ScrollEvent.SCROLL, getMouseScrolledHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_PRESSED) {
			window.addEventHandler(KeyEvent.KEY_PRESSED, getKeyPressedHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_RELEASED) {
			window.addEventHandler(KeyEvent.KEY_RELEASED, getKeyReleasedHandler());
		}
	}

	private EventHandler<MouseEvent> getMousePressedHandler() {
		if(pressure == null) {
			pressure = evt -> onPressure(evt, 0);
		}
		return pressure;
	}

	private EventHandler<MouseEvent> getMouseReleasedHandler() {
		if(release == null) {
			release = evt -> onRelease(evt, 0);
		}
		return release;
	}

	private EventHandler<MouseEvent> getMouseDraggedHandler() {
		if(drag == null) {
			drag = evt -> onDrag(evt, 0);
		}
		return drag;
	}

	private EventHandler<MouseEvent> getMouseMovedHandler() {
		if(move == null) {
			move = evt -> onMove(evt, 0);
		}
		return move;
	}

	private EventHandler<ScrollEvent> getMouseScrolledHandler() {
		if(scroll == null) {
			scroll = evt -> onScroll(evt, 0);
		}
		return scroll;
	}

	private EventHandler<KeyEvent> getKeyPressedHandler() {
		if(keyPress == null) {
			keyPress = evt -> onKeyPressure(evt, 0);
		}
		return keyPress;
	}

	private EventHandler<KeyEvent> getKeyReleasedHandler() {
		if(keyRelease == null) {
			keyRelease = evt -> onKeyRelease(evt, 0);
		}
		return keyRelease;
	}

	protected void onNodeUnregistered(final Node node) {
		getEventTypesOf(currentState).forEach(type -> unregisterEventToNode(type, node));
	}

	protected void onWindowUnregistered(final Window window) {
		getEventTypesOf(currentState).forEach(type -> unregisterEventToWindow(type, window));
	}

	protected void onNewNodeRegistered(final Node node) {
		getEventTypesOf(currentState).forEach(type -> registerEventToNode(type, node));
	}

	protected void onNewWindowRegistered(final Window window) {
		getEventTypesOf(currentState).forEach(type -> registerEventToWindow(type, window));
	}

	@Override
	public void registerToObservableNodeList(final ObservableList<? extends Node> nodes) {
		if(nodes != null) {
			additionalNodes.add(nodes);

			if(!nodes.isEmpty()) {
				final List<EventType<?>> events = getEventTypesOf(currentState);
				nodes.forEach(node -> events.forEach(type -> registerEventToNode(type, node)));
			}

			// Listener to any changes in the list of registered windows
			nodes.addListener((ListChangeListener<Node>) change -> {
				while(change.next()) {
					if(change.wasAdded()) {
						change.getAddedSubList().forEach(elt -> onNewNodeRegistered(elt));
					}
					if(change.wasRemoved()) {
						change.getRemoved().forEach(elt -> onNodeUnregistered(elt));
					}
				}
			});
		}
	}

	@Override
	public final void registerToNodes(final Collection<Node> widgets) {
		if(widgets != null) {
			registeredNodes.addAll(widgets.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}

	@Override
	public final void registerToWindows(final Collection<Window> windows) {
		if(windows != null) {
			registeredWindows.addAll(windows.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}

	@Override
	public Set<Node> getRegisteredNodes() {
		return Collections.unmodifiableSet(registeredNodes);
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

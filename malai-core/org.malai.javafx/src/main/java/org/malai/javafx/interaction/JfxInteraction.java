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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Window;
import org.malai.fsm.FSM;
import org.malai.fsm.OutputState;
import org.malai.interaction.InteractionData;
import org.malai.interaction.InteractionImpl;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.javafx.interaction.library.WidgetData;

public abstract class JfxInteraction<D extends InteractionData, F extends FSM<Event>, T> extends InteractionImpl<D, Event, F> implements FSMDataHandler, WidgetData<T> {
	protected final ObservableSet<Node> registeredNodes;
	protected final ObservableSet<Window> registeredWindows;
	protected final List<ObservableList<? extends Node>> additionalNodes;
	protected HelpAnimation helpAnimation;
	/** The widget used during the interaction. */
	protected T widget;

	private EventHandler<ScrollEvent> scrollHandler;
	private EventHandler<MouseEvent> mouseHandler;
	private EventHandler<KeyEvent> keyHandler;
	private EventHandler<ActionEvent> actionHandler;

	private final SetChangeListener<Node> nodesHandler = change -> {
		if(change.wasAdded()) {
			onNewNodeRegistered(change.getElementAdded());
		}
		if(change.wasRemoved()) {
			onNodeUnregistered(change.getElementRemoved());
		}
	};

	private final SetChangeListener<Window> windowsHandler = change -> {
		if(change.wasAdded()) {
			onNewWindowRegistered(change.getElementAdded());
		}
		if(change.wasRemoved()) {
			onWindowUnregistered(change.getElementRemoved());
		}
	};

	private final ListChangeListener<Node> addNodesHandler = change -> {
		while(change.next()) {
			if(change.wasAdded()) {
				change.getAddedSubList().forEach(elt -> onNewNodeRegistered(elt));
			}
			if(change.wasRemoved()) {
				change.getRemoved().forEach(elt -> onNodeUnregistered(elt));
			}
		}
	};

	protected JfxInteraction(final F fsm) {
		super(fsm);
		registeredNodes = FXCollections.observableSet();
		registeredWindows = FXCollections.observableSet();
		additionalNodes = new ArrayList<>();

		// Listener to any changes in the list of registered nodes
		registeredNodes.addListener(nodesHandler);

		// Listener to any changes in the list of registered windows
		registeredWindows.addListener(windowsHandler);
	}


	@Override
	public T getWidget() {
		return widget;
	}

	@Override
	public void reinitData() {
		widget = null;
	}

	@Override
	protected void updateEventsRegistered(final OutputState<Event> newState, final OutputState<Event> oldState) {
		// Do nothing when the interaction has only two nodes: init node and terminal node (this is a single-event interaction).
		if(newState == oldState || fsm.getStates().size() == 2) {
			return;
		}

		final List<EventType<?>> currEvents = getEventTypesOf(newState);
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
			eventsToRemove.forEach(type -> unregisterEventToNode(type, n));
			eventsToAdd.forEach(type -> registerEventToNode(type, n));
		}));
	}

	private List<EventType<?>> getEventTypesOf(final OutputState<Event> state) {
		return state.getTransitions().stream().map(t -> t.getAcceptedEvents()).flatMap(s -> s.stream()).distinct().map(o -> (EventType<?>) o).
			collect(Collectors.toList());
	}

	protected void onNodeUnregistered(final Node node) {
		getEventTypesOf(fsm.getCurrentState()).forEach(type -> unregisterEventToNode(type, node));
	}

	protected void onWindowUnregistered(final Window window) {
		getEventTypesOf(fsm.getCurrentState()).forEach(type -> unregisterEventToWindow(type, window));
	}

	protected void onNewNodeRegistered(final Node node) {
		getEventTypesOf(fsm.getCurrentState()).forEach(type -> registerEventToNode(type, node));
	}

	protected void onNewWindowRegistered(final Window window) {
		getEventTypesOf(fsm.getCurrentState()).forEach(type -> registerEventToWindow(type, window));
	}

	public void registerToObservableNodeList(final ObservableList<? extends Node> nodes) {
		if(nodes != null) {
			additionalNodes.add(nodes);

			if(!nodes.isEmpty()) {
				final List<EventType<?>> events = getEventTypesOf(fsm.getCurrentState());
				nodes.forEach(node -> events.forEach(type -> registerEventToNode(type, node)));
			}

			// Listener to any changes in the list of registered windows
			nodes.addListener(addNodesHandler);
		}
	}

	public Optional<HelpAnimation> getHelpAnimation() {
		return Optional.ofNullable(helpAnimation);
	}

	private void unregisterEventToNode(final EventType<?> eventType, final Node node) {
		if(eventType == MouseEvent.MOUSE_PRESSED) {
			node.removeEventHandler(MouseEvent.MOUSE_PRESSED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_RELEASED) {
			node.removeEventHandler(MouseEvent.MOUSE_RELEASED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED) {
			node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, getMouseHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			node.removeEventHandler(ScrollEvent.SCROLL, getScrolledHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_CLICKED) {
			node.removeEventHandler(MouseEvent.MOUSE_CLICKED, getMouseHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_PRESSED) {
			node.removeEventHandler(KeyEvent.KEY_PRESSED, getKeyHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_RELEASED) {
			node.removeEventHandler(KeyEvent.KEY_RELEASED, getKeyHandler());
		}
		if(eventType == KeyEvent.KEY_TYPED) {
			node.removeEventHandler(KeyEvent.KEY_TYPED, getKeyHandler());
		}
	}

	private void registerEventToNode(final EventType<?> eventType, final Node node) {
		if(eventType == MouseEvent.MOUSE_PRESSED) {
			node.addEventHandler(MouseEvent.MOUSE_PRESSED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_RELEASED) {
			node.addEventHandler(MouseEvent.MOUSE_RELEASED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED) {
			node.addEventHandler(MouseEvent.MOUSE_DRAGGED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_MOVED) {
			node.addEventHandler(MouseEvent.MOUSE_MOVED, getMouseHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			node.addEventHandler(ScrollEvent.SCROLL, getScrolledHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_CLICKED) {
			node.addEventHandler(MouseEvent.MOUSE_CLICKED, getMouseHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_PRESSED) {
			node.addEventHandler(KeyEvent.KEY_PRESSED, getKeyHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_RELEASED) {
			node.addEventHandler(KeyEvent.KEY_RELEASED, getKeyHandler());
		}
		if(eventType == KeyEvent.KEY_TYPED) {
			node.addEventHandler(KeyEvent.KEY_TYPED, getKeyHandler());
		}
	}

	private void unregisterEventToWindow(final EventType<?> eventType, final Window window) {
		if(eventType == MouseEvent.MOUSE_PRESSED) {
			window.removeEventHandler(MouseEvent.MOUSE_PRESSED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_RELEASED) {
			window.removeEventHandler(MouseEvent.MOUSE_RELEASED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED) {
			window.removeEventHandler(MouseEvent.MOUSE_DRAGGED, getMouseHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			window.removeEventHandler(ScrollEvent.SCROLL, getScrolledHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_CLICKED) {
			window.removeEventHandler(MouseEvent.MOUSE_CLICKED, getMouseHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_PRESSED) {
			window.removeEventHandler(KeyEvent.KEY_PRESSED, getKeyHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_RELEASED) {
			window.removeEventHandler(KeyEvent.KEY_RELEASED, getKeyHandler());
		}
		if(eventType == KeyEvent.KEY_TYPED) {
			window.removeEventHandler(KeyEvent.KEY_TYPED, getKeyHandler());
		}
	}

	private void registerEventToWindow(final EventType<?> eventType, final Window window) {
		if(eventType == MouseEvent.MOUSE_PRESSED) {
			window.addEventHandler(MouseEvent.MOUSE_PRESSED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_RELEASED) {
			window.addEventHandler(MouseEvent.MOUSE_RELEASED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED) {
			window.addEventHandler(MouseEvent.MOUSE_DRAGGED, getMouseHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_MOVED) {
			window.addEventHandler(MouseEvent.MOUSE_MOVED, getMouseHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			window.addEventHandler(ScrollEvent.SCROLL, getScrolledHandler());
			return;
		}
		if(eventType == MouseEvent.MOUSE_CLICKED) {
			window.addEventHandler(MouseEvent.MOUSE_CLICKED, getMouseHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_PRESSED) {
			window.addEventHandler(KeyEvent.KEY_PRESSED, getKeyHandler());
			return;
		}
		if(eventType == KeyEvent.KEY_RELEASED) {
			window.addEventHandler(KeyEvent.KEY_RELEASED, getKeyHandler());
		}
		if(eventType == KeyEvent.KEY_TYPED) {
			window.addEventHandler(KeyEvent.KEY_TYPED, getKeyHandler());
		}
	}

	protected void registerActionHandler(final Node node) {
		node.addEventHandler(ActionEvent.ACTION, getActionHandler());
	}

	protected void unregisterActionHandler(final Node node) {
		node.removeEventHandler(ActionEvent.ACTION, getActionHandler());
	}

	protected EventHandler<ActionEvent> getActionHandler() {
		if(actionHandler == null) {
			actionHandler = evt -> processEvent(evt);
		}
		return actionHandler;
	}

	protected EventHandler<MouseEvent> getMouseHandler() {
		if(mouseHandler == null) {
			mouseHandler = evt -> processEvent(evt);
		}
		return mouseHandler;
	}

	protected EventHandler<KeyEvent> getKeyHandler() {
		if(keyHandler == null) {
			keyHandler = evt -> processEvent(evt);
		}
		return keyHandler;
	}

	private EventHandler<ScrollEvent> getScrolledHandler() {
		if(scrollHandler == null) {
			scrollHandler = evt -> processEvent(evt);
		}
		return scrollHandler;
	}

	public void registerToNodes(final Collection<Node> widgets) {
		if(widgets != null) {
			registeredNodes.addAll(widgets.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}

	public void unregisterFromNodes(final Collection<Node> widgets) {
		if(widgets != null) {
			registeredNodes.removeAll(widgets.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}

	public void registerToWindows(final Collection<Window> windows) {
		if(windows != null) {
			registeredWindows.addAll(windows.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}

	public void unregisterFromWindows(final Collection<Window> windows) {
		if(windows != null) {
			registeredWindows.removeAll(windows.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		}
	}

	public Set<Node> getRegisteredNodes() {
		return Collections.unmodifiableSet(registeredNodes);
	}

	public Set<Window> getRegisteredWindows() {
		return Collections.unmodifiableSet(registeredWindows);
	}

	public abstract D getData();

	@Override
	protected boolean isEventsOfSameType(final Event evt1, final Event evt2) {
		return evt1 != null && evt2 != null && evt1.getEventType() == evt2.getEventType();
	}

	@Override
	protected void runInUIThread(final Runnable runnable) {
		if(Platform.isFxApplicationThread()) {
			runnable.run();
		}else {
			Platform.runLater(runnable);
		}
	}

	@Override
	public void uninstall() {
		helpAnimation = null;
		widget = null;
		scrollHandler = null;
		mouseHandler = null;
		keyHandler = null;
		actionHandler = null;
		registeredNodes.clear();
		registeredWindows.clear();
		additionalNodes.forEach(adds -> adds.addListener(addNodesHandler));
		additionalNodes.clear();
		registeredNodes.removeListener(nodesHandler);
		registeredWindows.removeListener(windowsHandler);
		super.uninstall();
	}
}

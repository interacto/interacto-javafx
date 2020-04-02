/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction;

import io.github.interacto.fsm.FSM;
import io.github.interacto.fsm.OutputState;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.interaction.InteractionImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import javafx.scene.input.TouchEvent;
import javafx.stage.Window;

/**
 * The base class for JavaFX interactions.
 * @param <D> The type of interaction data exposed by the interaction
 * @param <F> The type of the interaction FSM
 */
public abstract class JfxInteraction<D extends InteractionData, F extends FSM<Event>> extends InteractionImpl<D, Event, F> implements FSMDataHandler {
	protected final ObservableSet<Node> registeredNodes;
	protected final ObservableSet<Window> registeredWindows;
	protected final List<ObservableList<? extends Node>> additionalNodes;
	/** The interaction data */
	protected final D data;

	private EventHandler<ScrollEvent> scrollHandler;
	private EventHandler<MouseEvent> mouseHandler;
	private EventHandler<KeyEvent> keyHandler;
	private EventHandler<ActionEvent> actionHandler;
	private EventHandler<TouchEvent> touchHandler;

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
		data = createDataObject();
		registeredNodes = FXCollections.observableSet();
		registeredWindows = FXCollections.observableSet();
		additionalNodes = new ArrayList<>();

		// Listener to any changes in the list of registered nodes
		registeredNodes.addListener(nodesHandler);

		// Listener to any changes in the list of registered windows
		registeredWindows.addListener(windowsHandler);
	}

	protected abstract D createDataObject();

	@Override
	public void reinitData() {
		data.flush();
	}

	@Override
	public D getData() {
		return data;
	}

	@Override
	protected void updateEventsRegistered(final OutputState<Event> newState, final OutputState<Event> oldState) {
		// Do nothing when the interaction has only two nodes: init node and terminal node (this is a single-event interaction).
		if(newState == oldState || fsm.getStates().size() == 2) {
			return;
		}

		final List<EventType<?>> currEvents = getCurrentAcceptedEvents(newState);
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

	protected List<EventType<?>> getCurrentAcceptedEvents(final OutputState<Event> state) {
		return this.getEventTypesOf(state);
	}

	protected List<EventType<?>> getEventTypesOf(final OutputState<Event> state) {
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

	@Override
	protected void consumeEvent(final Event event) {
		event.consume();
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

	protected void unregisterEventToNode(final EventType<?> eventType, final Node node) {
		if(eventType == ActionEvent.ACTION) {
			unregisterActionHandler(node);
			return;
		}
		if(eventType.getSuperType() == MouseEvent.ANY || eventType.getSuperType().getSuperType() == MouseEvent.ANY) {
			node.removeEventHandler((EventType<MouseEvent>) eventType, getMouseHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			node.removeEventHandler(ScrollEvent.SCROLL, getScrolledHandler());
			return;
		}
		if(eventType.getSuperType() == KeyEvent.ANY) {
			node.removeEventHandler((EventType<KeyEvent>) eventType, getKeyHandler());
			return;
		}
		if(eventType.getSuperType() == TouchEvent.ANY) {
			node.removeEventHandler((EventType<TouchEvent>) eventType, getTouchHandler());
		}
	}

	protected void registerEventToNode(final EventType<?> eventType, final Node node) {
		if(eventType == ActionEvent.ACTION) {
			registerActionHandler(node);
			return;
		}
		if(eventType.getSuperType() == MouseEvent.ANY || eventType.getSuperType().getSuperType() == MouseEvent.ANY) {
			node.addEventHandler((EventType<MouseEvent>) eventType, getMouseHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			node.addEventHandler(ScrollEvent.SCROLL, getScrolledHandler());
			return;
		}
		if(eventType.getSuperType() == KeyEvent.ANY) {
			node.addEventHandler((EventType<KeyEvent>) eventType, getKeyHandler());
			return;
		}
		if(eventType.getSuperType() == TouchEvent.ANY) {
			node.addEventHandler((EventType<TouchEvent>) eventType, getTouchHandler());
		}
	}

	protected void unregisterEventToWindow(final EventType<?> eventType, final Window window) {
		if(eventType == ActionEvent.ACTION) {
			window.removeEventHandler(ActionEvent.ACTION, getActionHandler());
			return;
		}
		if(eventType.getSuperType() == MouseEvent.ANY || eventType.getSuperType().getSuperType() == MouseEvent.ANY) {
			window.removeEventHandler((EventType<MouseEvent>) eventType, getMouseHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			window.removeEventHandler(ScrollEvent.SCROLL, getScrolledHandler());
			return;
		}
		if(eventType.getSuperType() == KeyEvent.ANY) {
			window.removeEventHandler((EventType<KeyEvent>) eventType, getKeyHandler());
			return;
		}
		if(eventType.getSuperType() == TouchEvent.ANY) {
			window.removeEventHandler((EventType<TouchEvent>) eventType, getTouchHandler());
		}
	}

	protected void registerEventToWindow(final EventType<?> eventType, final Window window) {
		if(eventType == ActionEvent.ACTION) {
			window.addEventHandler(ActionEvent.ACTION, getActionHandler());
			return;
		}
		if(eventType.getSuperType() == MouseEvent.ANY || eventType.getSuperType().getSuperType() == MouseEvent.ANY) {
			window.addEventHandler((EventType<MouseEvent>) eventType, getMouseHandler());
			return;
		}
		if(eventType == ScrollEvent.SCROLL) {
			window.addEventHandler(ScrollEvent.SCROLL, getScrolledHandler());
			return;
		}
		if(eventType.getSuperType() == KeyEvent.ANY) {
			window.addEventHandler((EventType<KeyEvent>) eventType, getKeyHandler());
			return;
		}
		if(eventType.getSuperType() == TouchEvent.ANY) {
			window.addEventHandler((EventType<TouchEvent>) eventType, getTouchHandler());
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

	protected EventHandler<TouchEvent> getTouchHandler() {
		if(touchHandler == null) {
			touchHandler = evt -> processEvent(evt);
		}
		return touchHandler;
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

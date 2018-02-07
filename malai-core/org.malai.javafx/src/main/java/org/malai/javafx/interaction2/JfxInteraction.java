package org.malai.javafx.interaction2;

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
import org.malai.interaction2.Interaction;
import org.malai.javafx.interaction.help.HelpAnimation;

public abstract class JfxInteraction<F extends FSM<Event>, T> extends Interaction<Event, F> implements FSMDataHandler {
	protected final ObservableSet<Node> registeredNodes;
	protected final ObservableSet<Window> registeredWindows;
	protected final List<ObservableList<? extends Node>> additionalNodes;
	protected HelpAnimation helpAnimation;
	/** The widget used during the interaction. */
	protected T widget;

	private EventHandler<MouseEvent> pressure;
	private EventHandler<MouseEvent> release;
	private EventHandler<MouseEvent> drag;
	private EventHandler<KeyEvent> keyPress;
	private EventHandler<KeyEvent> keyRelease;
	private EventHandler<MouseEvent> move;
	private EventHandler<ScrollEvent> scroll;
	private EventHandler<ActionEvent> actionHandler;

	protected JfxInteraction(final F fsm) {
		super(fsm);
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

	/**
	 * @return The widget used during the interaction.
	 */
	public T getWidget() {
		return widget;
	}

	@Override
	public void reinitData() {
		widget = null;
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

	public Optional<HelpAnimation> getHelpAnimation() {
		return Optional.ofNullable(helpAnimation);
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

	public void onPressure(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return;
	}

	public void onRelease(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return;
	}

	public void onMove(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return;
	}

	public void onDrag(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return;
	}

	public void onKeyPressure(final KeyEvent event, final int idHID) {
		if(!isActivated()) return;
	}

	public void onKeyRelease(final KeyEvent event, final int idHID) {
		if(!isActivated()) return;
	}

//	public void onJfxButtonPressed(final Button button) {
//		if(!isActivated()) return;
//	}
//
//	public final void processEvent(final Event event) {
//		if(isActivated()) {
//			fsm.process(event);
//		}
//	}

//	public void onJfxComboBoxSelected(final ComboBox<?> cc) {
//		if(!isActivated()) return;
//	}
//
//	public void onJfxDatePicked(final DatePicker picker) {
//		if(!isActivated()) return;
//	}
//
//	public void onJfxColorPicked(final ColorPicker picker) {
//		if(!isActivated()) return;
//	}
//
//	public void onTextChanged(final TextInputControl tf) {
//		if(!isActivated()) return;
//	}
//
//	public void onJfxHyperlinkClicked(final Hyperlink link) {
//		if(!isActivated()) return;
//	}
//
//	public void onJfxMenuButtonPressed(final MenuButton button) {
//		if(!isActivated()) return;
//	}
//
//	public void onJfxMenuItemPressed(final MenuItem item) {
//		if(!isActivated()) return;
//	}
//
//	public void onJfxToggleButtonPressed(final ToggleButton button) {
//		if(!isActivated()) return;
//	}
//
//	public void onJfxSpinnerValueChanged(final Spinner<?> spinner) {
//		if(!isActivated()) return;
//	}
//
//	public void onJfXTabSelected(final TabPane tabPane) {
//		if(!isActivated()) return;
//	}

	public void onScroll(final ScrollEvent evt, final int idHID) {
		if(!isActivated()) return;
	}

//	public void onWindowClosed(final WindowEvent event) {
//		if(!isActivated()) return;
//	}
}

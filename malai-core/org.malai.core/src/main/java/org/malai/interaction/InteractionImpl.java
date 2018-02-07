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
package org.malai.interaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.malai.stateMachine.MustCancelStateMachineException;
import org.malai.stateMachine.State;
import org.malai.stateMachine.Transition;

/**
 * Base implementation of a FSM-based user interaction.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public abstract class InteractionImpl implements Interaction {
	protected Logger logger;

	/** The states that compose the finite state machine. */
	protected final Set<State> states;
	/** The initial state the starts the state machine. */
	protected final InitState initState;
	/** The current state of the state machine when the state machine is executed. */
	protected State currentState;
	/**
	 * Defines if the interaction is activated or not. If not, the interaction will not
	 * change on events.
	 */
	protected boolean activated;
	/** The handler that want to be notified when the state machine of the interaction changed. */
	protected List<InteractionHandler> handlers;
	/** The events still in process. For example when the user press key ctrl and scroll one time using the wheel of the mouse, the interaction scrolling is
	 * finished but the event keyPressed 'ctrl' is still in process. At the end of the interaction, these events are re-introduced into the
	 * state machine of the interaction for processing. */
	protected List<Event> stillProcessingEvents;
	/** The current timeout in progress. */
	protected TimeoutTransition currentTimeout;
	/** Defines the ID of last HID that has been used by the interaction. If the interaction has stopped or is aborted, the value of the attribute is -1. */
	protected int lastHIDUsed;


	/**
	 * Creates the interaction with a init state.
	 */
	public InteractionImpl() {
		this(new InitState());
	}


	/**
	 * Creates the state machine.
	 * @param initState The initial state of the state machine.
	 * @throws IllegalArgumentException If the given state is null.
	 * @since 0.1
	 */
	public InteractionImpl(final InitState initState) {
		super();

		if(initState == null) {
			throw new IllegalArgumentException();
		}

		currentTimeout = null;
		activated = true;
		states = new HashSet<>();
		initState.stateMachine = this;
		this.initState = initState;
		addState(initState);
		currentState = initState;
		lastHIDUsed = -1;
	}

	@Override
	public void log(final boolean log) {
		if(log) {
			if(logger == null) {
				logger = Logger.getLogger(getClass().getName());
			}
		}else {
			logger = null;
		}
	}

	private void setCurrentState(final State state) {
		final State oldState = currentState;
		currentState = state;
		changeEventsRegistered(oldState);
	}

	protected abstract void changeEventsRegistered(final State oldState);

	/**
	 * Initialises the interaction: creates the states and the transitions.
	 * @since 0.1
	 */
	protected abstract void initStateMachine();

	@Override
	public void setActivated(final boolean activated) {
		if(logger != null) {
			logger.log(Level.INFO, "Interaction activation: " + activated);
		}

		this.activated = activated;

		if(!activated) {
			reinit();
			clearEventsStillInProcess();
		}
	}

	@Override
	public boolean isActivated() {
		return activated;
	}

	@Override
	public State getCurrentState() {
		return currentState;

	}

	@Override
	public void reinit() {
		if(logger != null) {
			logger.log(Level.INFO, "Interaction reinit");
		}

		if(currentTimeout != null) {
			currentTimeout.stopTimeout();
		}

		currentTimeout = null;
		setCurrentState(initState);
		lastHIDUsed = -1;
	}

	@Override
	public List<InteractionHandler> getHandlers() {
		return handlers;
	}

	@Override
	public void addHandler(final InteractionHandler handler) {
		if(handler != null) {
			if(handlers == null) {
				handlers = new ArrayList<>();
			}

			handlers.add(handler);
		}
	}

	/**
	 * Notifies handler that the interaction starts.
	 */
	protected void notifyHandlersOnStart() throws MustCancelStateMachineException {
		try {
			if(handlers != null) {
				for(final InteractionHandler handler : handlers) {
					handler.interactionStarts();
				}
			}
		}catch(final MustCancelStateMachineException ex) {
			notifyHandlersOnCancel();
			throw ex;
		}
	}

	/**
	 * Notifies handler that the interaction updates.
	 */
	protected void notifyHandlersOnUpdate() throws MustCancelStateMachineException {
		try {
			if(handlers != null) {
				for(final InteractionHandler handler : handlers) {
					handler.interactionUpdates();
				}
			}
		}catch(final MustCancelStateMachineException ex) {
			notifyHandlersOnCancel();
			throw ex;
		}
	}

	/**
	 * Notifies handler that the interaction stops.
	 */
	protected void notifyHandlersOnStop() throws MustCancelStateMachineException {
		try {
			if(handlers != null) {
				for(final InteractionHandler handler : handlers) {
					handler.interactionStops();
				}
			}
		}catch(final MustCancelStateMachineException ex) {
			notifyHandlersOnCancel();
			throw ex;
		}
	}

	/**
	 * Notifies handler that the interaction is cancelled.
	 */
	protected void notifyHandlersOnCancel() {
		if(handlers != null) {
			handlers.forEach(handler -> handler.interactionCancels());
		}
	}

	@Override
	public void addState(final State state) {
		if(state != null) {
			states.add(state);
			state.setStateMachine(this);
		}
	}


	@Override
	public boolean isRunning() {
		return activated && currentState != initState;
	}


	/**
	 * Executes the given transition. Only if the state machine is activated.
	 * @param transition The transition to execute.
	 */
	protected void executeTransition(final Transition transition) {
		if(activated && transition != null) {
			if(logger != null) {
				logger.log(Level.INFO, "Transition exec: " + transition.getInputState().getName() + "->" + transition.getOutputState().getName());
			}

			try {
				transition.action();
				transition.getInputState().onOutgoing();
				setCurrentState(transition.getOutputState());
				transition.getOutputState().onIngoing();
			}catch(final MustCancelStateMachineException ex) {
				reinit();
			}
		}
	}


	/**
	 * Stops the current timeout transition.
	 * @since 0.2
	 */
	protected void stopCurrentTimeout() {
		if(currentTimeout != null) {
			if(logger != null) {
				logger.log(Level.INFO, "Timeout done");
			}

			currentTimeout.stopTimeout();
			currentTimeout = null;
		}
	}


	@Override
	public boolean checkTransition(final Transition transition) {
		final boolean ok;

		if(transition != null && states.contains(transition.getInputState()) && transition.isGuardRespected()) {
			stopCurrentTimeout();
			executeTransition(transition);
			ok = true;
		}else {
			ok = false;
		}

		if(logger != null) {
			logger.log(Level.INFO, "Check transition: " + ok);
		}

		return ok;
	}


	@Override
	public void onTimeout(final TimeoutTransition timeoutTransition) {
		if(activated && timeoutTransition != null) {
			executeTransition(timeoutTransition);
		}
	}


	/**
	 * At the end of the interaction, the events still in process must be recycled
	 * to be reused in the interaction. For instance will the KeysScrolling interaction,
	 * if key 'ctrl' is pressed and the user scrolls the key event 'ctrl' is re-introduced
	 * into the state machine of the interaction to be processed.
	 * @since 0.2
	 */
	protected void processEvents() {
		if(stillProcessingEvents != null) {
			synchronized(stillProcessingEvents) {
				Event event;
				// All the events must be processed but the list stillProcessingEvents can be modified
				// during the process. So, a clone of the list must be created.
				final List<Event> list = new ArrayList<>(stillProcessingEvents);

				// All the events must be processed.
				while(!list.isEmpty()) {
					event = list.remove(0);
					// Do not forget to remove the event from its original list.
					stillProcessingEvents.remove(0);

					if(logger != null) {
						logger.log(Level.INFO, "Recycling event: " + event);
					}

					processEvent(event);
				}
			}
		}
	}


	/**
	 * At the end of the interaction, the events still in process must be recycled
	 * to be reused in the interaction. For instance will the KeysScrolling interaction,
	 * if key 'ctrl' is pressed and the user scrolls the key event 'ctrl' is re-introduced
	 * into the state machine of the interaction to be processed.
	 * Companion operation of processEvents. Must be implemented by the different GUI libraries.
	 */
	protected abstract void processEvent(final Event event);


	@Override
	public void onTerminating() throws MustCancelStateMachineException {
		if(logger != null) {
			logger.log(Level.INFO, "Interaction ends");
		}

		notifyHandlersOnStop();
		reinit();
		processEvents();
	}


	@Override
	public void onCancelling() {
		if(logger != null) {
			logger.log(Level.INFO, "Interaction cancels");
		}

		notifyHandlersOnCancel();
		reinit();
		// When an interaction is aborted, the events in progress must not be reused.
		clearEventsStillInProcess();
	}


	@Override
	public void onStarting() throws MustCancelStateMachineException {
		if(logger != null) {
			logger.log(Level.INFO, "Interaction starts");
		}

		notifyHandlersOnStart();
		checkTimeoutTransition();
	}


	@Override
	public void onUpdating() throws MustCancelStateMachineException {
		if(logger != null) {
			logger.log(Level.INFO, "Interaction updates");
		}

		notifyHandlersOnUpdate();
		checkTimeoutTransition();
	}


	/**
	 * Checks if the current state has a timeout transition. If it is the case, the timeout transition is launched.
	 */
	protected void checkTimeoutTransition() {
		final Optional<TimeoutTransition> timeout = currentState.getTransitions().stream().filter(tr -> tr instanceof TimeoutTransition).findFirst().map(tr -> (TimeoutTransition) tr);

		if(timeout.isPresent()) {
			currentTimeout = timeout.get();
			currentTimeout.startTimeout();
		}
	}


	@Override
	public int getLastHIDUsed() {
		return lastHIDUsed;
	}

	@Override
	public void setLastHIDUsed(final int hid) {
		lastHIDUsed = hid;
	}


	@Override
	public void clearEventsStillInProcess() {
		if(stillProcessingEvents != null) {
			synchronized(stillProcessingEvents) {
				stillProcessingEvents.clear();
			}
		}
	}


	@Override
	public void clearEvents() {
		reinit();
		clearEventsStillInProcess();
	}


	/**
	 * Defines the concept of event.
	 */
	protected abstract static class Event {
		/** The identifier of the HID. */
		protected int idHID;

		/**
		 * Creates the event.
		 * @param idHID The identifier of the HID.
		 * @since 0.2
		 */
		public Event(final int idHID) {
			super();
			this.idHID = idHID;
		}

		/**
		 * @return The ID of the HID used.
		 */
		public int getIdHID() {
			return idHID;
		}
	}
}

package org.malai.interaction;

import java.util.ArrayList;
import java.util.List;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;
import org.malai.stateMachine.State;
import org.malai.stateMachine.Transition;
import org.malai.stateMachine.MustAbortStateMachineException;

/**
 * Defines an interaction as defined in the Malai model.<br>
 * An interaction is a state machine and a class.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 10/10/2009<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public abstract class InteractionImpl implements Interaction {
	/** The states that compose the finite state machine. */
	protected List<State> states;

	/** The initial state the starts the state machine. */
	protected InitState initState;

	/** The current state of the state machine when the state machine is executed. */
	protected State currentState;

	/** Defines if the interaction is activated or not. If not, the interaction will not
	 * change on events.
	 * */
	protected boolean activated;

	/** The handlers that want to be notified when the state machine of the
	 * interaction changed.
	 */
	protected List<InteractionHandler> handlers;

	/** The events still in process. For example when the user press key ctrl and scroll one
	 * time using the wheel of the mouse, the interaction scrolling is finished but the event keyPressed
	 * 'ctrl' is still in process. At the end of the interaction, these events are re-introduced into the
	 * state machine of the interaction for processing.
	 */
	protected List<Event> stillProcessingEvents;

	/** The current timeout in progress. */
	protected TimeoutTransition currentTimeout;

	/** Defines the ID of last HID that has been used by the interaction. If the interaction has stopped or is
	 * aborted, the value of the attribute is -1.
	 */
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

		if(initState==null)
			throw new IllegalArgumentException();

		currentTimeout			= null;
		activated 				= true;
		states 					= new ArrayList<>();
		initState.stateMachine 	= this;
		this.initState 			= initState;
		addState(initState);
		reinit();
	}


	/**
	 * Initialises the interaction: creates the states and the transitions.
	 * @since 0.1
	 */
	protected abstract void initStateMachine();


	@Override
	public void setActivated(final boolean activated) {
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
		if(currentTimeout!=null)
			currentTimeout.stopTimeout();

		currentTimeout	= null;
		currentState 	= initState;
		lastHIDUsed		= -1;
	}


	@Override
	public List<InteractionHandler> getHandlers() {
		return handlers;
	}


	@Override
	public void addHandler(final InteractionHandler handler) {
		if(handler!=null) {
			if(handlers==null)
				handlers = new ArrayList<>();

			handlers.add(handler);
		}
	}



	/**
	 * Notifies handlers that the interaction starts.
	 * @since 0.1
	 */
	protected void notifyHandlersOnStart() throws MustAbortStateMachineException {
		try {
			if(handlers!=null)
	    		for(final InteractionHandler handler : handlers)
	    			handler.interactionStarts(this);
		}catch(final MustAbortStateMachineException ex) {
			notifyHandlersOnAborting();
			throw ex;
		}
	}


	/**
	 * Notifies handlers that the interaction updates.
	 * @since 0.1
	 */
	protected void notifyHandlersOnUpdate() throws MustAbortStateMachineException {
		try {
			if(handlers!=null)
	    		for(final InteractionHandler handler : handlers)
	    			handler.interactionUpdates(this);
		}catch(final MustAbortStateMachineException ex) {
			notifyHandlersOnAborting();
			throw ex;
		}
	}



	/**
	 * Notifies handlers that the interaction stops.
	 * @since 0.1
	 */
	protected void notifyHandlersOnStop() throws MustAbortStateMachineException {
		try {
			if(handlers!=null)
	    		for(final InteractionHandler handler : handlers)
	    			handler.interactionStops(this);
		}catch(final MustAbortStateMachineException ex) {
			notifyHandlersOnAborting();
			throw ex;
		}
	}



	/**
	 * Notifies handlers that the interaction stops.
	 * @since 0.1
	 */
	protected void notifyHandlersOnAborting() {
		if(handlers!=null)
			for(final InteractionHandler handler : handlers)
				handler.interactionAborts(this);
	}


	/**
	 * Try to find a Pickable object at the given coordinate in the given source object.
	 * @param x The X-coordinate of the location to check.
	 * @param y The Y-coordinate of the location to check.
	 * @param source The source object in which the function will search.
	 * @return null if nothing is found. Otherwise a pickable object.
	 * @since 0.2
	 */
	public static Pickable getPickableAt(final double x, final double y, final Object source) {
		if(source==null)
			return null;

		if(source instanceof Picker)
			return ((Picker)source).getPickableAt(x, y);

		if(source instanceof Pickable) {
			final Pickable srcPickable = (Pickable) source;

			if(srcPickable.contains(x, y))
				return srcPickable;

			return srcPickable.getPicker().getPickableAt(x, y);
		}

		return null;
	}


	@Override
	public void addState(final State state) {
		if(state!=null) {
			states.add(state);
			state.setStateMachine(this);
		}
	}


	@Override
	public void linkToEventable(final Eventable eventable) {
		if(eventable!=null && eventable.hasEventManager())
			eventable.getEventManager().addHandlers(this);
	}


	@Override
	public boolean isRunning() {
		return activated && currentState!=initState;
	}


	/**
	 * Executes the given transition. Only if the state machine is activated.
	 * @param transition The transition to execute.
	 * @since 0.1
	 */
	protected void executeTransition(final Transition transition) {
		if(activated)
    		try {
    			if(transition!=null) {
    				transition.action();
    				transition.getInputState().onOutgoing();
    				currentState = transition.getOutputState();
    				transition.getOutputState().onIngoing();
    			}
    		}catch(final MustAbortStateMachineException ex) { reinit(); }
	}


	/**
	 * Stops the current timeout transition.
	 * @since 0.2
	 */
	protected void stopCurrentTimeout() {
		if(currentTimeout!=null) {
			currentTimeout.stopTimeout();
			currentTimeout = null;
		}
	}


	@Override
	public boolean checkTransition(final Transition transition) {
		final boolean ok;

		if(transition.isGuardRespected()) {
			stopCurrentTimeout();
			executeTransition(transition);
			ok = true;
		}
		else ok = false;

		return ok;
	}


	@Override
	public void onScroll(final int posX, final int posY, final int direction, final int amount,
							final int type, final int idHID, final Object src) {
		if(!activated) return ;

		boolean again = true;
		Transition transition;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof ScrollTransition) {
				final ScrollTransition st = (ScrollTransition)transition;

				st.setAmount(amount);
				st.setDirection(direction);
				st.setSource(src);
				st.setType(type);
				st.setX(posX);
				st.setY(posY);
				st.setHid(idHID);
				again = !checkTransition(transition);
			}
		}
	}


	@Override
	public void onMove(final int button, final int x, final int y, final boolean pressed, final int idHID, final Object source) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof MoveTransition) {
				final MoveTransition mt = (MoveTransition)t;

				mt.setX(x);
				mt.setY(y);
				mt.setButton(button);
				mt.setSource(source);
				mt.setPressed(pressed);
				mt.setHid(idHID);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onPressure(final int button, final int x, final int y, final int idHID, final Object source) {
		if(!activated) return ;

		boolean again = true;
		Transition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof PressureTransition) {
				final PressureTransition pt =  (PressureTransition)t;

				pt.setX(x);
				pt.setY(y);
				pt.setButton(button);
				pt.setSource(source);
				pt.setHid(idHID);
				again = !checkTransition(t);

				if(!again)
					// Adding an event 'still in process'
					addEvent(new MousePressEvent(idHID, x, y, button, source));
			}
		}
	}


	@Override
	public void onRelease(final int button, final int x, final int y, final int idHID, final Object source) {
		boolean again = true;

		if(activated) {
			Transition t;

			for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
				t = currentState.getTransition(i);

				if(t instanceof ReleaseTransition) {
					final ReleaseTransition rt = (ReleaseTransition)t;

					rt.setX(x);
					rt.setY(y);
					rt.setButton(button);
					rt.setSource(source);
					t.setHid(idHID);
					if(t.isGuardRespected()) {
						// Removing from the 'still in process' list
						removePressEvent(idHID);
						again = !checkTransition(t);
					}
				}
			}
		}

		// Removing from the 'still in process' list
		if(again)
			removePressEvent(idHID);
	}


	@Override
	public void onTimeout(final TimeoutTransition timeoutTransition) {
		if(!activated || timeoutTransition==null) return ;
		executeTransition(timeoutTransition);
	}


	/**
	 * Adds the given event to the events 'still in process' list.
	 * @param event The event to add.
	 * @since 0.2
	 */
	protected void addEvent(final Event event) {
		if(stillProcessingEvents==null)
			stillProcessingEvents = new ArrayList<>();

		stillProcessingEvents.add(event);
	}


	/**
	 * Removes the given Press event from the events 'still in process' list.
	 * @param idHID The identifier of the HID which produced the event.
	 * @since 0.2
	 */
	protected void removePressEvent(final int idHID) {
		if(stillProcessingEvents==null) return ;

		boolean removed = false;
		Event event;

		for(int i=0, size=stillProcessingEvents.size(); i<size && !removed; i++) {
			event = stillProcessingEvents.get(i);

			if(event instanceof MousePressEvent && event.idHID==idHID) {
				removed = true;
				stillProcessingEvents.remove(i);
			}
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
		if(stillProcessingEvents!=null) {
			Event event;
			// All the events must be processed but the list stillProcessingEvents can be modified
			// during the process. So, a clone of the list must be created.
			final List<Event> list = new ArrayList<>(stillProcessingEvents);

			// All the events must be processed.
			while(!list.isEmpty()) {
				event = list.remove(0);
				// Do not forget to remove the event from its original list.
				stillProcessingEvents.remove(0);
				processEvent(event);
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
	protected void processEvent(final Event event) {
		if(event instanceof MousePressEvent) {
			final MousePressEvent press = (MousePressEvent)event;
			onPressure(press.button, press.x, press.y, press.idHID, press.source);
		}
	}


	@Override
	public void onTerminating() throws MustAbortStateMachineException {
		notifyHandlersOnStop();
		reinit();
		processEvents();
	}


	@Override
	public void onAborting() {
		notifyHandlersOnAborting();
		reinit();
		// When an interaction is aborted, the events in progress must not be reused.
		clearEventsStillInProcess();
	}


	@Override
	public void onStarting() throws MustAbortStateMachineException {
		notifyHandlersOnStart();
		checkTimeoutTransition();
	}


	@Override
	public void onUpdating() throws MustAbortStateMachineException {
		notifyHandlersOnUpdate();
		checkTimeoutTransition();
	}


	/**
	 * Checks if the current state has a timeout transition. If it is the case,
	 * the timeout transition is launched.
	 * @since 0.2
	 */
	protected void checkTimeoutTransition() {
		boolean again = true;
		Transition transition;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof TimeoutTransition) {
				currentTimeout = (TimeoutTransition)transition;
				again = false;
				currentTimeout.startTimeout();
			}
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
		if(stillProcessingEvents!=null)
			stillProcessingEvents.clear();
	}


	@Override
	public void clearEvents() {
		reinit();
		clearEventsStillInProcess();
	}


	/**
	 * This class defines an event corresponding to the pressure of a button of a mouse.
	 */
	protected static class MousePressEvent extends Event {
		/** The x coordinate of the pressure. */
		protected int x;

		/** The y coordinate of the pressure. */
		protected int y;

		/** The object targeted during the pressure. */
		protected Object source;

		/** The button used to perform the pressure. */
		protected int button;

		/**
		 * Creates the event.
		 * @param idHID The identifier of the HID.
		 * @param x The x coordinate of the pressure.
		 * @param y The y coordinate of the pressure.
		 * @param button The button used to perform the pressure.
		 * @param source The object targeted during the pressure.
		 * @since 0.2
		 */
		public MousePressEvent(final int idHID, final int x, final int y, final int button, final Object source) {
			super(idHID);
			this.x = x;
			this.y = y;
			this.button = button;
			this.source = source;
		}
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
		public int getIdHID() { return idHID; }
	}
}

package org.malai.fsm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FSM<E> {
	protected Logger logger;
	protected boolean inner;
	protected State<E> startingState;
	protected final InitState<E> initState;
	protected OutputState<E> currentState;
	/** The states that compose the finite state machine. */
	protected final Set<State<E>> states;
	/** The handler that want to be notified when the state machine of the interaction changed. */
	protected FSMHandler handler;
	/**
	 * The events still in process. For example when the user press key ctrl and scroll one time using the wheel of the mouse, the interaction scrolling is
	 * finished but the event keyPressed 'ctrl' is still in process. At the end of the interaction, these events are re-introduced into the
	 * state machine of the interaction for processing.
	 */
	protected List<E> eventsToProcess;
	/** The current timeout in progress. */
	protected TimeoutTransition<E> currentTimeout;
	protected FSM<E> currentSubFSM;


	public FSM() {
		super();
		initState = new InitState<>(this, "init");
		states = new HashSet<>();
		states.add(initState);
		startingState = initState;
		currentState = initState;
		inner = false;
	}

	public OutputState<E> getCurrentState() {
		return currentState;
	}

	public void setInner(final boolean inner) {
		this.inner = inner;
	}

	public void process(final E event) {
		if(event == null) {
			return;
		}
		if(currentSubFSM != null) {
			currentSubFSM.process(event);
		}else {
			currentState.process(event);
		}
	}

	protected void enterStdState(final StdState<E> state) throws CancelFSMException {
		currentState = state;
		checkTimeoutTransition();
		onUpdating();
	}

	/**
	 * At the end of the FSM execution, the events still (eg keyPress) in process must be recycled to be reused in the FSM.
	 */
	protected void processRemainingEvents() {
		if(eventsToProcess != null) {
			synchronized(eventsToProcess) {
				// All the events must be processed but the list stillProcessingEvents can be modified
				// during the process. So, a clone of the list must be created.
				final List<E> list = new ArrayList<>(eventsToProcess);

				// All the events must be processed.
				while(!list.isEmpty()) {
					final E event = list.remove(0);
					// Do not forget to remove the event from its original list.
					eventsToProcess.remove(0);

					if(logger != null) {
						logger.log(Level.INFO, "Recycling event: " + event);
					}

					process(event);
				}
			}
		}
	}

	protected void addRemaningEventsToProcess(final E event) {
		if(event != null) {
			if(eventsToProcess == null) {
				eventsToProcess = new ArrayList<>();
			}
			synchronized(eventsToProcess) {
				eventsToProcess.add(event);
			}
		}
	}

	/**
	 * Terminates the state machine.
	 */
	protected void onTerminating() throws CancelFSMException {
		if(logger != null) {
			logger.log(Level.INFO, "FSM ended");
		}

		notifyHandlerOnStop();
		reinit();
		processRemainingEvents();
	}

	/** Cancels the state machine. */
	protected void onCancelling() {
		if(logger != null) {
			logger.log(Level.INFO, "FSM cancelled");
		}

		notifyHandlerOnCancel();
		// When an interaction is aborted, the events in progress must not be reused.
		fullReinit();
	}

	/**
	 * Starts the state machine.
	 */
	public void onStarting() throws CancelFSMException {
		if(logger != null) {
			logger.log(Level.INFO, "FSM started");
		}

		notifyHandlerOnStart();
	}

	/**
	 * Updates the state machine.
	 */
	public void onUpdating() throws CancelFSMException {
		if(logger != null) {
			logger.log(Level.INFO, "FSM updated");
		}

		notifyHandlerOnUpdate();
	}

	/**
	 * Adds a state to the state machine.
	 * @param state The state to add. Must not be null.
	 */
	protected void addState(final InputState<E> state) {
		if(state != null) {
			states.add(state);
		}
	}

	public void log(final boolean log) {
		if(log) {
			if(logger == null) {
				logger = Logger.getLogger(getClass().getName());
			}
		}else {
			logger = null;
		}
	}

	public void reinit() {
		if(logger != null) {
			logger.log(Level.INFO, "FSM reinitialised");
		}

		if(currentTimeout != null) {
			currentTimeout.stopTimeout();
		}

		currentState = initState;
		currentTimeout = null;

		if(currentSubFSM != null) {
			currentSubFSM.reinit();
		}
	}

	public void fullReinit() {
		if(eventsToProcess != null) {
			synchronized(eventsToProcess) {
				eventsToProcess.clear();
			}
		}
		reinit();

		if(currentSubFSM != null) {
			currentSubFSM.fullReinit();
		}
	}

	protected void onTimeout() {
		if(currentTimeout != null) {
			if(logger != null) {
				logger.log(Level.INFO, "Timeout");
			}

			try {
				currentTimeout.execute(null).ifPresent(state -> {
					currentState = (OutputState<E>) state;
					checkTimeoutTransition();
				});
			}catch(final CancelFSMException ignored) {
				// Already processed
			}
		}
	}

	/**
	 * Stops the current timeout transition.
	 */
	protected void stopCurrentTimeout() {
		if(currentTimeout != null) {
			if(logger != null) {
				logger.log(Level.INFO, "Timeout stopped");
			}

			currentTimeout.stopTimeout();
			currentTimeout = null;
		}
	}

	/**
	 * Checks whether the current state has a timeout transition.
	 * If it is the case, the timeout transition is launched.
	 */
	protected void checkTimeoutTransition() {
		currentState.getTransitions().stream().filter(tr -> tr instanceof TimeoutTransition).findFirst().map(tr -> (TimeoutTransition<E>) tr).ifPresent(tr -> {
			if(logger != null) {
				logger.log(Level.INFO, "Timeout starting");
			}

			currentTimeout = tr;
			currentTimeout.startTimeout();
		});
	}

	public void setHandler(final FSMHandler handler) {
		this.handler = handler;
	}

	/**
	 * Notifies handler that the interaction starts.
	 */
	protected void notifyHandlerOnStart() throws CancelFSMException {
		try {
			if(handler != null) {
				handler.fsmStarts();
			}
		}catch(final CancelFSMException ex) {
			onCancelling();
			throw ex;
		}
	}

	/**
	 * Notifies handler that the interaction updates.
	 */
	protected void notifyHandlerOnUpdate() throws CancelFSMException {
		try {
			if(handler != null) {
				handler.fsmUpdates();
			}
		}catch(final CancelFSMException ex) {
			onCancelling();
			throw ex;
		}
	}

	/**
	 * Notifies handler that the interaction stops.
	 */
	protected void notifyHandlerOnStop() throws CancelFSMException {
		try {
			if(handler != null) {
				handler.fsmStops();
			}
		}catch(final CancelFSMException ex) {
			onCancelling();
			throw ex;
		}
	}

	/**
	 * Notifies handler that the interaction is cancelled.
	 */
	protected void notifyHandlerOnCancel() {
		if(handler != null) {
			handler.fsmCancels();
		}
	}
}

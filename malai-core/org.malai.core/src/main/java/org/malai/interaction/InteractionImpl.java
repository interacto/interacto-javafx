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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.malai.fsm.FSM;
import org.malai.fsm.InitState;
import org.malai.fsm.OutputState;

public abstract class InteractionImpl<D extends InteractionData, E, F extends FSM<E>> {
	protected final F fsm;
	/** Defines whether the interaction is activated. If not, the interaction will not change on events. */
	protected boolean activated;
	protected Logger logger;
	protected long throttleTimeout;
	protected final AtomicLong throttleCounter;
	protected E currentThrottledEvent;
	/** The current throttle thread in progress. */
	private Future<?> currThrottleTimeoutFuture;
	private ExecutorService executor;

	protected InteractionImpl(final F fsm) {
		super();

		if(fsm == null) {
			throw new IllegalArgumentException("null fsm");
		}

		executor = null;
		currThrottleTimeoutFuture = null;
		throttleTimeout = 0L;
		this.fsm = fsm;
		fsm.currentStateProp().obs((oldValue, newValue) -> updateEventsRegistered(newValue, oldValue));
		activated = true;
		throttleCounter = new AtomicLong();
		currentThrottledEvent = null;
	}

	public abstract D getData();

	public void setThrottleTimeout(final long timeout) {
		throttleTimeout = timeout;
	}

	protected abstract void updateEventsRegistered(final OutputState<E> newState, final OutputState<E> oldState);

	public boolean isRunning() {
		return activated && !(fsm.getCurrentState() instanceof InitState<?>);
	}

	public void fullReinit() {
		fsm.fullReinit();
	}

	private void directEventProcess(final E event) {
		fsm.process(event);
	}

	/**
	 * Defines whether the two given events are of the same type.
	 * For example, whether they are both mouse move events.
	 * This check is platform specific.
	 * @param evt1 The first event to check.
	 * @param evt2 The second event to check.
	 * @return True: the two events are of the same type.
	 */
	protected abstract boolean isEventsOfSameType(final E evt1, final E evt2);

	/**
	 * Throttling: sleeping between events of the same type.
	 */
	private void createThrottleTimeout() {
		if(executor == null) {
			executor = Executors.newWorkStealingPool();
		}

		// Cancelling the current task.
		if(currThrottleTimeoutFuture != null && !currThrottleTimeoutFuture.isDone()) {
			currThrottleTimeoutFuture.cancel(true);
		}

		// Executing a new timeout for the throttling operation.
		currThrottleTimeoutFuture = executor.submit(() -> {
			try {
				Thread.sleep(throttleTimeout);
				E evt = null;
				synchronized(throttleCounter) {
					if(throttleCounter.get() > 0L && currentThrottledEvent != null) {
						evt = currentThrottledEvent;
					}
					throttleCounter.set(0L);
					currentThrottledEvent = null;
				}
				if(evt != null) {
					final E evtToProcess = evt;
					runInUIThread(() -> directEventProcess(evtToProcess));
				}
			}catch(final InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		});
	}

	/**
	 * Runs the given command in the UI thread.
	 * This is necessary since some created threads (e.g. throttling, timeout transition)
	 * exit the UI thread but may require some job to be executed in the UI thread.
	 * @param cmd The job to execute in the UI thread.
	 */
	protected abstract void runInUIThread(final Runnable cmd);

	/**
	 * Throttling processing: the given event is checked to be throttled or not.
	 * @param event The event to check.
	 * @return True: the event must be processed by the interaction.
	 */
	private boolean checkThrottlingEvent(final E event) {
		synchronized(throttleCounter) {
			if(currentThrottledEvent == null || !isEventsOfSameType(currentThrottledEvent, event)) {
				if(currentThrottledEvent != null && throttleCounter.get() > 0L) {
					directEventProcess(event);
				}
				throttleCounter.set(0L);
				currentThrottledEvent = event;
				createThrottleTimeout();
				return true;
			}else {
				// The previous thottled event is ignored
				throttleCounter.incrementAndGet();
				currentThrottledEvent = event;
				return false;
			}
		}
	}

	public void processEvent(final E event) {
		if(isActivated()) {
			if(throttleTimeout <= 0L || checkThrottlingEvent(event)) {
				directEventProcess(event);
			}
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

		fsm.log(log);
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(final boolean activated) {
		if(logger != null) {
			logger.log(Level.INFO, "Interaction activation: " + activated);
		}

		this.activated = activated;

		if(!activated) {
			fsm.fullReinit();
		}
	}

	public F getFsm() {
		return fsm;
	}

	protected void reinit() {
		fsm.reinit();
		reinitData();
	}

	protected abstract void reinitData();

	public void uninstall() {
		setActivated(false);
		logger = null;
		if(executor != null) {
			executor.shutdown();
		}
	}
}

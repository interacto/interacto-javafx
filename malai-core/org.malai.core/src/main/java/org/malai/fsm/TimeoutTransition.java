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
package org.malai.fsm;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongSupplier;

public class TimeoutTransition<E> extends Transition<E> {
	/** The timeoutDuration in ms. */
	private final LongSupplier timeoutDuration;

	/** The current thread in progress. */
	private Thread timeoutThread;

	private boolean timeouted;

	public TimeoutTransition(final OutputState<E> srcState, final InputState<E> tgtState, final LongSupplier timeout) {
		super(srcState, tgtState);

		if(timeout == null) {
			throw new IllegalArgumentException();
		}

		timeoutDuration = timeout;
		timeouted = false;
	}

	/**
	 * Launches the timer.
	 */
	public void startTimeout() {
		if(timeoutThread == null) {
			timeoutThread = new Thread(() -> {
				final long time = TimeoutTransition.this.timeoutDuration.getAsLong();

				if(time > 0L) {
					try {
						// Sleeping the thread.
						Thread.sleep(time);
						// There is a timeoutDuration and the interaction must be notified of that.
						// Notifying the interaction of the timeoutDuration.
						timeouted = true;
						TimeoutTransition.this.src.getFSM().onTimeout();
					}catch(final InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			});
			timeoutThread.start();
		}
	}

	/**
	 * Stops the timer.
	 */
	public void stopTimeout() {
		if(timeoutThread != null) {
			timeoutThread.interrupt();
			timeoutThread = null;
		}
	}

	@Override
	protected boolean accept(final E event) {
		return timeouted;
	}

	@Override
	protected boolean isGuardOK(final E event) {
		return timeouted;
	}

	@Override
	public Optional<InputState<E>> execute(final E event) throws CancelFSMException {
		try {
			if(accept(event) && isGuardOK(event)) {
				src.exit();
				action(event);
				tgt.enter();
				timeouted = false;
				return Optional.of(tgt);
			}
			return Optional.empty();
		}catch(final CancelFSMException ex) {
			timeouted = false;
			throw ex;
		}
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.emptySet();
	}
}

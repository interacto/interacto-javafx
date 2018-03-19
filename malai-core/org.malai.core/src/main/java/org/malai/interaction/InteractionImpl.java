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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.malai.fsm.FSM;
import org.malai.fsm.InitState;
import org.malai.fsm.OutputState;

public abstract class InteractionImpl<E, F extends FSM<E>> {
	protected Logger logger;

	protected final F fsm;
	/**
	 * Defines if the interaction is activated or not. If not, the interaction will not
	 * change on events.
	 */
	protected boolean activated;

	protected InteractionImpl(final F fsm) {
		super();

		if(fsm == null) {
			throw new IllegalArgumentException("null fsm");
		}

		this.fsm = fsm;
		fsm.currentStateProp().obs((oldValue, newValue) -> updateEventsRegistered(newValue, oldValue));
		activated = true;
	}

	protected abstract void updateEventsRegistered(final OutputState<E> newState, final OutputState<E> oldState);

	public boolean isRunning() {
		return activated && !(fsm.getCurrentState() instanceof InitState<?>);
	}

	public void fullReinit() {
		fsm.fullReinit();
	}

	public void processEvent(final E event) {
		if(isActivated()) {
			fsm.process(event);
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
}

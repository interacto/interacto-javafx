/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.action;

import java.util.Collections;
import java.util.List;

/**
 * Base implementation of the Action interface.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class ActionImpl implements Action {
	/** The state of the action. */
	protected ActionStatus status;

	/**
	 * The default constructor.
	 * Initialises the current status to created.
	 */
	public ActionImpl() {
		super();
		status = ActionStatus.CREATED;
	}


	@Override
	public void flush() {
		status = ActionStatus.FLUSHED;
	}


	@Override
	public boolean doIt() {
		final boolean ok;

		if((status == ActionStatus.CREATED || status == ActionStatus.EXECUTED) && canDo()) {
			ok = true;
			doActionBody();
			status = ActionStatus.EXECUTED;
			ActionsRegistry.INSTANCE.onActionExecuted(this);
		}else {
			ok = false;
		}

		return ok;
	}


	/**
	 * This method contains the statements to execute the action.
	 * This method is automatically called by DoIt and must not be called explicitly.
	 * @since 0.1
	 */
	protected abstract void doActionBody();

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}

	@Override
	public boolean hadEffect() {
		return isDone();
	}


	@Override
	public boolean unregisteredBy(final Action action) {
		return false;
	}


	@Override
	public void done() {
		if(status == ActionStatus.CREATED || status == ActionStatus.EXECUTED) {
			status = ActionStatus.DONE;
			ActionsRegistry.INSTANCE.onActionDone(this);
		}
	}


	@Override
	public boolean isDone() {
		return status == ActionStatus.DONE;
	}


	@Override
	public String toString() {
		return getClass().getSimpleName();
	}


	@Override
	public void abort() {
		status = ActionStatus.ABORTED;
	}


	@Override
	public ActionStatus getStatus() {
		return status;
	}


	@Override
	public List<Action> followingActions() {
		return Collections.emptyList();
	}
}

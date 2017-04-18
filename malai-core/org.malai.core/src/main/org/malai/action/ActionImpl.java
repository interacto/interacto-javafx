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
 * An action is based on the command design pattern: it is an object that encapsulates information to execute a task and to undo/redo
 * it if necessary.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class ActionImpl implements Action {

	/** Provides the state of the action. */
	protected ActionStatus status;


	/**
	 * The default constructor.
	 */
	public ActionImpl() {
		super();
		status = ActionStatus.CREATED;
	}


	/**
	 * When an action is no more useful it can be flushes to release the used data.
	 * Should be overridden.
	 * @since 0.2
	 */
	@Override
	public void flush() {
		status = ActionStatus.FLUSHED;
	}


	/**
	 * Executes the action. Should be overridden by sub-class to define stuffs to execute.
	 * If the status of the action differs than CREATED or EXECUTED and if the action cannot be done (canDo),
	 * the action is not executed.
	 * @since 0.1
	 * @return True if the execution is successful. False otherwise.
	 */
	@Override
	public boolean doIt() {
		final boolean ok;

		if((status==ActionStatus.CREATED || status==ActionStatus.EXECUTED) && canDo()) {
			ok = true;
			doActionBody();
			status = ActionStatus.EXECUTED;
			ActionsRegistry.INSTANCE.onActionExecuted(this);
		}
		else ok = false;

		return ok;
	}


	/**
	 * This method contains the core code to execute when the action is executed.
	 * @since 0.1
	 */
	protected abstract void doActionBody();


	/**
	 * @return True if the execution of the action had effects on the target.
	 * By default this function return the result of isDone. Should be overridden.
	 * @since 0.1
	 */
	@Override
	public boolean hadEffect() {
		return isDone();
	}



	/**
	 * Defines if the given action can cancel the calling action. Should be overridden.
	 * By default, false is returned.
	 * @param IAction The action to test.
	 * @return True if the given action cancels the calling action. By default, false is returned.
	 * @since 0.1
	 */
	@Override
	public boolean cancelledBy(final Action IAction) {
		return false;
	}



	/**
	 * Sets the action to "done".
	 * @since 0.1
	 */
	@Override
	public void done() {
		if(status==ActionStatus.CREATED || status==ActionStatus.EXECUTED) {
			status = ActionStatus.DONE;
			ActionsRegistry.INSTANCE.onActionDone(this);
		}
	}



	/**
	 * @return True if the action is done.
	 * @since 0.1
	 */
	@Override
	public boolean isDone() {
		return status==ActionStatus.DONE;
	}



	@Override
	public String toString() {
		return getClass().getSimpleName();
	}



	/**
	 * Aborts the action.
	 * @since 0.1
	 */
	@Override
	public void abort() {
		status = ActionStatus.ABORTED;
	}


	/**
	 * Provides the state of the action.
	 * @return The state of the action.
	 * @since 0.2
	 */
	@Override
	public ActionStatus getStatus() {
		return status;
	}

	/**
	 * The executing of an action may provoke the execution of other actions.<br>
	 * For instance with a drawing editor, one may want that after having pasting
	 * shapes, the new shapes must be selected.
	 * Thus, the action PasteShapes will be followed by an action SelectShapes.<br>
	 * This is the goal of the operation.<br>
	 * This operation creates an initialises the action that will be executed after
	 * each final execution of the current action.
	 * @return A list of actions that must be executed afterward. Cannot be null.
	 */
	@Override
	public List<Action> followingActions() {
		return Collections.emptyList();
	}
}

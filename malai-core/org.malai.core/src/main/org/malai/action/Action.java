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

import java.util.List;

/**
 * An action is based on the command design pattern: it is an object that
 * encapsulates information to execute a task and to undo/redo it if
 * necessary.
 * @author Arnaud Blouin
 */
public interface Action {
	void flush();

	/**
	 * Specifies if the action must be saved in the action register. For instance,
	 * some actions, such as a scroll of the scroll bars, should not be saved nor
	 * put in the undo/redo manager. Thus, they must not be registrable.
	 * @return True: the action is registrable.
	 * @since 0.1
	 */
	boolean isRegisterable();

	boolean doIt();

	/**
	 * @return True if the action can be executed.
	 * @since 0.1
	 */
	boolean canDo();

	boolean hadEffect();

	boolean cancelledBy(Action IAction);

	void done();

	boolean isDone();

	void abort();

	ActionStatus getStatus();

	List<Action> followingActions();

	/** Defines the different state of the action. @since 0.2 */
	public static enum ActionStatus {
		/** When the action is created but not executed yet. */
		CREATED,
		/** When the action has been created and executed one time. */
		EXECUTED,
		/** When the action has been aborted. */
		ABORTED,
		/** When the action has been defined as done. */
		DONE,
		/** The the action has been flushed. In this case, the action cannot be used anymore. */
		FLUSHED
		}
}

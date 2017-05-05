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
 * An action is produced and executed in reaction of a user interaction.
 * It follows the command design pattern.
 * It contains statements to execute to perform the action.
 * The interface Undoable can be used to add undo/redo features to an action.
 * @author Arnaud Blouin
 */
public interface Action {
	/**
	 * Flushes the action.
	 * Can be useful to close streams, free objects, etc.
	 * An action should flushed manually only when it is not managed by the action registry of the application.
	 * When an action is gathered and managed by an action registry, it is automatically flushed when the
	 * action registry removes the action.
	 */
	void flush();

	/**
	 * Specifies whether the action must be saved in the action register. For instance,
	 * some actions, such as a scroll of the scroll bars, should not be saved or
	 * put in the undo/redo manager. Thus, they must not be registrable.
	 * @return True: the action is registrable.
	 * @since 0.1
	 */
	boolean isRegisterable();

	/**
	 * This method manages the execution of the action.
	 * @return True: the execution of the action is OK.
	 */
	boolean doIt();

	/**
	 * Checks whether the action can be executed.
	 * @return True if the action can be executed.
	 * @since 0.1
	 */
	boolean canDo();

	/**
	 * State whether the execution of this action has effects on the system.
	 * @return True: the action has effects on the system.
	 */
	boolean hadEffect();


	/**
	 * Checks whether the current action can be cancelled by the given one.
	 * @param action The action to check whether it can cancel the current action.
	 * @return True: The given action can cancel the current action.
	 */
	boolean unregisteredBy(final Action action);

	/**
	 * Marks the action as "done" and sends it to the action registry.
	 * @since 0.1
	 */
	void done();

	/**
	 * To know whether the action has been marked as 'done'.
	 * @return True: the action has been marked as 'done'.
	 */
	boolean isDone();

	/**
	 * Marks the action has aborted.
	 */
	void abort();

	/**
	 * Provides the status of the action.
	 * @return The status of the action.
	 * @since 0.2
	 */
	ActionStatus getStatus();

	/**
	 * The execution of the action may provoke the execution of other actions.
	 * For instance with a drawing editor, one may want that after having pasted shapes, the new shapes must be selected.
	 * So, the action PasteShapes will be followed by an action SelectShapes.
	 * This is the goal of the operation.
	 * This operation creates and initialises the action that will be executed after each final execution of the current action.
	 * @return A list of actions that must be executed afterward. Cannot be null.
	 */
	List<Action> followingActions();

	/**
	 * Defines the different states of the action.
	 * @since 0.2
	 */
	enum ActionStatus {
		/** When the action is created but not executed yet. */
		CREATED,
		/** When the action has been created and executed one time. */
		EXECUTED,
		/** When the action has been aborted. */
		ABORTED,
		/** When the action has been marked as done. */
		DONE,
		/** The action has been flushed. In this case, the action must not be used anymore. */
		FLUSHED
	}
}

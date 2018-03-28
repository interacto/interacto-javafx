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
package org.malai.command;

import java.util.List;

/**
 * A command is produced and executed in reaction of a user interaction.
 * It follows the command design pattern.
 * It contains statements to execute to perform the command.
 * The interface Undoable can be used to add undo/redo features to a command.
 * @author Arnaud Blouin
 */
public interface Command {
	/**
	 * Flushes the command.
	 * Can be useful to close streams, free objects, etc.
	 * A command should flushed manually only when it is not managed by the cmd registry of the application.
	 * When a command is gathered and managed by a command registry, it is automatically flushed when the
	 * command registry removes the cmd.
	 */
	void flush();

	/**
	 * Specifies whether the command must be saved in the cmd register. For instance,
	 * some commands, such as a scroll, should not be saved or put in the undo/redo manager. Such commands should not be registrable.
	 * @return The registration policy.
	 */
	RegistrationPolicy getRegistrationPolicy();

	/**
	 * This method manages the execution of the command.
	 * @return True: the execution of the command is OK.
	 */
	boolean doIt();

	/**
	 * Checks whether the command can be executed.
	 * @return True if the command can be executed.
	 * @since 0.1
	 */
	boolean canDo();

	/**
	 * State whether the execution of this command has effects on the system.
	 * @return True: the command has effects on the system.
	 */
	boolean hadEffect();


	/**
	 * Checks whether the current command can be cancelled by the given one.
	 * @param cmd The command to check whether it can cancel the current cmd.
	 * @return True: The given command can cancel the current cmd.
	 */
	boolean unregisteredBy(final Command cmd);

	/**
	 * Marks the command as "done" and sends it to the cmd registry.
	 * @since 0.1
	 */
	void done();

	/**
	 * To know whether the command has been marked as 'done'.
	 * @return True: the command has been marked as 'done'.
	 */
	boolean isDone();

	/**
	 * Marks the command has aborted.
	 */
	void cancel();

	/**
	 * Provides the status of the command.
	 * @return The status of the command.
	 * @since 0.2
	 */
	CmdStatus getStatus();

	/**
	 * The execution of the command may provoke the execution of other commands.
	 * For instance with a drawing editor, one may want that after having pasted shapes, the new shapes must be selected.
	 * So, the command PasteShapes will be followed by a command SelectShapes.
	 * This is the goal of the operation.
	 * This operation creates and initialises the command that will be executed after each final execution of the current cmd.
	 * @return A list of commands that must be executed afterward. Cannot be null.
	 */
	List<Command> followingCmds();

	/**
	 * Defines the registration policy of the command.
	 */
	enum RegistrationPolicy {
		/** The command is never registered. */
		NONE,
		/** The command is registered in the cmd register. The cmd is not flushed when the registry wants to free some commands. */
		UNLIMITED,
		/** The command is registered in the cmd register. The cmd can be flushed by the registry. */
		LIMITED
	}

	/**
	 * Defines the different states of the command.
	 * @since 0.2
	 */
	enum CmdStatus {
		/** When the command is created but not executed yet. */
		CREATED,
		/** When the command has been created and executed one time. */
		EXECUTED,
		/** When the command has been cancelled. */
		CANCELLED,
		/** When the command has been marked as done. */
		DONE,
		/** The command has been flushed. In this case, the cmd must not be used anymore. */
		FLUSHED
	}
}

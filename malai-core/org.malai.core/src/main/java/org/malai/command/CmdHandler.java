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

import org.malai.undo.UndoHandler;

/**
 * This interface allows to create a bridge between a command and an object that want to be aware about events on commands
 * (such as creation or deletion of a command).
 * @author Arnaud Blouin
 */
public interface CmdHandler extends UndoHandler {
	/**
	 * Notifies the handler when the given command is added to the registry.
	 * @param cmd The added command.
	 */
	void onCmdAdded(final Command cmd);

	/**
	 * Notifies the handler when the given command is cancelled.
	 * @param cmd The cancelled command.
	 */
	void onCmdCancelled(final Command cmd);

	/**
	 * Notifies the handler when the given command is executed.
	 * @param cmd The executed command.
	 */
	void onCmdExecuted(final Command cmd);

	/**
	 * Notifies the handler when the given command is done.
	 * @param cmd The command that ends.
	 */
	void onCmdDone(final Command cmd);
}

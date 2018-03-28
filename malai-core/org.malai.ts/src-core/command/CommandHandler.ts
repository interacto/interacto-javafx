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

import {UndoHandler} from "../undo/UndoHandler";
import {Command} from "./Command";

/**
 * This interface allows to create a bridge between a command and an object that want to be aware about events on commands
 * (such as creation or deletion of a command).
 * @author Arnaud Blouin
 * @class
 */
export interface CommandHandler extends UndoHandler {
    /**
     * Notifies the handler when the given command is added to the registry.
     * @param {*} cmd The added command.
     */
    onCmdAdded(cmd: Command): void;

    /**
     * Notifies the handler when the given command is cancelled.
     * @param {*} cmd The cancelled command.
     */
    onCmdCancelled(cmd: Command): void;

    /**
     * Notifies the handler when the given command is executed.
     * @param {*} cmd The executed command.
     */
    onCmdExecuted(cmd: Command): void;

    /**
     * Notifies the handler when the given command is done.
     * @param {*} cmd The command that ends.
     */
    onCmdDone(cmd: Command): void;
}


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

/**
 * An interface for undoable objects.
 * @author Arnaud BLOUIN
 * @since 0.1
 * @class
 */
export interface Undoable {
    /**
     * Cancels the command.
     * @since 0.1
     */
    undo(): void;

    /**
     * Redoes the cancelled command.
     * @since 0.1
     */
    redo(): void;

    /**
     * @return {string} The name of the undo command.
     * @since 0.1
     */
    getUndoName(): string;
}

export function isUndoableType(obj: Undoable | Object): obj is Undoable {
    return (<Undoable>obj).undo !== undefined && (<Undoable>obj).redo !== undefined && (<Undoable>obj).getUndoName !== undefined;
}


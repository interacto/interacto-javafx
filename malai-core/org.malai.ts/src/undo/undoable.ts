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
namespace malai {
    /**
     * An interface for undoable objects.
     * @author Arnaud BLOUIN
     */
    export interface Undoable {
        /**
         * Cancels the action.
         */
        undo() : void;

        /**
         * Redoes the cancelled action.
         */
        redo() : void;

        /**
         * @return The name of the undo action.
         */
        getUndoName() : string;
    }

    export function isUndoableInstance(obj : any) : obj is Undoable {
        return obj.undo !== undefined && obj.redo !== undefined && obj.getUndoName !== undefined;
    }
}


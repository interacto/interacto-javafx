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
     * This handler must help object that want to be aware of undone/redone event (for instance, to update some widgets).
     * @author Arnaud BLOUIN
     */
    export interface UndoHandler {
        /**
         * Notifies the handler that the stored undoable objects have been all removed.
         */
        onUndoableCleared() : void;

        /**
         * Actions to do when an undoable object is added to the undo register.
         * @param undoable The undoable object added to the undo register.
         */
        onUndoableAdded(undoable : Undoable) : void;

        /**
         * Actions to do when an undoable object is undone.
         * @param undoable The undone object.
         */
        onUndoableUndo(undoable : Undoable) : void;

        /**
         * Actions to do when an undoable object is redone.
         * @param undoable The redone object.
         */
        onUndoableRedo(undoable : Undoable) : void;
    }
}


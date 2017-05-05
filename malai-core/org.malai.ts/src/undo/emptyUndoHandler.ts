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
     * An implementation of UndoHandler that does nothing. Useful to avoid implementing all the operations.
     * @author Arnaud BLOUIN
     */
    export class EmptyUndoHandler implements UndoHandler {
        /**
         * Creates an undo handler that does nothing.
         */
        public constructor() {
        }

        public onUndoableCleared() : void {
        }

        public onUndoableAdded(_undoable : Undoable) : void {
        }

        public onUndoableUndo(_undoable : Undoable) : void {
        }

        public onUndoableRedo(_undoable : Undoable) : void {
        }
    }
}


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
     * A collector of undone/redone objects.
     * @author Arnaud BLOUIN
     */
    export class UndoCollector {
        /**
         * The default undo/redo collector.
         */
        public static readonly INSTANCE : UndoCollector = new UndoCollector();

        /**
         * The standard text for redo.
         */
        public static readonly EMPTY_REDO : string = "redo";

        /**
         * The standard text for undo.
         */
        public static readonly EMPTY_UNDO : string = "undo";

        /**
         * The Null object for UndoHandler. To avoid the use of null in the stacks.
         */
        private static readonly STUB_UNDO_HANDLER : UndoHandler = new EmptyUndoHandler();

        /**
         * Contains the handlers of each undoable of the undo stack
         */
        private readonly undoHandlers : UndoHandler[];

        /**
         * Contains the handlers of each undoable of the redo stack
         */
        private readonly redoHandlers : UndoHandler[];

        /**
         * Contains the undoable objects.
         */
        private readonly undoables : Undoable[];

        /**
         * Contains the redoable objects.
         */
        private readonly redoables : Undoable[];

        /**
         * The maximal number of undo.
         */
        private sizeMax : number;

        /**
         * The handlers that handles the collector.
         */
        private readonly handlers : UndoHandler[];


        /**
         * Creates the undo collector.
         */
        constructor() {
            this.sizeMax = 0;
            this.handlers = [];
            this.undoables = [];
            this.redoables = [];
            this.undoHandlers = [];
            this.redoHandlers = [];
            this.sizeMax = 30;
        }

        /**
         * Adds a handler to the collector.
         * @param handler The handler to add. Must not be null.
         */
        public addHandler(handler : UndoHandler) : void {
            if(handler != null) {
                this.handlers.push(handler);
            }
        }

        /**
         * Removes the given handler from the collector.
         * @param handler The handler to remove. Must not be null.
         */
        public removeHandler(handler : UndoHandler) : void {
            if(handler != null) {
                let index = this.handlers.indexOf(handler)
                if(index != -1) {
                    this.handlers.splice(index, 1)
                }
            }
        }

        /**
         * Removes all the undoable objects of the collector.
         */
        public clear() : void {
            this.undoables.length = 0;
            this.redoables.length = 0;
            this.undoHandlers.length = 0;
            this.redoHandlers.length = 0;
            this.handlers.forEach(handler => handler.onUndoableCleared());
        }

        /**
         * Adds an undoable object to the collector.
         * @param undoable The undoable object to add.
         * @param undoHandler The handler that produced or is associated to the undoable object.
         */
        public add(undoable : Undoable, undoHandler : UndoHandler) : void {
            if(undoable != null && this.sizeMax > 0) {
                if(this.undoables.length === this.sizeMax) {
                    this.undoables.pop();
                    this.undoHandlers.pop();
                }
                this.undoables.push(undoable);
                if(undoHandler == null) {
                    this.undoHandlers.push(UndoCollector.STUB_UNDO_HANDLER);
                } else {
                    this.undoHandlers.push(undoHandler);
                }
                this.redoables.length = 0;
                this.redoHandlers.length = 0;
                this.handlers.forEach(handler => handler.onUndoableAdded(undoable));
            }
        }

        /**
         * Undoes the last undoable object.
         */
        public undo() : void {
            if(this.undoables.length>0) {
                let undoable : Undoable | undefined = this.undoables.pop();
                let undoHandler : UndoHandler | undefined = this.undoHandlers.pop();

                if(undoable != null && undoHandler != null) {
                    let realUndoable : Undoable = undoable as Undoable;
                    undoable.undo();
                    this.redoables.push(undoable);
                    this.redoHandlers.push(undoHandler);
                    undoHandler.onUndoableUndo(undoable);
                    this.handlers.forEach(handler => handler.onUndoableUndo(realUndoable));
                }
            }
        }

        /**
         * Redoes the last undoable object.
         */
        public redo() : void {
            if(this.redoables.length>0) {
                let undoable : Undoable | undefined = this.redoables.pop();
                let redoHandler : UndoHandler | undefined = this.redoHandlers.pop();

                if(undoable != null && redoHandler != null) {
                    let realUndoable : Undoable = undoable as Undoable;
                    undoable.redo();
                    this.undoables.push(undoable);
                    this.undoHandlers.push(redoHandler);
                    redoHandler.onUndoableRedo(undoable);
                    this.handlers.forEach(handler => handler.onUndoableRedo(realUndoable));
                }
            }
        }

        /**
         * @return The last undoable object name or nothing if there is no last object.
         */
        public getLastUndoMessage() : string | undefined {
            if(this.undoables.length > 0) {
                return this.undoables[this.undoables.length - 1].getUndoName();
            }
            return;
        }

        /**
         * @return The last redoable object name or nothing if there is no last object.
         */
        public getLastRedoMessage() : string | undefined {
            if(this.redoables.length > 0) {
                return this.redoables[this.redoables.length - 1].getUndoName();
            }
            return;
        }

        /**
         * @return The last undoable object or nothing if there is no last object.
         */
        public getLastUndo() : Undoable | undefined {
            if(this.undoables.length > 0) {
                return this.undoables[this.undoables.length - 1];
            }
            return;
        }

        /**
         * @return The last redoable object or nothing if there is no last object.
         */
        public getLastRedo() : Undoable | undefined {
            if(this.redoables.length > 0) {
                return this.redoables[this.redoables.length - 1];
            }
            return;
        }

        /**
         * @return The max number of saved undoable objects.
         */
        public getSizeMax() : number {
            return this.sizeMax;
        }

        /**
         * @param max The max number of saved undoable objects. Must be great than 0.
         */
        public setSizeMax(max : number) : void {
            if(max >= 0) {
                if(max < this.sizeMax) {
                    this.undoables.splice(0, this.sizeMax - max);
                    this.undoHandlers.splice(0, this.sizeMax - max);
                }
                this.sizeMax = max;
            }
        }

        /**
         * @return The stack of saved undoable objects.
         */
        public getUndos() : Undoable[] {
            return this.undoables;
        }

        /**
         * @return The stack of saved redoable objects
         */
        public getRedos() : Undoable[] {
            return this.redoables;
        }
    }
}

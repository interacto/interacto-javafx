/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="../../../util/Optional.ts" />

namespace org.malai.undo {
    /**
     * A collector of undone/redone objects.
     * @author Arnaud BLOUIN
     * @since 0.1
     * @class
     */
    export class UndoCollector {
        /**
         * The default undo/redo collector.
         */
        public static INSTANCE : UndoCollector; public static INSTANCE_$LI$() : UndoCollector { if(UndoCollector.INSTANCE == null) UndoCollector.INSTANCE = new UndoCollector(); return UndoCollector.INSTANCE; };

        /**
         * The standard text for redo.
         */
        public static EMPTY_REDO : string = "redo";

        /**
         * The standard text for undo.
         */
        public static EMPTY_UNDO : string = "undo";

        /**
         * The Null object for UndoHandler. To avoid the use of null in the stacks.
         */
        static STUB_UNDO_HANDLER : org.malai.undo.UndoHandler; public static STUB_UNDO_HANDLER_$LI$() : org.malai.undo.UndoHandler { if(UndoCollector.STUB_UNDO_HANDLER == null) UndoCollector.STUB_UNDO_HANDLER = new org.malai.undo.EmptyUndoHandler(); return UndoCollector.STUB_UNDO_HANDLER; };

        /**
         * Contains the handler of each undoable of the undo stack
         */
        /*private*/ undoHandlers : Array<org.malai.undo.UndoHandler>;

        /**
         * Contains the handler of each undoable of the redo stack
         */
        /*private*/ redoHandlers : Array<org.malai.undo.UndoHandler>;

        /**
         * Contains the undoable objects.
         */
        /*private*/ __undo : Array<org.malai.undo.Undoable>;

        /**
         * Contains the redoable objects.
         */
        /*private*/ __redo : Array<org.malai.undo.Undoable>;

        /**
         * The maximal number of undo.
         */
        /*private*/ sizeMax : number;

        /**
         * The handler that handles the collector.
         */
        /*private*/ handlers : Array<org.malai.undo.UndoHandler>;

        constructor() {
            if(this.undoHandlers===undefined) this.undoHandlers = null;
            if(this.redoHandlers===undefined) this.redoHandlers = null;
            if(this.__undo===undefined) this.__undo = null;
            if(this.__redo===undefined) this.__redo = null;
            if(this.sizeMax===undefined) this.sizeMax = 0;
            if(this.handlers===undefined) this.handlers = null;
            this.handlers = <any>([]);
            this.__undo = [];
            this.__redo = [];
            this.undoHandlers = [];
            this.redoHandlers = [];
            this.sizeMax = 30;
        }

        /**
         * Adds a handler to the collector.
         * @param {*} handler The handler to add. Must not be null.
         */
        public addHandler(handler : org.malai.undo.UndoHandler) {
            if(handler != null) {
                /* add */(this.handlers.push(handler)>0);
            }
        }

        /**
         * Removes the given handler from the collector.
         * @param {*} handler The handler to remove. Must not be null.
         */
        public removeHandler(handler : org.malai.undo.UndoHandler) {
            if(handler != null) {
                /* remove */(a => { let index = a.indexOf(handler); if(index>=0) { a.splice(index, 1); return true; } else { return false; }})(this.handlers);
            }
        }

        public getHandlers() : Array<org.malai.undo.UndoHandler> {
            return /* unmodifiableList */this.handlers.slice(0);
        }

        public clearHandlers() {
            /* clear */(this.handlers.length = 0);
        }

        /**
         * Removes all the undoable objects of the collector.
         */
        public clear() {
            /* clear */(this.__undo.length = 0);
            /* clear */(this.__redo.length = 0);
            /* clear */(this.undoHandlers.length = 0);
            /* clear */(this.redoHandlers.length = 0);
            for(let index121=0; index121 < this.handlers.length; index121++) {
                let h = this.handlers[index121];
                {
                    h.onUndoableCleared();
                }
            }
        }

        /**
         * Adds an undoable object to the collector.
         * @param {*} undoable The undoable object to add.
         * @param {*} undoHandler The handler that produced or is associated to the undoable object.
         */
        public add(undoable : org.malai.undo.Undoable, undoHandler : org.malai.undo.UndoHandler) {
            // if(undoable != null && this.sizeMax > 0) {
            //     if(/* size */(<number>this.__undo.length) === this.sizeMax) {
            //         this.__undo.removeLast();
            //         this.undoHandlers.removeLast();
            //     }
            //     /* push */(this.__undo.push(undoable)>0);
            //     if(undoHandler == null) {
            //         /* push */(this.undoHandlers.push(UndoCollector.STUB_UNDO_HANDLER_$LI$())>0);
            //     } else {
            //         /* push */(this.undoHandlers.push(undoHandler)>0);
            //     }
            //     /* clear */(this.__redo.length = 0);
            //     /* clear */(this.redoHandlers.length = 0);
            //     for(let index122=0; index122 < this.handlers.length; index122++) {
            //         let handler = this.handlers[index122];
            //         {
            //             handler.onUndoableAdded(undoable);
            //         }
            //     }
            // }
        }

        /**
         * Undoes the last undoable object.
         */
        public undo() {
            if(!/* isEmpty */(this.__undo.length == 0)) {
                let undoable : org.malai.undo.Undoable = /* pop */this.__undo.pop();
                let undoHandler : org.malai.undo.UndoHandler = /* pop */this.undoHandlers.pop();
                undoable.undo();
                /* push */(this.__redo.push(undoable)>0);
                /* push */(this.redoHandlers.push(undoHandler)>0);
                undoHandler.onUndoableUndo(undoable);
                for(let index123=0; index123 < this.handlers.length; index123++) {
                    let handler = this.handlers[index123];
                    {
                        handler.onUndoableUndo(undoable);
                    }
                }
            }
        }

        /**
         * Redoes the last undoable object.
         */
        public redo() {
            if(!/* isEmpty */(this.__redo.length == 0)) {
                let undoable : org.malai.undo.Undoable = /* pop */this.__redo.pop();
                let redoHandler : org.malai.undo.UndoHandler = /* pop */this.redoHandlers.pop();
                undoable.redo();
                /* push */(this.__undo.push(undoable)>0);
                /* push */(this.undoHandlers.push(redoHandler)>0);
                redoHandler.onUndoableRedo(undoable);
                for(let index124=0; index124 < this.handlers.length; index124++) {
                    let handler = this.handlers[index124];
                    {
                        handler.onUndoableRedo(undoable);
                    }
                }
            }
        }

        /**
         * @return {util.Optional} The last undoable object name or null if there is no last object.
         */
        public getLastUndoMessage() : util.Optional<string> {
            return /* isEmpty */(this.__undo.length == 0)?util.Optional.empty<any>():util.Optional.ofNullable<any>(/* peek */((s) => { return s[s.length-1]; })(this.__undo).getUndoName());
        }

        /**
         * @return {util.Optional} The last redoable object name or null if there is no last object.
         */
        public getLastRedoMessage() : util.Optional<string> {
            return /* isEmpty */(this.__redo.length == 0)?util.Optional.empty<any>():util.Optional.ofNullable<any>(/* peek */((s) => { return s[s.length-1]; })(this.__redo).getUndoName());
        }

        /**
         * @return {util.Optional} The last undoable object or null if there is no last object.
         */
        public getLastUndo() : util.Optional<org.malai.undo.Undoable> {
            return /* isEmpty */(this.__undo.length == 0)?util.Optional.empty<any>():util.Optional.ofNullable<any>(/* peek */((s) => { return s[s.length-1]; })(this.__undo));
        }

        /**
         * @return {util.Optional} The last redoable object or null if there is no last object.
         */
        public getLastRedo() : util.Optional<org.malai.undo.Undoable> {
            return /* isEmpty */(this.__redo.length == 0)?util.Optional.empty<any>():util.Optional.ofNullable<any>(/* peek */((s) => { return s[s.length-1]; })(this.__redo));
        }

        /**
         * @return {number} The max number of saved undoable objects.
         */
        public getSizeMax() : number {
            return this.sizeMax;
        }

        /**
         * @param {number} max The max number of saved undoable objects. Must be great than 0.
         */
        public setSizeMax(max : number) {
            // if(max >= 0) {
            //     for(let i : number = 0, nb : number = /* size */(<number>this.__undo.length) - max; i < nb; i++) {
            //         this.__undo.removeLast();
            //         this.undoHandlers.removeLast();
            //     };
            //     this.sizeMax = max;
            // }
        }

        /**
         * @return {*[]} The stack of saved undoable objects.
         * @since 0.1
         */
        public getUndo() : Array<org.malai.undo.Undoable> {
            return this.__undo;
        }

        /**
         * @return {*[]} The stack of saved redoable objects
         * @since 0.1
         */
        public getRedo() : Array<org.malai.undo.Undoable> {
            return this.__redo;
        }
    }
    UndoCollector["__class"] = "org.malai.undo.UndoCollector";

}


org.malai.undo.UndoCollector.STUB_UNDO_HANDLER_$LI$();

org.malai.undo.UndoCollector.INSTANCE_$LI$();

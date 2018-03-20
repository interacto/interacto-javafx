/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Creates an undo handler that does nothing.
     * @class
     * @author Arnaud BLOUIN
     */
    export class EmptyUndoHandler implements UndoHandler {
        public constructor() {
        }

        /**
         * 
         */
        public onUndoableCleared() {
        }

        /**
         * 
         * @param {*} undoable
         */
        public onUndoableAdded(undoable : Undoable) {
        }

        /**
         * 
         * @param {*} undoable
         */
        public onUndoableUndo(undoable : Undoable) {
        }

        /**
         * 
         * @param {*} undoable
         */
        public onUndoableRedo(undoable : Undoable) {
        }
    }
    EmptyUndoHandler["__class"] = "malai.EmptyUndoHandler";
    EmptyUndoHandler["__interfaces"] = ["malai.UndoHandler"];


}


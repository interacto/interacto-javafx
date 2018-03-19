/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.undo {
    /**
     * Creates an undo handler that does nothing.
     * @class
     * @author Arnaud BLOUIN
     */
    export class EmptyUndoHandler implements org.malai.undo.UndoHandler {
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
        public onUndoableAdded(undoable : org.malai.undo.Undoable) {
        }

        /**
         * 
         * @param {*} undoable
         */
        public onUndoableUndo(undoable : org.malai.undo.Undoable) {
        }

        /**
         * 
         * @param {*} undoable
         */
        public onUndoableRedo(undoable : org.malai.undo.Undoable) {
        }
    }
    EmptyUndoHandler["__class"] = "org.malai.undo.EmptyUndoHandler";
    EmptyUndoHandler["__interfaces"] = ["org.malai.undo.UndoHandler"];


}


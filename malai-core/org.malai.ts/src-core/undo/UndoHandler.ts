/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * This handler must help object that want to be aware of undone/redone event (for instance, to update some widgets).
     * @author Arnaud BLOUIN
     * @since 0.1
     * @class
     */
    export interface UndoHandler {
        /**
         * Notifies the handler that the stored undoable objects have been all removed.
         * @since 0.2
         */
        onUndoableCleared();

        /**
         * Actions to do when an undoable object is added to the undo register.
         * @param {*} undoable The undoable object added to the undo register.
         * @since 0.2
         */
        onUndoableAdded(undoable : Undoable);

        /**
         * Actions to do when an undoable object is undone.
         * @param {*} undoable The undone object.
         * @since 0.2
         */
        onUndoableUndo(undoable : Undoable);

        /**
         * Actions to do when an undoable object is redone.
         * @param {*} undoable The redone object.
         * @since 0.2
         */
        onUndoableRedo(undoable : Undoable);
    }
}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * An interface for undoable objects.
     * @author Arnaud BLOUIN
     * @since 0.1
     * @class
     */
    export interface Undoable {
        /**
         * Cancels the action.
         * @since 0.1
         */
        undo();

        /**
         * Redoes the cancelled action.
         * @since 0.1
         */
        redo();

        /**
         * @return {string} The name of the undo action.
         * @since 0.1
         */
        getUndoName() : string;
    }
}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action {
    /**
     * This interface allows to create a bridge between an action and an object that want to be aware about events on actions
     * (such as creation or deletion of an action).
     * @author Arnaud Blouin
     * @class
     */
    export interface ActionHandler extends org.malai.undo.UndoHandler {
        /**
         * Notifies the handler when the given action is added to the registry.
         * @param {*} action The added action.
         */
        onActionAdded(action : org.malai.action.Action);

        /**
         * Notifies the handler when the given action is cancelled.
         * @param {*} action The cancelled action.
         */
        onActionCancelled(action : org.malai.action.Action);

        /**
         * Notifies the handler when the given action is executed.
         * @param {*} action The executed action.
         */
        onActionExecuted(action : org.malai.action.Action);

        /**
         * Notifies the handler when the given action is done.
         * @param {*} action The action that ends.
         */
        onActionDone(action : org.malai.action.Action);
    }
}


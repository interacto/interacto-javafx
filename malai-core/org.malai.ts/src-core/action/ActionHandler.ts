/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * This interface allows to create a bridge between an action and an object that want to be aware about events on actions
     * (such as creation or deletion of an action).
     * @author Arnaud Blouin
     * @class
     */
    export interface ActionHandler extends UndoHandler {
        /**
         * Notifies the handler when the given action is added to the registry.
         * @param {*} action The added action.
         */
        onActionAdded(action : Action);

        /**
         * Notifies the handler when the given action is cancelled.
         * @param {*} action The cancelled action.
         */
        onActionCancelled(action : Action);

        /**
         * Notifies the handler when the given action is executed.
         * @param {*} action The executed action.
         */
        onActionExecuted(action : Action);

        /**
         * Notifies the handler when the given action is done.
         * @param {*} action The action that ends.
         */
        onActionDone(action : Action);
    }
}


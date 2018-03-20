/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * An action is produced and executed in reaction of a user interaction.
     * It follows the command design pattern.
     * It contains statements to execute to perform the action.
     * The interface Undoable can be used to add undo/redo features to an action.
     * @author Arnaud Blouin
     * @class
     */
    export interface Action {
        /**
         * Flushes the action.
         * Can be useful to close streams, free objects, etc.
         * An action should flushed manually only when it is not managed by the action registry of the application.
         * When an action is gathered and managed by an action registry, it is automatically flushed when the
         * action registry removes the action.
         */
        flush();

        /**
         * Specifies whether the action must be saved in the action register. For instance,
         * some actions, such as a scroll, should not be saved or put in the undo/redo manager. Such actions should not be registrable.
         * @return {malai.Action.RegistrationPolicy} The registration policy.
         */
        getRegistrationPolicy() : RegistrationPolicy;

        /**
         * This method manages the execution of the action.
         * @return {boolean} True: the execution of the action is OK.
         */
        doIt() : boolean;

        /**
         * Checks whether the action can be executed.
         * @return {boolean} True if the action can be executed.
         * @since 0.1
         */
        canDo() : boolean;

        /**
         * State whether the execution of this action has effects on the system.
         * @return {boolean} True: the action has effects on the system.
         */
        hadEffect() : boolean;

        /**
         * Checks whether the current action can be cancelled by the given one.
         * @param {*} action The action to check whether it can cancel the current action.
         * @return {boolean} True: The given action can cancel the current action.
         */
        unregisteredBy(action : Action) : boolean;

        /**
         * Marks the action as "done" and sends it to the action registry.
         * @since 0.1
         */
        done();

        /**
         * To know whether the action has been marked as 'done'.
         * @return {boolean} True: the action has been marked as 'done'.
         */
        isDone() : boolean;

        /**
         * Marks the action has aborted.
         */
        cancel();

        /**
         * Provides the status of the action.
         * @return {malai.Action.ActionStatus} The status of the action.
         * @since 0.2
         */
        getStatus() : ActionStatus;

        /**
         * The execution of the action may provoke the execution of other actions.
         * For instance with a drawing editor, one may want that after having pasted shapes, the new shapes must be selected.
         * So, the action PasteShapes will be followed by an action SelectShapes.
         * This is the goal of the operation.
         * This operation creates and initialises the action that will be executed after each final execution of the current action.
         * @return {*[]} A list of actions that must be executed afterward. Cannot be null.
         */
        followingActions() : Array<Action>;
    }

    /**
     * Defines the registration policy of the action.
     * @enum
     * @property {malai.Action.RegistrationPolicy} NONE
     * The action is never registered.
     * @property {malai.Action.RegistrationPolicy} UNLIMITED
     * The action is registered in the action register. The action is not flushed when the registry wants to free some actions.
     * @property {malai.Action.RegistrationPolicy} LIMITED
     * The action is registered in the action register. The action can be flushed by the registry.
     * @class
     */
    export enum RegistrationPolicy {

        /**
         * The action is never registered.
         */
        NONE,
        /**
         * The action is registered in the action register. The action is not flushed when the registry wants to free some actions.
         */
        UNLIMITED,
        /**
         * The action is registered in the action register. The action can be flushed by the registry.
         */
        LIMITED
    }

    /**
     * Defines the different states of the action.
     * @since 0.2
     * @enum
     * @property {malai.Action.ActionStatus} CREATED
     * When the action is created but not executed yet.
     * @property {malai.Action.ActionStatus} EXECUTED
     * When the action has been created and executed one time.
     * @property {malai.Action.ActionStatus} CANCELLED
     * When the action has been cancelled.
     * @property {malai.Action.ActionStatus} DONE
     * When the action has been marked as done.
     * @property {malai.Action.ActionStatus} FLUSHED
     * The action has been flushed. In this case, the action must not be used anymore.
     * @class
     */
    export enum ActionStatus {

        /**
         * When the action is created but not executed yet.
         */
        CREATED,
        /**
         * When the action has been created and executed one time.
         */
        EXECUTED,
        /**
         * When the action has been cancelled.
         */
        CANCELLED,
        /**
         * When the action has been marked as done.
         */
        DONE,
        /**
         * The action has been flushed. In this case, the action must not be used anymore.
         */
        FLUSHED
    }
}

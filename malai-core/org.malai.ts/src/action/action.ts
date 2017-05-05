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
     * Defines the different states of the action.
     */
    export enum ActionStatus {
        CREATED, EXECUTED, ABORTED, DONE, FLUSHED
    }


    /**
     * An action is produced and executed in reaction of a user interaction.
     * It follows the command design pattern.
     * It contains statements to execute to perform the action.
     * The interface Undoable can be used to add undo/redo features to an action.
     * @author Arnaud Blouin
     */
    export interface Action {
        /**
         * Flushes the action.
         * Can be useful to close streams, free objects, etc.
         * An action should flushed manually only when it is not managed by the action registry of the application.
         * When an action is gathered and managed by an action registry, it is automatically flushed when the
         * action registry removes the action.
         */
        flush() : void;

        /**
         * Specifies whether the action must be saved in the action register. For instance,
         * some actions, such as a scroll of the scroll bars, should not be saved or
         * put in the undo/redo manager. Thus, they must not be registrable.
         * @returns True: the action is registrable.
         */
        isRegisterable() : boolean;

        /**
         * This method manages the execution of the action.
         * @return True: the execution of the action is OK.
         */
        doIt() : boolean;

        /**
         * Checks whether the action can be executed.
         * @return True if the action can be executed.
         */
        canDo() : boolean;

        /**
         * State whether the execution of this action has effects on the system.
         * @return True: the action has effects on the system.
         */
        hadEffect() : boolean;

        /**
         * Checks whether the current action can be cancelled by the given one.
         * @param action The action to check whether it can cancel the current action.
         * @return True: The given action can cancel the current action.
         */
        unregisteredBy(action : Action) : boolean;

        /**
         * Marks the action as "done" and sends it to the action registry.
         */
        done() : void;

        /**
         * To know whether the action has been marked as 'done'.
         * @return True: the action has been marked as 'done'.
         */
        isDone() : boolean;

        /**
         * Marks the action has aborted.
         */
        abort() : void;

        /**
         * Provides the status of the action.
         * @return The status of the action.
         */
        getStatus() : ActionStatus;

        /**
         * The execution of the action may provoke the execution of other actions.
         * For instance with a drawing editor, one may want that after having pasted shapes, the new shapes must be selected.
         * So, the action PasteShapes will be followed by an action SelectShapes.
         * This is the goal of the operation.
         * This operation creates and initialises the action that will be executed after each final execution of the current action.
         * @return A list of actions that must be executed afterward. Cannot be null.
         */
        followingActions() : Action[];
    }
}


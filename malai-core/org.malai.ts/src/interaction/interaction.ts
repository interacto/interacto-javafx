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
     * An interface defining the concept of interaction and its related services.
     * @author Arnaud BLOUIN
     */
    export interface Interaction extends EventProcessor {
        /**
         * Terminates the state machine.
         */
        onTerminating() : void;

        /**
         * Aborts the state machine.
         */
        onAborting() : void;

        /**
         * Starts the state machine.
         */
        onStarting() : void;

        /**
         * Updates the state machine.
         */
        onUpdating() : void;

        /**
         * Adds a state to the state machine.
         * @param state The state to add. Must not be null.
         */
        addState(state : State) : void;

        /**
         * Reinits the state machine.
         */
        reinit() : void;

        /**
         * Defines if the state machine is activated.
         * @param activated True: the state machine will be activated.
         */
        setActivated(activated : boolean) : void;

        /**
         * @return The current state of the running state machine. Or null when the machine is not running.
         */
        getCurrentState() : State;

        /**
         * @return True or false depending on whether the state machine is activated.
         */
        isActivated() : boolean;

        /**
         * Checks whether the transition can be executed and executes it when possible.
         * @param transition The transition to check.
         * @return True: the transition has been executed. False otherwise.
         */
        checkTransition(transition : Transition) : boolean;

        /**
         * @return True: the state machine is running.
         */
        isRunning() : boolean;

        /**
         * @return The handlers that listens to the interaction.
         */
        getHandlers() : InteractionHandler[];

        /**
         * Adds an interaction handler.
         * @param handler The handler to add.
         */
        addHandler(handler : InteractionHandler) : void;

        /**
         * Clears the events of the interaction still in process.
         */
        clearEventsStillInProcess() : void;

        /**
         * Stops the interaction and clears all its events waiting for a process.
         */
        clearEvents() : void;
    }
}


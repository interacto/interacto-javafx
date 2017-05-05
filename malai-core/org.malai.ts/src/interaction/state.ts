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
     * This interface defines the notion of state that composes a state machine.
     * @author Arnaud BLOUIN
     */
    export interface State {
        /**
         * Adds a transition to the state.
         * @param trans The new transition. Must not be null.
         */
        addTransition(trans : Transition) : void;

        /**
         * @return The name of the state.
         */
        getName() : string;

        /**
         * @return The transitions that leaves the state.
         */
        getTransitions() : Transition[];

        /**
         * @param index The position of the transition to get.
         * @return The corresponding transition or null if the given index is not valid.
         */
        getTransition(index : number) : Transition | undefined;

        setInteraction(newInteraction : Interaction) : void;

        getInteraction() : Interaction;
    }
}


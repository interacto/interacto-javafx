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
     * This interface defines the notion of transition of a state machine.
     * @author Arnaud BLOUIN
     */
    export interface Transition {
        /**
         * Performs the actions to do when the transition is executed.
         * Should be overridden.
         */
        action() : void;

        /**
         * @return True: the condition defining if the transition can be executed is correct.
         * By default: true. Should be overridden.
         */
        isGuardRespected() : boolean;

        /**
         * @return The source state of the transition.
         */
        getInputState() : SourceableState;

        /**
         * @return The target state of the transition.
         */
        getOutputState() : TargetableState;
    }
}


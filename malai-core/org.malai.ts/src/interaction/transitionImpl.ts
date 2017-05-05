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
     * A transition links two states of a state machine if a given condition is respected.
     * Actions can be performed when executing the transition.
     * @author Arnaud BLOUIN
     */
    export abstract class TransitionImpl implements Transition {
        /**
         * The source state.
         */
        protected readonly inputState : SourceableState;

        /**
         * The target state.
         */
        protected readonly outputState : TargetableState;

        /**
         * Defines a transition.
         * @param inputState The source state of the transition.
         * @param outputState The target state of the transition.
         */
        public constructor(inputState : SourceableState, outputState : TargetableState) {
            this.inputState = inputState;
            this.outputState = outputState;
            this.inputState.addTransition(this);
        }

        public action() : void {
        }

        public isGuardRespected() : boolean {
            return true;
        }

        public getInputState() : SourceableState {
            return this.inputState;
        }

        public getOutputState() : TargetableState {
            return this.outputState;
        }
    }
}

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
     * A state of a user interaction.
     * @author Arnaud BLOUIN
     */
    export abstract class StateImpl implements State {
        /**
         * The name of the state.
         */
        protected readonly name : string;

        /**
         * The list of the transitions that leave the state.
         */
        protected readonly transitions : Transition[];

        /**
         * The state machine that contains the state.
         */
        protected interaction : Interaction;

        /**
         * Creates the state.
         * @param name The name of the state.
         */
        public constructor(name : string) {
            this.name = name;
            this.transitions = [];
        }

        public setInteraction(newInteraction : Interaction) : void {
            this.interaction = newInteraction;
        }

        public getInteraction() : Interaction {
            return this.interaction;
        }

        public addTransition(trans : Transition) : void {
            if(trans != null) {
                this.transitions.push(trans);
            }
        }

        public getName() : string {
            return this.name;
        }

        public getTransitions() : Transition[] {
            return this.transitions;
        }

        public getTransition(i : number) : Transition | undefined {
            if(i > 0 && i <= this.transitions.length) {
                return this.transitions[i];
            }
            return;
        }
    }
}

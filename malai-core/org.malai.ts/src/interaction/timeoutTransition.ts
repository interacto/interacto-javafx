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

/// <reference path="transitionImpl.ts" />

namespace malai {
    /**
     * This transition defines a timeout: when the user does nothing during a given duration, the timeout transition is executed.
     * @author Arnaud BLOUIN
     */
    export class TimeoutTransition extends TransitionImpl {
        /**
         * The timeout in ms.
         */
        private timeout : number;

        /**
         * The current thread in progress.
         */
        private timeoutInProgress : boolean;

        /**
         * Creates the transition.
         * @param inputState The source state of the transition.
         * @param outputState The target state of the transition.
         * @param timeout The timeout in ms. Must be greater than 0.
         */
        public constructor(inputState : SourceableState, outputState : TargetableState, timeout : number) {
            super(inputState, outputState);
            this.timeout = timeout;
            this.timeoutInProgress = false;
        }

        /**
         * Launches the timer.
         */
        public startTimeout() : void {
            if(!this.timeoutInProgress) {
                this.timeoutInProgress = true;
                setInterval(() => {
                    if(this.timeoutInProgress) {
                        this.getInputState().getInteraction().onTimeout(this);
                        this.timeoutInProgress = false;
                    }
                }, this.timeout);
            }
        }

        /**
         * Stops the timer.
         * @since 0.2
         */
        public stopTimeout() : void {
           this.timeoutInProgress = false;
        }

        /**
         * @param timeout The timeout in ms. Must be greater than 0.
         * @since 0.2
         */
        public setTimeout(timeout : number) : void {
            if(timeout > 0) {
                this.timeout = timeout;
            }
        }

        /**
         * @return The timeout in ms.
         * @since 0.2
         */
        public getTimeout() : number {
            return this.timeout;
        }
    }
}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="Transition.ts" />

namespace malai {
    export class EpsilonTransition<E> extends Transition<E> {
        public constructor(srcState : OutputState<E>, tgtState : InputState<E>) {
            super(srcState, tgtState);
        }

        /**
         * 
         * @param {*} event
         * @return {boolean}
         */
        accept(event : E) : boolean {
            return true;
        }

        /**
         * 
         * @param {*} event
         * @return {boolean}
         */
        isGuardOK(event : E) : boolean {
            return true;
        }

        /**
         * 
         * @return {*[]}
         */
        public getAcceptedEvents() : Set<String> {
            return new Set([]);
        }
    }
    EpsilonTransition["__class"] = "malai.EpsilonTransition";

}


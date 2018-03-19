/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="Transition.ts" />

namespace org.malai.fsm {
    export class EpsilonTransition<E> extends org.malai.fsm.Transition<E> {
        public constructor(srcState : org.malai.fsm.OutputState<E>, tgtState : org.malai.fsm.InputState<E>) {
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
        public getAcceptedEvents() : Array<any> {
            return /* emptySet */[];
        }
    }
    EpsilonTransition["__class"] = "org.malai.fsm.EpsilonTransition";

}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="OutputStateImpl.ts" />

namespace malai {
    export class InitState<E> extends OutputStateImpl<E> {
        constructor(stateMachine : FSM<E>, stateName : string) {
            super(stateMachine, stateName);
        }

        /**
         * 
         */
        public exit() {
            this.checkStartingState();
        }
    }
    InitState["__class"] = "malai.InitState";
    InitState["__interfaces"] = ["malai.State","malai.OutputState"];


}


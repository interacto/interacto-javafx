/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="OutputStateImpl.ts" />

namespace org.malai.fsm {
    export class InitState<E> extends org.malai.fsm.OutputStateImpl<E> {
        constructor(stateMachine : org.malai.fsm.FSM<E>, stateName : string) {
            super(stateMachine, stateName);
        }

        /**
         * 
         */
        public exit() {
            this.checkStartingState();
        }
    }
    InitState["__class"] = "org.malai.fsm.InitState";
    InitState["__interfaces"] = ["org.malai.fsm.State","org.malai.fsm.OutputState"];


}


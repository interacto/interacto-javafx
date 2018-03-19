/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="StateImpl.ts" />

namespace org.malai.fsm {
    export class CancellingState<E> extends org.malai.fsm.StateImpl<E> implements org.malai.fsm.InputState<E> {
        checkStartingState() {
            if(!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                this.getFSM().onStarting();
            }
        }
        public constructor(stateMachine : org.malai.fsm.FSM<E>, stateName : string) {
            super(stateMachine, stateName);
        }

        /**
         * 
         */
        public enter() {
            this.fsm.onCancelling();
        }
    }
    CancellingState["__class"] = "org.malai.fsm.CancellingState";
    CancellingState["__interfaces"] = ["org.malai.fsm.State","org.malai.fsm.InputState"];


}


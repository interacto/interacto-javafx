/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="StateImpl.ts" />

namespace malai {
    export class CancellingState<E> extends StateImpl<E> implements InputState<E> {
        checkStartingState() {
            if(!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                this.getFSM().onStarting();
            }
        }
        public constructor(stateMachine : FSM<E>, stateName : string) {
            super(stateMachine, stateName);
        }

        /**
         * 
         */
        public enter() {
            this.fsm.onCancelling();
        }
    }
    CancellingState["__class"] = "malai.CancellingState";
    CancellingState["__interfaces"] = ["malai.State","malai.InputState"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export class StdState<E> extends OutputStateImpl<E> implements InputState<E> {
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
            this.checkStartingState();
            this.fsm.enterStdState(this);
        }

        /**
         * 
         */
        public exit() {
        }
    }
    StdState["__class"] = "malai.StdState";
    StdState["__interfaces"] = ["malai.State","malai.OutputState","malai.InputState"];


}


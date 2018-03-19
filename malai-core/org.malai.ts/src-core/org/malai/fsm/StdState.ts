/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.fsm {
    export class StdState<E> extends org.malai.fsm.OutputStateImpl<E> implements org.malai.fsm.InputState<E> {
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
            this.checkStartingState();
            this.fsm.enterStdState(this);
        }

        /**
         * 
         */
        public exit() {
        }
    }
    StdState["__class"] = "org.malai.fsm.StdState";
    StdState["__interfaces"] = ["org.malai.fsm.State","org.malai.fsm.OutputState","org.malai.fsm.InputState"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.fsm {
    export class TerminalState<E> extends org.malai.fsm.StateImpl<E> implements org.malai.fsm.InputState<E> {
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
            this.fsm.onTerminating();
        }
    }
    TerminalState["__class"] = "org.malai.fsm.TerminalState";
    TerminalState["__interfaces"] = ["org.malai.fsm.State","org.malai.fsm.InputState"];


}


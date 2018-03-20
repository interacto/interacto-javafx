/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export class TerminalState<E> extends StateImpl<E> implements InputState<E> {
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
            this.fsm.onTerminating();
        }
    }
    TerminalState["__class"] = "malai.TerminalState";
    TerminalState["__interfaces"] = ["malai.State","malai.InputState"];


}


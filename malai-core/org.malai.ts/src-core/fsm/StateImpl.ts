/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export abstract class StateImpl<E> implements State<E> {
        checkStartingState() {
            if(!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                this.getFSM().onStarting();
            }
        }
        fsm : FSM<E>;

        name : string;

        constructor(stateMachine : FSM<E>, stateName : string) {
            if(this.fsm===undefined) this.fsm = null;
            if(this.name===undefined) this.name = null;
            this.fsm = stateMachine;
            this.name = stateName;
        }

        /**
         * 
         * @return {string}
         */
        public getName() : string {
            return this.name;
        }

        /**
         * 
         * @return {FSM}
         */
        public getFSM() : FSM<E> {
            return this.fsm;
        }
    }
    StateImpl["__class"] = "malai.StateImpl";
    StateImpl["__interfaces"] = ["malai.State"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.fsm {
    export abstract class StateImpl<E> implements org.malai.fsm.State<E> {
        checkStartingState() {
            if(!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                this.getFSM().onStarting();
            }
        }
        fsm : org.malai.fsm.FSM<E>;

        name : string;

        constructor(stateMachine : org.malai.fsm.FSM<E>, stateName : string) {
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
         * @return {org.malai.fsm.FSM}
         */
        public getFSM() : org.malai.fsm.FSM<E> {
            return this.fsm;
        }

        /**
         * 
         * @return {string}
         */
        public toString() : string {
            return /* getSimpleName */(c => c["__class"]?c["__class"].substring(c["__class"].lastIndexOf('.')+1):c["name"].substring(c["name"].lastIndexOf('.')+1))((<any>this.constructor)) + "{name=\'" + this.name + "\'}";
        }
    }
    StateImpl["__class"] = "org.malai.fsm.StateImpl";
    StateImpl["__interfaces"] = ["org.malai.fsm.State"];


}


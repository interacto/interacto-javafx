/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.interaction {
    /**
     * Creates the state.
     * @param {string} name The name of the state.
     * @throws IllegalArgumentException If the given name is null.
     * @since 0.1
     * @class
     * @author Arnaud BLOUIN
     */
    export abstract class StateImpl implements org.malai.stateMachine.State {
        /**
         * The name of the state.
         */
        name : string;

        /**
         * The list of the transitions that leave the state.
         */
        transitions : Array<org.malai.stateMachine.Transition>;

        /**
         * The state machine that contains the state.
         */
        stateMachine : org.malai.interaction.Interaction;

        public constructor(name : string) {
            if(this.name===undefined) this.name = null;
            if(this.transitions===undefined) this.transitions = null;
            if(this.stateMachine===undefined) this.stateMachine = null;
            if(name == null) throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            this.name = name;
            this.stateMachine = null;
            this.transitions = <any>([]);
        }

        /**
         * 
         * @param {*} sm
         */
        public setStateMachine(sm : org.malai.stateMachine.StateMachine) {
            if(sm != null && (sm["__interfaces"] != null && sm["__interfaces"].indexOf("org.malai.interaction.Interaction") >= 0 || sm.constructor != null && sm.constructor["__interfaces"] != null && sm.constructor["__interfaces"].indexOf("org.malai.interaction.Interaction") >= 0)) {
                this.stateMachine = <org.malai.interaction.Interaction><any>sm;
            }
        }

        /**
         * 
         * @return {*}
         */
        public getStateMachine() : org.malai.interaction.Interaction {
            return this.stateMachine;
        }

        /**
         * 
         * @param {*} trans
         */
        public addTransition(trans : org.malai.stateMachine.Transition) {
            if(trans != null) {
                /* add */(this.transitions.push(trans)>0);
            }
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
         * @return {*[]}
         */
        public getTransitions() : Array<org.malai.stateMachine.Transition> {
            return this.transitions;
        }

        /**
         * 
         * @param {number} i
         * @return {*}
         */
        public getTransition(i : number) : org.malai.stateMachine.Transition {
            return i < 0 || i >= /* size */(<number>this.transitions.length)?null:/* get */this.transitions[i];
        }
    }
    StateImpl["__class"] = "org.malai.interaction.StateImpl";
    StateImpl["__interfaces"] = ["org.malai.stateMachine.State"];
}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.fsm {
    export abstract class OutputStateImpl<E> extends org.malai.fsm.StateImpl<E> implements org.malai.fsm.OutputState<E> {
        process(event : any) : boolean {
            {
                let array129 = this.getTransitions();
                for(let index128=0; index128 < array129.length; index128++) {
                    let tr = array129[index128];
                    {
                        try {
                            if(tr.execute(event).isPresent()) {
                                return true;
                            }
                        } catch(ignored) {
                        };
                    }
                }
            }
            return false;
        }
        checkStartingState() {
            if(!this.getFSM().isStarted() && this.getFSM().startingState === this) {
                this.getFSM().onStarting();
            }
        }
        transitions : Array<org.malai.fsm.Transition<E>>;

        constructor(stateMachine : org.malai.fsm.FSM<E>, stateName : string) {
            super(stateMachine, stateName);
            if(this.transitions===undefined) this.transitions = null;
            this.transitions = <any>([]);
        }

        /**
         * 
         * @return {org.malai.fsm.Transition[]}
         */
        public getTransitions() : Array<org.malai.fsm.Transition<E>> {
            return /* unmodifiableList */this.transitions.slice(0);
        }

        /**
         * 
         * @param {org.malai.fsm.Transition} tr
         */
        public addTransition(tr : org.malai.fsm.Transition<E>) {
            if(tr != null) {
                /* add */(this.transitions.push(tr)>0);
            }
        }

        public abstract exit(): any;    }
    OutputStateImpl["__class"] = "org.malai.fsm.OutputStateImpl";
    OutputStateImpl["__interfaces"] = ["org.malai.fsm.State","org.malai.fsm.OutputState"];


}


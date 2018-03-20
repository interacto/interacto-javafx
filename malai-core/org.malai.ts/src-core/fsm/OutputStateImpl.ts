/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export abstract class OutputStateImpl<E> extends StateImpl<E> implements OutputState<E> {
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
                        }
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
        transitions : Array<Transition<E>>;

        constructor(stateMachine : FSM<E>, stateName : string) {
            super(stateMachine, stateName);
            if(this.transitions===undefined) this.transitions = null;
            this.transitions = <any>([]);
        }

        /**
         * 
         * @return {Transition[]}
         */
        public getTransitions() : Array<Transition<E>> {
            return /* unmodifiableList */this.transitions.slice(0);
        }

        /**
         * 
         * @param {Transition} tr
         */
        public addTransition(tr : Transition<E>) {
            if(tr != null) {
                /* add */(this.transitions.push(tr)>0);
            }
        }

        public abstract exit(): any;    }
    OutputStateImpl["__class"] = "malai.OutputStateImpl";
    OutputStateImpl["__interfaces"] = ["malai.State","malai.OutputState"];


}


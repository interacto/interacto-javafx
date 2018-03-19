/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.interaction {
    /**
     * This state defines a terminal state that ends the interaction.
     * @author Arnaud BLOUIN
     * @since 0.1
     * @param {string} name
     * @class
     * @extends org.malai.interaction.StateImpl
     */
    export class TerminalState extends org.malai.interaction.StateImpl implements org.malai.stateMachine.TargetableState {
        public constructor(name : string) {
            super(name);
        }

        /**
         * 
         */
        public onIngoing() {
            this.stateMachine.onTerminating();
        }
    }
    TerminalState["__class"] = "org.malai.interaction.TerminalState";
    TerminalState["__interfaces"] = ["org.malai.stateMachine.TargetableState","org.malai.stateMachine.State"];


}


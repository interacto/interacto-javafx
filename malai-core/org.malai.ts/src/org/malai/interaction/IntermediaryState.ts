/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="StateImpl.ts" />

namespace org.malai.interaction {
    /**
     * This state defines a standard state that modifies the state of the interaction.
     * @author Arnaud BLOUIN
     * @since 0.1
     * @param {string} name
     * @class
     * @extends org.malai.interaction.StateImpl
     */
    export class IntermediaryState extends org.malai.interaction.StateImpl implements org.malai.stateMachine.SourceableState, org.malai.stateMachine.TargetableState {
        public constructor(name : string) {
            super(name);
        }

        /**
         * 
         */
        public onIngoing() {
            this.stateMachine.onUpdating();
        }

        /**
         * 
         */
        public onOutgoing() {
        }
    }
    IntermediaryState["__class"] = "org.malai.interaction.IntermediaryState";
    IntermediaryState["__interfaces"] = ["org.malai.stateMachine.SourceableState","org.malai.stateMachine.TargetableState","org.malai.stateMachine.State"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="StateImpl.ts" />

namespace org.malai.interaction {
    /**
     * This state defines an aborting state that cancels the interaction.
     * @author Arnaud BLOUIN
     * @param {string} name
     * @class
     * @extends org.malai.interaction.StateImpl
     */
    export class CancellingState extends org.malai.interaction.StateImpl implements org.malai.stateMachine.TargetableState {
        public constructor(name : string) {
            super(name);
        }

        /**
         * 
         */
        public onIngoing() {
            this.stateMachine.onCancelling();
        }
    }
    CancellingState["__class"] = "org.malai.interaction.CancellingState";
    CancellingState["__interfaces"] = ["org.malai.stateMachine.TargetableState","org.malai.stateMachine.State"];


}


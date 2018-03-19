/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="StateImpl.ts" />

namespace org.malai.interaction {
    /**
     * Creates the initial state.
     * @since 0.1
     * @class
     * @extends org.malai.interaction.StateImpl
     * @author Arnaud BLOUIN
     */
    export class InitState extends org.malai.interaction.StateImpl implements org.malai.stateMachine.SourceableState {
        public constructor() {
            super("Init");
        }

        /**
         * 
         */
        public onOutgoing() {
            this.stateMachine.onStarting();
        }
    }
    InitState["__class"] = "org.malai.interaction.InitState";
    InitState["__interfaces"] = ["org.malai.stateMachine.SourceableState","org.malai.stateMachine.State"];


}


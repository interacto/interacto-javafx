/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.interaction {
    /**
     * Defines a transition.
     * @param {*} inputState The source state of the transition.
     * @param {*} outputState The target state of the transition.
     * @throws IllegalArgumentException If one of the given parameters is null or not valid.
     * @since 0.1
     * @class
     * @author Arnaud BLOUIN
     */
    export abstract class TransitionImpl implements org.malai.stateMachine.Transition {
        /**
         * The source state.
         */
        inputState : org.malai.stateMachine.SourceableState;

        /**
         * The target state.
         */
        outputState : org.malai.stateMachine.TargetableState;

        /**
         * The ID of the HID that produced the transition.
         */
        hid : number;

        public constructor(inputState : org.malai.stateMachine.SourceableState, outputState : org.malai.stateMachine.TargetableState) {
            if(this.inputState===undefined) this.inputState = null;
            if(this.outputState===undefined) this.outputState = null;
            if(this.hid===undefined) this.hid = 0;
            if(inputState == null || outputState == null) throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            this.inputState = inputState;
            this.outputState = outputState;
            this.inputState.addTransition(this);
        }

        /**
         * 
         */
        public action() {
        }

        /**
         * 
         * @return {boolean}
         */
        public isGuardRespected() : boolean {
            return true;
        }

        /**
         * 
         * @return {*}
         */
        public getInputState() : org.malai.stateMachine.SourceableState {
            return this.inputState;
        }

        /**
         * 
         * @return {*}
         */
        public getOutputState() : org.malai.stateMachine.TargetableState {
            return this.outputState;
        }

        /**
         * 
         * @return {string}
         */
        public toString() : string {
            return /* getSimpleName */(c => c["__class"]?c["__class"].substring(c["__class"].lastIndexOf('.')+1):c["name"].substring(c["name"].lastIndexOf('.')+1))((<any>this.constructor)) + '[' + this.inputState.getName() + ">>" + this.outputState.getName() + ']';
        }

        /**
         * 
         * @return {number}
         */
        public getHid() : number {
            return this.hid;
        }

        /**
         * 
         * @param {number} hid
         */
        public setHid(hid : number) {
            this.hid = hid;
        }

        public abstract getEventType<T>() : T;
    }
    TransitionImpl["__class"] = "org.malai.interaction.TransitionImpl";
    TransitionImpl["__interfaces"] = ["org.malai.stateMachine.Transition"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="TransitionImpl.ts" />

namespace org.malai.interaction {
    /**
     * Creates the transition.
     * @param {*} inputState The source state of the transition.
     * @param {*} outputState The target state of the transition.
     * @param {() => number} timeout The timeout in ms. Must be greater than 0.
     * @throws IllegalArgumentException If one of the given parameters is null or not valid.
     * @since 0.2
     * @class
     * @extends org.malai.interaction.TransitionImpl
     * @author Arnaud BLOUIN
     */
    export class TimeoutTransition extends org.malai.interaction.TransitionImpl {
        /**
         * The timeout in ms.
         */
        timeout : any;

        /**
         * The current thread in progress.
         */
        // /*private*/ timeoutThread : java.lang.Thread;

        public constructor(inputState : org.malai.stateMachine.SourceableState, outputState : org.malai.stateMachine.TargetableState, timeout : any) {
            super(inputState, outputState);
            if(this.timeout===undefined) this.timeout = null;
            // if(this.timeoutThread===undefined) this.timeoutThread = null;
            if(timeout == null) {
                throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            }
            this.timeout = <any>(timeout);
        }

        /**
         * Launches the timer.
         * @since 0.2
         */
        public startTimeout() {
            // if(this.timeoutThread == null) {
            //     this.timeoutThread = new java.lang.Thread(() => {
            //         let time : number = (target => (typeof target === 'function')?target():(<any>target).getAsLong())(this.timeout);
            //         if(time > 0) {
            //             try {
            //                 java.lang.Thread.sleep(time);
            //                 let sm : org.malai.stateMachine.StateMachine = this.getInputState().getStateMachine();
            //                 if(sm != null && (sm["__interfaces"] != null && sm["__interfaces"].indexOf("org.malai.interaction.Interaction") >= 0 || sm.constructor != null && sm.constructor["__interfaces"] != null && sm.constructor["__interfaces"].indexOf("org.malai.interaction.Interaction") >= 0)) {
            //                     (<org.malai.interaction.Interaction><any>sm).onTimeout(this);
            //                 }
            //             } catch(ex) {
            //             };
            //         }
            //     });
            //     this.timeoutThread.start();
            // }
        }

        /**
         * Stops the timer.
         * @since 0.2
         */
        public stopTimeout() {
            // if(this.timeoutThread != null) {
            //     this.timeoutThread.interrupt();
            //     this.timeoutThread = null;
            // }
        }

        /**
         * 
         * @return {*}
         */
        public getEventType<T>() : T {
            return null;
        }
    }
    TimeoutTransition["__class"] = "org.malai.interaction.TimeoutTransition";
    TimeoutTransition["__interfaces"] = ["org.malai.stateMachine.Transition"];


}


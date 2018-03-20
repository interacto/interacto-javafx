/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export class TimeoutTransition<E> extends Transition<E> {
        /**
         * The timeoutDuration in ms.
         */
        /*private*/ timeoutDuration : any;

        /**
         * The current thread in progress.
         */
        // /*private*/ timeoutThread : java.lang.Thread;

        /*private*/ timeouted : boolean;

        public constructor(srcState : OutputState<E>, tgtState : InputState<E>, timeout : any) {
            super(srcState, tgtState);
            if(this.timeoutDuration===undefined) this.timeoutDuration = null;
            // if(this.timeoutThread===undefined) this.timeoutThread = null;
            if(this.timeouted===undefined) this.timeouted = false;
            if(timeout == null) {
                throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            }
            this.timeoutDuration = <any>(timeout);
            this.timeouted = false;
        }

        /**
         * Launches the timer.
         */
        public startTimeout() {
            // if(this.timeoutThread == null) {
            //     this.timeoutThread = new java.lang.Thread(() => {
            //         let time : number = (target => (typeof target === 'function')?target():(<any>target).getAsLong())(this.timeoutDuration);
            //         if(time > 0) {
            //             try {
            //                 java.lang.Thread.sleep(time);
            //                 this.timeouted = true;
            //                 this.src.getFSM().onTimeout();
            //             } catch(ex) {
            //             };
            //         }
            //     });
            //     this.timeoutThread.start();
            // }
        }

        /**
         * Stops the timer.
         */
        public stopTimeout() {
            // if(this.timeoutThread != null) {
            //     this.timeoutThread.interrupt();
            //     this.timeoutThread = null;
            // }
        }

        /**
         * 
         * @param {*} event
         * @return {boolean}
         */
        accept(event : E) : boolean {
            return this.timeouted;
        }

        /**
         * 
         * @param {*} event
         * @return {boolean}
         */
        isGuardOK(event : E) : boolean {
            return this.timeouted;
        }

        /**
         * 
         * @param {*} event
         * @return {util.Optional}
         */
        public execute(event : E) : Optional<InputState<E>> {
            try {
                if(this.accept(event) && this.isGuardOK(event)) {
                    this.src.exit();
                    this.action(event);
                    this.tgt.enter();
                    this.timeouted = false;
                    return Optional.of<any>(this.tgt);
                }
                return Optional.empty<any>();
            } catch(ex) {
                this.timeouted = false;
                throw ex;
            }
        }

        /**
         * 
         * @return {*[]}
         */
        public getAcceptedEvents() : Set<String> {
            return new Set();
        }
    }
    TimeoutTransition["__class"] = "malai.TimeoutTransition";

}


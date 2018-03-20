/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export abstract class InteractionImpl<E, F extends FSM<E>> {
        // logger : java.util.logging.Logger;

        protected fsm : F;

        /**
         * Defines if the interaction is activated or not. If not, the interaction will not
         * change on events.
         */
        protected activated : boolean;

        protected constructor(fsm : F) {
            // if(this.logger===undefined) this.logger = null;
            if(this.fsm===undefined) this.fsm = null;
            if(this.activated===undefined) this.activated = false;
            if(fsm == null) {
                throw Object.defineProperty(new Error("null fsm"), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
            }
            this.fsm = fsm;
            fsm.currentStateProp().obs({ onChange : (oldValue, newValue) => this.updateEventsRegistered(newValue, oldValue) });
            this.activated = true;
        }

        protected abstract updateEventsRegistered(newState : OutputState<E>, oldState : OutputState<E>) : any;

        public isRunning() : boolean {
            return this.activated && !(this.fsm.getCurrentState() != null && this.fsm.getCurrentState() instanceof <any>InitState);
        }

        public fullReinit() {
            this.fsm.fullReinit();
        }

        public processEvent(event : E) {
            if(this.isActivated()) {
                this.fsm.process(event);
            }
        }

        public log(log : boolean) {
            // if(log) {
            //     if(this.logger == null) {
            //         this.logger = java.util.logging.Logger.getLogger(/* getName */(c => c["__class"]?c["__class"]:c["name"])((<any>this.constructor)));
            //     }
            // } else {
            //     this.logger = null;
            // }
            this.fsm.log(log);
        }

        public isActivated() : boolean {
            return this.activated;
        }

        public setActivated(activated : boolean) {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "Interaction activation: " + activated);
            // }
            this.activated = activated;
            if(!activated) {
                this.fsm.fullReinit();
            }
        }

        public getFsm() : F {
            return this.fsm;
        }

        protected  reinit() {
            this.fsm.reinit();
            this.reinitData();
        }

        protected abstract reinitData();
    }
    InteractionImpl["__class"] = "malai.InteractionImpl";

}


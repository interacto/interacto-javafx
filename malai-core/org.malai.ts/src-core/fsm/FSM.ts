/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export class FSM<E> {
        // logger : java.util.logging.Logger;

        inner : boolean;

        /**
         * By default an FSM triggers its 'start' event when it leaves its initial state.
         * In some cases, this is not the case. For example, a double-click interaction is an FSM that must trigger
         * its start event when the FSM reaches... its terminal state. Similarly, a DnD must trigger its start event
         * on the first move, not on the first press.
         * The goal of this attribute is to identify the state of the FSM that must trigger the start event.
         * By default, this attribute is set with the initial state of the FSM.
         */
        startingState : State<E>;

        /**
         * Goes with 'startingState'. It permits to know whether the FSM has started, ie whether the 'starting state' has been reached.
         */
        started : boolean;

        initState : InitState<E>;

        currentState : ObsValue<OutputState<E>>;

        /**
         * The states that compose the finite state machine.
         */
        states : Array<State<E>>;

        /**
         * The handler that want to be notified when the state machine of the interaction changed.
         */
        handlers : Array<FSMHandler>;

        /**
         * The events still in process. For example when the user press key ctrl and scroll one time using the wheel of the mouse, the interaction scrolling is
         * finished but the event keyPressed 'ctrl' is still in process. At the end of the interaction, these events are re-introduced into the
         * state machine of the interaction for processing.
         */
        eventsToProcess : Array<E>;

        /**
         * The current timeout in progress.
         */
        currentTimeout : TimeoutTransition<E>;

        currentSubFSM : FSM<E>;

        public constructor() {
            // if(this.logger===undefined) this.logger = null;
            if(this.inner===undefined) this.inner = false;
            if(this.startingState===undefined) this.startingState = null;
            if(this.started===undefined) this.started = false;
            if(this.initState===undefined) this.initState = null;
            if(this.currentState===undefined) this.currentState = null;
            if(this.states===undefined) this.states = null;
            if(this.handlers===undefined) this.handlers = null;
            if(this.eventsToProcess===undefined) this.eventsToProcess = null;
            if(this.currentTimeout===undefined) this.currentTimeout = null;
            if(this.currentSubFSM===undefined) this.currentSubFSM = null;
            this.started = false;
            this.initState = <any>(new InitState<any>(this, "init"));
            this.states = <any>([]);
            /* add */((s, e) => { if(s.indexOf(e)==-1) { s.push(e); return true; } else { return false; } })(this.states, this.initState);
            this.startingState = this.initState;
            this.currentState = <any>(new ObsValue<any>(this.initState));
            this.inner = false;
            this.handlers = <any>([]);
        }

        public getCurrentState() : OutputState<E> {
            return this.currentState.get();
        }

        public setInner(inner : boolean) {
            this.inner = inner;
        }

        public process(event : E) : boolean {
            if(event == null) {
                return false;
            }
            if(this.currentSubFSM != null) {
                return this.currentSubFSM.process(event);
            }
            return this.currentState.get().process(event);
        }

        enterStdState(state : StdState<E>) {
            this.setCurrentState(state);
            this.checkTimeoutTransition();
            if(this.started) {
                this.onUpdating();
            }
        }

        public isStarted() : boolean {
            return this.started;
        }

        setCurrentState(state : OutputState<E>) {
            this.currentState.set(state);
        }

        /**
         * At the end of the FSM execution, the events still (eg keyPress) in process must be recycled to be reused in the FSM.
         */
        processRemainingEvents() {
            //FIXME
            // if(this.eventsToProcess != null) {
            //     {
            //         let list : Array<E> = <any>(this.eventsToProcess.slice(0));
            //         while((!/* isEmpty */(list.length == 0))) {
            //             let event : E = /* remove */list.splice(0, 1);
            //             /* remove */this.eventsToProcess.splice(0, 1);
            //             // if(this.logger != null) {
            //             //     this.logger.log(java.util.logging.Level.INFO, "Recycling event: " + event);
            //             // }
            //             this.process(event);
            //         };
            //     };
            // }
        }

        addRemaningEventsToProcess(event : E) {
            if(event != null) {
                if(this.eventsToProcess == null) {
                    this.eventsToProcess = <any>([]);
                }
                {
                    /* add */(this.eventsToProcess.push(event)>0);
                };
            }
        }

        /**
         * Terminates the state machine.
         */
        onTerminating() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "FSM ended");
            // }
            if(this.started) {
                this.notifyHandlerOnStop();
            }
            this.reinit();
            this.processRemainingEvents();
        }

        /**
         * Cancels the state machine.
         */
        onCancelling() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "FSM cancelled");
            // }
            if(this.started) {
                this.notifyHandlerOnCancel();
            }
            this.fullReinit();
        }

        /**
         * Starts the state machine.
         */
        public onStarting() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "FSM started");
            // }
            this.started = true;
            this.notifyHandlerOnStart();
        }

        /**
         * Updates the state machine.
         */
        public onUpdating() {
            if(this.started) {
                // if(this.logger != null) {
                //     this.logger.log(java.util.logging.Level.INFO, "FSM updated");
                // }
                this.notifyHandlerOnUpdate();
            }
        }

        /**
         * Adds a state to the state machine.
         * @param {*} state The state to add. Must not be null.
         */
        addState(state : InputState<E>) {
            if(state != null) {
                /* add */((s, e) => { if(s.indexOf(e)==-1) { s.push(e); return true; } else { return false; } })(this.states, state);
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
        }

        public reinit() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "FSM reinitialised");
            // }
            if(this.currentTimeout != null) {
                this.currentTimeout.stopTimeout();
            }
            this.started = false;
            this.currentState.set(this.initState);
            this.currentTimeout = null;
            if(this.currentSubFSM != null) {
                this.currentSubFSM.reinit();
            }
        }

        public fullReinit() {
            if(this.eventsToProcess != null) {
                {
                    /* clear */(this.eventsToProcess.length = 0);
                };
            }
            this.reinit();
            if(this.currentSubFSM != null) {
                this.currentSubFSM.fullReinit();
            }
        }

        onTimeout() {
            // if(this.currentTimeout != null) {
            //     // if(this.logger != null) {
            //     //     this.logger.log(java.util.logging.Level.INFO, "Timeout");
            //     // }
            //     try {
            //         this.currentTimeout.execute(null).filter((state) => (state != null && (state["__interfaces"] != null && state["__interfaces"].indexOf("OutputState") >= 0 || state.constructor != null && state.constructor["__interfaces"] != null && state.constructor["__interfaces"].indexOf("OutputState") >= 0))).ifPresent((state) => {
            //             this.currentState.set(<OutputState<E>><any>state);
            //             this.checkTimeoutTransition();
            //         });
            //     } catch(ignored) {
            //     };
            // }
        }

        /**
         * Stops the current timeout transition.
         */
        stopCurrentTimeout() {
            if(this.currentTimeout != null) {
                // if(this.logger != null) {
                //     this.logger.log(java.util.logging.Level.INFO, "Timeout stopped");
                // }
                this.currentTimeout.stopTimeout();
                this.currentTimeout = null;
            }
        }

        /**
         * Checks whether the current state has a timeout transition.
         * If it is the case, the timeout transition is launched.
         */
        checkTimeoutTransition() {
            //FIXME
            // this.currentState.get().getTransitions().filter((tr) => (tr != null && tr instanceof <any>TimeoutTransition)).findFirst().map<any>((tr) => <TimeoutTransition<E>>tr).ifPresent((tr) => {
            //     // if(this.logger != null) {
            //     //     this.logger.log(java.util.logging.Level.INFO, "Timeout starting");
            //     // }
            //     this.currentTimeout = tr;
            //     this.currentTimeout.startTimeout();
            // });
        }

        public addHandler(handler : FSMHandler) {
            if(handler != null) {
                /* add */((s, e) => { if(s.indexOf(e)==-1) { s.push(e); return true; } else { return false; } })(this.handlers, handler);
            }
        }

        public removeHandler(handler : FSMHandler) {
            if(handler != null) {
                /* remove */(a => { let index = a.indexOf(handler); if(index>=0) { a.splice(index, 1); return true; } else { return false; }})(this.handlers);
            }
        }

        /**
         * Notifies handler that the interaction starts.
         */
        notifyHandlerOnStart() {
            try {
                for(let index125=0; index125 < this.handlers.length; index125++) {
                    let handler = this.handlers[index125];
                    {
                        handler.fsmStarts();
                    }
                }
            } catch(ex) {
                this.onCancelling();
                throw ex;
            };
        }

        /**
         * Notifies handler that the interaction updates.
         */
        notifyHandlerOnUpdate() {
            try {
                for(let index126=0; index126 < this.handlers.length; index126++) {
                    let handler = this.handlers[index126];
                    {
                        handler.fsmUpdates();
                    }
                }
            } catch(ex) {
                this.onCancelling();
                throw ex;
            };
        }

        /**
         * Notifies handler that the interaction stops.
         */
        notifyHandlerOnStop() {
            try {
                for(let index127=0; index127 < this.handlers.length; index127++) {
                    let handler = this.handlers[index127];
                    {
                        handler.fsmStops();
                    }
                }
            } catch(ex) {
                this.onCancelling();
                throw ex;
            };
        }

        /**
         * Notifies handler that the interaction is cancelled.
         */
        notifyHandlerOnCancel() {
            this.handlers.forEach((handler) => handler.fsmCancels());
        }

        public getStates() : Array<State<E>> {
            return /* unmodifiableSet */this.states.slice(0);
        }

        public currentStateProp() : ObsValue<OutputState<E>> {
            return this.currentState;
        }
    }
    FSM["__class"] = "malai.FSM";

}


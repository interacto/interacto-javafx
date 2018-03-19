/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.interaction {
    /**
     * Creates the state machine.
     * @param {org.malai.interaction.InitState} initState The initial state of the state machine.
     * @throws IllegalArgumentException If the given state is null.
     * @since 0.1
     * @class
     * @author Arnaud BLOUIN
     */
    export abstract class InteractionImpl implements org.malai.interaction.Interaction {
        // logger : java.util.logging.Logger;

        /**
         * The states that compose the finite state machine.
         */
        states : Array<org.malai.stateMachine.State>;

        /**
         * The initial state the starts the state machine.
         */
        initState : org.malai.interaction.InitState;

        /**
         * The current state of the state machine when the state machine is executed.
         */
        currentState : org.malai.stateMachine.State;

        /**
         * Defines if the interaction is activated or not. If not, the interaction will not
         * change on events.
         */
        activated : boolean;

        /**
         * The handler that want to be notified when the state machine of the interaction changed.
         */
        handlers : Array<org.malai.interaction.InteractionHandler>;

        /**
         * The events still in process. For example when the user press key ctrl and scroll one time using the wheel of the mouse, the interaction scrolling is
         * finished but the event keyPressed 'ctrl' is still in process. At the end of the interaction, these events are re-introduced into the
         * state machine of the interaction for processing.
         */
        stillProcessingEvents : Array<InteractionImpl.Event>;

        /**
         * The current timeout in progress.
         */
        currentTimeout : org.malai.interaction.TimeoutTransition;

        /**
         * Defines the ID of last HID that has been used by the interaction. If the interaction has stopped or is aborted, the value of the attribute is -1.
         */
        lastHIDUsed : number;

        public constructor(initState? : any) {
            if(((initState != null && initState instanceof <any>org.malai.interaction.InitState) || initState === null)) {
                let __args = Array.prototype.slice.call(arguments);
                // if(this.logger===undefined) this.logger = null;
                if(this.states===undefined) this.states = null;
                if(this.initState===undefined) this.initState = null;
                if(this.currentState===undefined) this.currentState = null;
                if(this.activated===undefined) this.activated = false;
                if(this.handlers===undefined) this.handlers = null;
                if(this.stillProcessingEvents===undefined) this.stillProcessingEvents = null;
                if(this.currentTimeout===undefined) this.currentTimeout = null;
                if(this.lastHIDUsed===undefined) this.lastHIDUsed = 0;
                // if(this.logger===undefined) this.logger = null;
                if(this.states===undefined) this.states = null;
                if(this.initState===undefined) this.initState = null;
                if(this.currentState===undefined) this.currentState = null;
                if(this.activated===undefined) this.activated = false;
                if(this.handlers===undefined) this.handlers = null;
                if(this.stillProcessingEvents===undefined) this.stillProcessingEvents = null;
                if(this.currentTimeout===undefined) this.currentTimeout = null;
                if(this.lastHIDUsed===undefined) this.lastHIDUsed = 0;
                (() => {
                    if(initState == null) {
                        throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
                    }
                    this.currentTimeout = null;
                    this.activated = true;
                    this.states = <any>([]);
                    initState.stateMachine = this;
                    this.initState = initState;
                    this.addState(initState);
                    this.currentState = initState;
                    this.lastHIDUsed = -1;
                })();
            } else if(initState === undefined) {
                let __args = Array.prototype.slice.call(arguments);
                {
                    let __args = Array.prototype.slice.call(arguments);
                    let initState : any = new org.malai.interaction.InitState();
                    // if(this.logger===undefined) this.logger = null;
                    if(this.states===undefined) this.states = null;
                    if(this.initState===undefined) this.initState = null;
                    if(this.currentState===undefined) this.currentState = null;
                    if(this.activated===undefined) this.activated = false;
                    if(this.handlers===undefined) this.handlers = null;
                    if(this.stillProcessingEvents===undefined) this.stillProcessingEvents = null;
                    if(this.currentTimeout===undefined) this.currentTimeout = null;
                    if(this.lastHIDUsed===undefined) this.lastHIDUsed = 0;
                    // if(this.logger===undefined) this.logger = null;
                    if(this.states===undefined) this.states = null;
                    if(this.initState===undefined) this.initState = null;
                    if(this.currentState===undefined) this.currentState = null;
                    if(this.activated===undefined) this.activated = false;
                    if(this.handlers===undefined) this.handlers = null;
                    if(this.stillProcessingEvents===undefined) this.stillProcessingEvents = null;
                    if(this.currentTimeout===undefined) this.currentTimeout = null;
                    if(this.lastHIDUsed===undefined) this.lastHIDUsed = 0;
                    (() => {
                        if(initState == null) {
                            throw Object.defineProperty(new Error(), '__classes', { configurable: true, value: ['java.lang.Throwable','java.lang.Object','java.lang.RuntimeException','java.lang.IllegalArgumentException','java.lang.Exception'] });
                        }
                        this.currentTimeout = null;
                        this.activated = true;
                        this.states = <any>([]);
                        initState.stateMachine = this;
                        this.initState = initState;
                        this.addState(initState);
                        this.currentState = initState;
                        this.lastHIDUsed = -1;
                    })();
                }
            } else throw new Error('invalid overload');
        }

        /**
         * 
         * @param {boolean} log
         */
        public log(log : boolean) {
            // if(log) {
            //     if(this.logger == null) {
            //         this.logger = java.util.logging.Logger.getLogger(/* getName */(c => c["__class"]?c["__class"]:c["name"])((<any>this.constructor)));
            //     }
            // } else {
            //     this.logger = null;
            // }
        }

        setCurrentState(state : org.malai.stateMachine.State) {
            let oldState : org.malai.stateMachine.State = this.currentState;
            this.currentState = state;
            this.changeEventsRegistered(oldState);
        }

        abstract changeEventsRegistered(oldState : org.malai.stateMachine.State);

        /**
         * Initialises the interaction: creates the states and the transitions.
         * @since 0.1
         */
        abstract initStateMachine();

        /**
         * 
         * @param {boolean} activated
         */
        public setActivated(activated : boolean) {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "Interaction activation: " + activated);
            // }
            this.activated = activated;
            if(!activated) {
                this.reinit();
                this.clearEventsStillInProcess();
            }
        }

        /**
         * 
         * @return {boolean}
         */
        public isActivated() : boolean {
            return this.activated;
        }

        /**
         * 
         * @return {*}
         */
        public getCurrentState() : org.malai.stateMachine.State {
            return this.currentState;
        }

        /**
         * 
         */
        public reinit() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "Interaction reinit");
            // }
            if(this.currentTimeout != null) {
                this.currentTimeout.stopTimeout();
            }
            this.currentTimeout = null;
            this.setCurrentState(this.initState);
            this.lastHIDUsed = -1;
        }

        /**
         * 
         * @return {*[]}
         */
        public getHandlers() : Array<org.malai.interaction.InteractionHandler> {
            return this.handlers;
        }

        /**
         * 
         * @param {*} handler
         */
        public addHandler(handler : org.malai.interaction.InteractionHandler) {
            if(handler != null) {
                if(this.handlers == null) {
                    this.handlers = <any>([]);
                }
                /* add */(this.handlers.push(handler)>0);
            }
        }

        /**
         * Notifies handler that the interaction starts.
         */
        notifyHandlersOnStart() {
            try {
                if(this.handlers != null) {
                    for(let index131=0; index131 < this.handlers.length; index131++) {
                        let handler = this.handlers[index131];
                        {
                            handler.interactionStarts();
                        }
                    }
                }
            } catch(ex) {
                this.notifyHandlersOnCancel();
                throw ex;
            };
        }

        /**
         * Notifies handler that the interaction updates.
         */
        notifyHandlersOnUpdate() {
            try {
                if(this.handlers != null) {
                    for(let index132=0; index132 < this.handlers.length; index132++) {
                        let handler = this.handlers[index132];
                        {
                            handler.interactionUpdates();
                        }
                    }
                }
            } catch(ex) {
                this.notifyHandlersOnCancel();
                throw ex;
            };
        }

        /**
         * Notifies handler that the interaction stops.
         */
        notifyHandlersOnStop() {
            try {
                if(this.handlers != null) {
                    for(let index133=0; index133 < this.handlers.length; index133++) {
                        let handler = this.handlers[index133];
                        {
                            handler.interactionStops();
                        }
                    }
                }
            } catch(ex) {
                this.notifyHandlersOnCancel();
                throw ex;
            };
        }

        /**
         * Notifies handler that the interaction is cancelled.
         */
        notifyHandlersOnCancel() {
            if(this.handlers != null) {
                this.handlers.forEach((handler) => handler.interactionCancels());
            }
        }

        /**
         * 
         * @param {*} state
         */
        public addState(state : org.malai.stateMachine.State) {
            if(state != null) {
                /* add */((s, e) => { if(s.indexOf(e)==-1) { s.push(e); return true; } else { return false; } })(this.states, state);
                state.setStateMachine(this);
            }
        }

        /**
         * 
         * @return {boolean}
         */
        public isRunning() : boolean {
            return this.activated && this.currentState !== this.initState;
        }

        /**
         * Executes the given transition. Only if the state machine is activated.
         * @param {*} transition The transition to execute.
         */
        executeTransition(transition : org.malai.stateMachine.Transition) {
            if(this.activated && transition != null) {
                // if(this.logger != null) {
                //     this.logger.log(java.util.logging.Level.INFO, "Transition exec: " + transition.getInputState().getName() + "->" + transition.getOutputState().getName());
                // }
                try {
                    transition.action();
                    transition.getInputState().onOutgoing();
                    this.setCurrentState(transition.getOutputState());
                    transition.getOutputState().onIngoing();
                } catch(ex) {
                    this.reinit();
                };
            }
        }

        /**
         * Stops the current timeout transition.
         * @since 0.2
         */
        stopCurrentTimeout() {
            if(this.currentTimeout != null) {
                // if(this.logger != null) {
                //     this.logger.log(java.util.logging.Level.INFO, "Timeout done");
                // }
                this.currentTimeout.stopTimeout();
                this.currentTimeout = null;
            }
        }

        /**
         * 
         * @param {*} transition
         * @return {boolean}
         */
        public checkTransition(transition : org.malai.stateMachine.Transition) : boolean {
            let ok : boolean;
            if(transition != null && /* contains */(this.states.indexOf(<any>(transition.getInputState())) >= 0) && transition.isGuardRespected()) {
                this.stopCurrentTimeout();
                this.executeTransition(transition);
                ok = true;
            } else {
                ok = false;
            }
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "Check transition: " + ok);
            // }
            return ok;
        }

        /**
         * 
         * @param {org.malai.interaction.TimeoutTransition} timeoutTransition
         */
        public onTimeout(timeoutTransition : org.malai.interaction.TimeoutTransition) {
            if(this.activated && timeoutTransition != null) {
                this.executeTransition(timeoutTransition);
            }
        }

        /**
         * At the end of the interaction, the events still in process must be recycled
         * to be reused in the interaction. For instance will the KeysScrolling interaction,
         * if key 'ctrl' is pressed and the user scrolls the key event 'ctrl' is re-introduced
         * into the state machine of the interaction to be processed.
         * @since 0.2
         */
        processEvents() {
            //FIXME
            // if(this.stillProcessingEvents != null) {
            //     {
            //         let event : InteractionImpl.Event;
            //         let list : Array<InteractionImpl.Event> = <any>(this.stillProcessingEvents.slice(0));
            //         while((!/* isEmpty */(list.length == 0))) {
            //             event = /* remove */list.splice(0, 1);
            //             /* remove */this.stillProcessingEvents.splice(0, 1);
            //             // if(this.logger != null) {
            //             //     this.logger.log(java.util.logging.Level.INFO, "Recycling event: " + event);
            //             // }
            //             this.processEvent(event);
            //         };
            //     };
            // }
        }

        /**
         * At the end of the interaction, the events still in process must be recycled
         * to be reused in the interaction. For instance will the KeysScrolling interaction,
         * if key 'ctrl' is pressed and the user scrolls the key event 'ctrl' is re-introduced
         * into the state machine of the interaction to be processed.
         * Companion operation of processEvents. Must be implemented by the different GUI libraries.
         * @param {org.malai.interaction.InteractionImpl.Event} event
         */
        abstract processEvent(event : InteractionImpl.Event);

        /**
         * 
         */
        public onTerminating() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "Interaction ends");
            // }
            this.notifyHandlersOnStop();
            this.reinit();
            this.processEvents();
        }

        /**
         * 
         */
        public onCancelling() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "Interaction cancels");
            // }
            this.notifyHandlersOnCancel();
            this.reinit();
            this.clearEventsStillInProcess();
        }

        /**
         * 
         */
        public onStarting() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "Interaction starts");
            // }
            this.notifyHandlersOnStart();
            this.checkTimeoutTransition();
        }

        /**
         * 
         */
        public onUpdating() {
            // if(this.logger != null) {
            //     this.logger.log(java.util.logging.Level.INFO, "Interaction updates");
            // }
            this.notifyHandlersOnUpdate();
            this.checkTimeoutTransition();
        }

        /**
         * Checks if the current state has a timeout transition. If it is the case, the timeout transition is launched.
         */
        checkTimeoutTransition() {
            //FIXME
            // let timeout : util.Optional<org.malai.interaction.TimeoutTransition> = this.currentState.getTransitions().stream().filter((tr) => (tr != null && tr instanceof <any>org.malai.interaction.TimeoutTransition)).findFirst().map<any>((tr) => <org.malai.interaction.TimeoutTransition><any>tr);
            // if(timeout.isPresent()) {
            //     this.currentTimeout = timeout.get();
            //     this.currentTimeout.startTimeout();
            // }
        }

        /**
         * 
         * @return {number}
         */
        public getLastHIDUsed() : number {
            return this.lastHIDUsed;
        }

        /**
         * 
         * @param {number} hid
         */
        public setLastHIDUsed(hid : number) {
            this.lastHIDUsed = hid;
        }

        /**
         * 
         */
        public clearEventsStillInProcess() {
            if(this.stillProcessingEvents != null) {
                {
                    /* clear */(this.stillProcessingEvents.length = 0);
                };
            }
        }

        /**
         * 
         */
        public clearEvents() {
            this.reinit();
            this.clearEventsStillInProcess();
        }
    }
    InteractionImpl["__class"] = "org.malai.interaction.InteractionImpl";
    InteractionImpl["__interfaces"] = ["org.malai.interaction.Interaction","org.malai.stateMachine.StateMachine","org.malai.interaction.EventProcessor"];



    export namespace InteractionImpl {

        /**
         * Creates the event.
         * @param {number} idHID The identifier of the HID.
         * @since 0.2
         * @class
         */
        export abstract class Event {
            /**
             * The identifier of the HID.
             */
            idHID : number;

            public constructor(idHID : number) {
                if(this.idHID===undefined) this.idHID = 0;
                this.idHID = idHID;
            }

            /**
             * @return {number} The ID of the HID used.
             */
            public getIdHID() : number {
                return this.idHID;
            }
        }
        Event["__class"] = "org.malai.interaction.InteractionImpl.Event";

    }

}


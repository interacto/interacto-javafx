/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
namespace malai {
    /**
     * Base implementation of a FSM-based user interaction.
     * @author Arnaud BLOUIN
     */
    export abstract class InteractionImpl implements Interaction {
        /**
         * The states that compose the finite state machine.
         */
        protected readonly states : State[];

        /**
         * The initial state the starts the state machine.
         */
        protected readonly initState : InitState;

        /**
         * The current state of the state machine when the state machine is executed.
         */
        protected currentState : State;

        /**
         * Defines if the interaction is activated or not. If not, the interaction will not
         * change on events.
         */
        protected activated : boolean;

        /**
         * The handlers that want to be notified when the state machine of the
         * interaction changed.
         */
        protected readonly handlers : InteractionHandler[];

        /**
         * The events still in process. For example when the user press key ctrl and scroll one
         * time using the wheel of the mouse, the interaction scrolling is finished but the event keyPressed
         * 'ctrl' is still in process. At the end of the interaction, these events are re-introduced into the
         * state machine of the interaction for processing.
         */
        protected readonly stillProcessingEvents : InteractionImpl.Event[];

        /**
         * The current timeout in progress.
         */
        protected currentTimeout? : TimeoutTransition;

        /**
         * Creates the state machine.
         * @param initState The initial state of the state machine.
         */
        public constructor(initState : InitState) {
            this.currentTimeout = undefined;
            this.activated = true;
            this.states = [];
            this.stillProcessingEvents = [];
            this.initState.setInteraction(this);
            this.initState = initState;
            this.addState(initState);
            this.reinit();
        }

        /**
         * Initialises the interaction: creates the states and the transitions.
         */
        protected abstract initStateMachine() : void;

        public setActivated(activated : boolean) : void {
            this.activated = activated;
            if(!activated) {
                this.reinit();
                this.clearEventsStillInProcess();
            }
        }

        public isActivated() : boolean {
            return this.activated;
        }

        public getCurrentState() : State {
            return this.currentState;
        }

        public reinit() : void {
            if(this.currentTimeout != null) {
                this.currentTimeout.stopTimeout();
            }
            this.currentTimeout = undefined;
            this.currentState = this.initState;
        }

        public getHandlers() : InteractionHandler[] {
            return this.handlers;
        }

        public addHandler(handler : InteractionHandler) : void {
            if(handler != null) {
                this.handlers.push(handler);
            }
        }

        /**
         * Notifies handlers that the interaction starts.
         */
        protected notifyHandlersOnStart() : void {
            try {
                this.handlers.forEach(handler =>  handler.interactionStarts(this));
            } catch(ex) {
                this.notifyHandlersOnAborting();
                throw ex;
            }
        }

        /**
         * Notifies handlers that the interaction updates.
         */
        protected notifyHandlersOnUpdate() : void {
            try {
                this.handlers.forEach(handler =>  handler.interactionUpdates(this));
            } catch(ex) {
                this.notifyHandlersOnAborting();
                throw ex;
            }
        }

        /**
         * Notifies handlers that the interaction stops.
         */
        protected notifyHandlersOnStop() : void {
            try {
                this.handlers.forEach(handler =>  handler.interactionStops(this));
            } catch(ex) {
                this.notifyHandlersOnAborting();
                throw ex;
            }
        }

        /**
         * Notifies handlers that the interaction stops.
         */
        protected notifyHandlersOnAborting() : void {
            this.handlers.forEach(handler =>  handler.interactionAborts(this));
        }

        public addState(state : State) : void {
            if(state != null) {
                this.states.push(state);
                state.setInteraction(this);
            }
        }

        public isRunning() : boolean {
            return this.activated && this.currentState !== this.initState;
        }

        /**
         * Executes the given transition. Only if the state machine is activated.
         * @param transition The transition to execute.
         */
        protected executeTransition(transition : Transition) : void {
            if(this.activated && transition != null) {
                try {
                    transition.action();
                    transition.getInputState().onOutgoing();
                    this.currentState = transition.getOutputState();
                    transition.getOutputState().onIngoing();
                } catch(ex) {
                    this.reinit();
                }
            }
        }

        /**
         * Stops the current timeout transition.
         */
        protected stopCurrentTimeout() : void {
            if(this.currentTimeout != null) {
                this.currentTimeout.stopTimeout();
                this.currentTimeout = undefined;
            }
        }

        public checkTransition(transition : Transition) : boolean {
            let ok : boolean;

            if(transition != null && transition.isGuardRespected()) {
                this.stopCurrentTimeout();
                this.executeTransition(transition);
                ok = true;
            } else {
                ok = false;
            }

            return ok;
        }

        public onTimeout(timeoutTransition : TimeoutTransition) : void {
            if(this.activated && timeoutTransition != null) {
                this.executeTransition(timeoutTransition);
            }
        }

        /**
         * At the end of the interaction, the events still in process must be recycled
         * to be reused in the interaction. For instance will the KeysScrolling interaction,
         * if key 'ctrl' is pressed and the user scrolls the key event 'ctrl' is re-introduced
         * into the state machine of the interaction to be processed.
         */
        protected processEvents() : void {
            while(this.stillProcessingEvents.length > 0) {
                this.processEvent(this.stillProcessingEvents.splice(0, 1)[0]);
            }
        }

        /**
         * At the end of the interaction, the events still in process must be recycled
         * to be reused in the interaction. For instance will the KeysScrolling interaction,
         * if key 'ctrl' is pressed and the user scrolls the key event 'ctrl' is re-introduced
         * into the state machine of the interaction to be processed.
         * Companion operation of processEvents. Must be implemented by the different GUI libraries.
         */
        protected abstract processEvent(event : InteractionImpl.Event) : void;

        public onTerminating() : void {
            this.notifyHandlersOnStop();
            this.reinit();
            this.processEvents();
        }

        public onAborting() : void {
            this.notifyHandlersOnAborting();
            this.reinit();
            this.clearEventsStillInProcess();
        }

        public onStarting() : void {
            this.notifyHandlersOnStart();
            this.checkTimeoutTransition();
        }

        public onUpdating() : void {
            this.notifyHandlersOnUpdate();
            this.checkTimeoutTransition();
        }

        /**
         * Checks if the current state has a timeout transition. If it is the case, the timeout transition is launched.
         */
        protected checkTimeoutTransition() : void {
            let again : boolean = true;

            for(let i : number = 0, j : number = this.currentState.getTransitions().length; i < j && again; i++) {
                let transition : Transition | undefined = this.currentState.getTransition(i);

                if(transition instanceof TimeoutTransition) {
                    this.currentTimeout = transition as TimeoutTransition;
                    again = false;
                    this.currentTimeout.startTimeout();
                }
            }
        }

        public clearEventsStillInProcess() : void {
            if(this.stillProcessingEvents != null) {
                this.stillProcessingEvents.length = 0;
            }
        }

        public clearEvents() : void {
            this.reinit();
            this.clearEventsStillInProcess();
        }
    }


    export namespace InteractionImpl {
        export interface Event {
        }
    }
}


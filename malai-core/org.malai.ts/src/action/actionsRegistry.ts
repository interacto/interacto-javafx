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
    import UndoCollector = malai.UndoCollector;

    import Undoable = malai.Undoable;

    /**
     * A register of actions.
     * This is a singleton.
     * The register has a limited size that can be changed.
     * It can notify handlers about changes in the registry.
     * @author Arnaud Blouin
     */
    export class ActionsRegistry {
        /**
         * The singleton.
         */
        public static readonly INSTANCE : ActionsRegistry = new ActionsRegistry();

        /**
         * The saved actions.
         */
        private readonly actions : Action[];

        /**
         * The actions handlers.
         */
        private readonly handlers : ActionHandler[];

        /**
         * The max number of actions that can contain the register.
         */
        private sizeMax : number;

        /**
         * Creates and initialises a register.
         */
        constructor() {
            this.sizeMax = 0;
            this.actions = [];
            this.handlers = [];
            this.sizeMax = 30;
        }

        /**
         * Notifies handlers that an action has been executed.
         * @param action The executed action.
         */
        public onActionExecuted(action : Action) : void {
            if(action != null) {
                this.handlers.forEach(handler => handler.onActionExecuted(action));
            }
        }

        /**
         * Notifies handlers that an action ends.
         * @param action The ending action.
         */
        public onActionDone(action : Action) : void {
            if(action != null) {
                this.handlers.forEach(handler => handler.onActionDone(action));
            }
        }

        /**
         * @return The stored actions. Cannot be null. Because of concurrency, you should not modify this list.
         */
        public getActions() : Action[] {
            return this.actions;
        }

        /**
         * Removes and flushes the actions from the register that use the given action type.
         * @see Action::unregisteredBy
         * @param action The action that may cancels others.
         */
        public unregisterActions(action : Action) : void {
            if(action == null) return;

            let i : number = 0;

            while(i < this.actions.length){
                if(this.actions[i].unregisteredBy(action)) {
                    let act : Action = this.actions[i];
                    this.actions.splice(i, 1);
                    this.handlers.forEach(handler => handler.onActionCancelled(act));
                    act.flush();
                } else {
                    i++;
                }
            }
        }

        /**
         * Adds an action to the register. Before being added, the given action is used to cancel actions
         * already added. Handlers are notified of the add of the given action. If Undoable, the action is
         * added to the undo collector as well.
         * @param action The action to add. If null, nothing is done.
         * @param actionHandler The handler that produced or is associated to the action. If null, nothing is done.
         */
        public addAction(action : Action, actionHandler : ActionHandler) : void {
            if(action != null && actionHandler != null && this.actions.indexOf(action) === -1 && this.sizeMax > 0) {
                this.unregisterActions(action);
                if(this.actions.length === this.sizeMax) {
                    this.actions[0].flush();
                    this.actions.splice(0, 1);
                }

                this.actions.push(action);

                this.handlers.forEach(handler => handler.onActionAdded(action));

                if(isUndoableInstance(action)) {
                    UndoCollector.INSTANCE.add(action as any as Undoable, actionHandler);
                }
            }
        }

        /**
         * Removes the action from the register. The action is then flushed.
         * @param action The action to remove.
         */
        public removeAction(action : Action) : void {
            if(action == null) return;
            let index = this.actions.indexOf(action);
            if(index !== -1) {
                this.actions.splice(index, 1);
                action.flush();
            }
        }

        /**
         * Adds an action handler.
         * @param handler The handler to add.
         */
        public addHandler(handler : ActionHandler) : void {
            if(handler != null) {
                this.handlers.push(handler);
            }
        }

        /**
         * Removes the given handler.
         * @param handler The handler to remove.
         */
        public removeHandler(handler : ActionHandler) {
            if(handler != null) {
                let index = this.handlers.indexOf(handler);
                if(index !== -1) {
                    this.handlers.splice(index, 1);
                }
            }
        }

        /**
         * Removes all the action handlers.
         */
        public removeAllHandlers() : void {
            this.handlers.length = 0;
        }

        /**
         * Flushes and removes all the stored actions.
         */
        public clear() {
            this.actions.forEach(action => action.flush());
            this.actions.length = 0;
        }

        /**
         * Aborts the given action, i.e. the action is aborted and removed from the register.
         * Handlers are then notified. The action is finally flushed.
         * @param action The action to abort.
         */
        public abortAction(action : Action) {
            if(action != null) {
                action.abort();
                let index = this.actions.indexOf(action);
                if(index !== -1) {
                    this.actions.splice(index, 1);
                    this.handlers.forEach(handler => handler.onActionAborted(action));
                }
                action.flush();
            }
        }

        /**
         * @return The maximal number of actions that the register can contain.
         */
        public getSizeMax() : number {
            return this.sizeMax;
        }

        /**
         * Changes the number of actions that the register can contain.
         * In the case that actions have to be removed (because the new size is smaller than the old one),
         * the necessary number of the oldest actions are flushed and removed from the register.
         * @param newSizeMax The max number of actions that can contain the register. Must be equal or greater than 0.
         */
        public setSizeMax(newSizeMax : number) : void {
            if(newSizeMax >= 0) {
                if(newSizeMax < this.sizeMax) {
                    let removed: Action[] = this.actions.splice(0, this.actions.length - newSizeMax);
                    removed.forEach(action => action.flush());
                }
                this.sizeMax = newSizeMax;
            }
        }
    }
}

/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action {
    /**
     * A register of actions.
     * This is a singleton. It automatically collects the executed actions when the action is executed by an instrument.
     * The register has a limited size that can be changed.
     * It can notify handler about changes in the registry.
     * @author Arnaud Blouin
     * @class
     */
    export class ActionsRegistry {
        /**
         * The singleton.
         */
        public static INSTANCE : ActionsRegistry; public static INSTANCE_$LI$() : ActionsRegistry { if(ActionsRegistry.INSTANCE == null) ActionsRegistry.INSTANCE = new ActionsRegistry(); return ActionsRegistry.INSTANCE; };

        /**
         * The saved actions.
         */
        /*private*/ actions : Array<org.malai.action.Action>;

        /**
         * The actions handler.
         */
        /*private*/ handlers : Array<org.malai.action.ActionHandler>;

        /**
         * The max number of cleanable actions (cf. Action::getRegistrationPolicy) that can contain the register.
         */
        /*private*/ sizeMax : number;

        constructor() {
            if(this.actions===undefined) this.actions = null;
            if(this.handlers===undefined) this.handlers = null;
            if(this.sizeMax===undefined) this.sizeMax = 0;
            this.actions = <any>([]);
            this.handlers = <any>([]);
            this.sizeMax = 50;
        }

        public getHandlers() : Array<org.malai.action.ActionHandler> {
            return /* unmodifiableList */this.handlers.slice(0);
        }

        /**
         * Notifies handler that an action has been executed.
         * @param {*} action The executed action.
         */
        public onActionExecuted(action : org.malai.action.Action) {
            if(action != null) {
                {
                    this.handlers.forEach((handler) => handler.onActionExecuted(action));
                };
            }
        }

        /**
         * Notifies handler that an action ends.
         * @param {*} action The ending action.
         */
        public onActionDone(action : org.malai.action.Action) {
            if(action != null) {
                {
                    this.handlers.forEach((handler) => handler.onActionDone(action));
                };
            }
        }

        /**
         * @return {*[]} The stored actions. Cannot be null. Because of concurrency, you should not modify this list.
         */
        public getActions() : Array<org.malai.action.Action> {
            return this.actions;
        }

        /**
         * Removes and flushes the actions from the register that use the given action type.
         * @see Action::unregisteredBy
         * @param {*} action The action that may cancels others.
         */
        public unregisterActions(action : org.malai.action.Action) {
            //FIXME
            // if(action == null) return;
            // let i : number = 0;
            // {
            //     while((i < /* size */(<number>this.actions.length))) {
            //         if(/* get */this.actions[i].unregisteredBy(action)) {
            //             /* remove */this.actions.splice(i, 1).flush();
            //         } else {
            //             i++;
            //         }
            //     };
            // };
        }

        /**
         * Adds an action to the register. Before being added, the given action is used to cancel actions
         * already added. Handlers are notified of the add of the given action. If Undoable, the action is
         * added to the undo collector as well.
         * @param {*} action The action to add. If null, nothing is done.
         * @param {*} actionHandler The handler that produced or is associated to the action. If null, nothing is done.
         */
        public addAction(action : org.malai.action.Action, actionHandler : org.malai.action.ActionHandler) {
            //FIXME
            // {
            //     if(action != null && actionHandler != null && !/* contains */(this.actions.indexOf(<any>(action)) >= 0) && (this.sizeMax > 0 || action.getRegistrationPolicy() === org.malai.action.Action.RegistrationPolicy.UNLIMITED)) {
            //         this.unregisterActions(action);
            //         if(/* size */(<number>this.actions.length) >= this.sizeMax) {
            //             this.actions.stream().filter((act) => act.getRegistrationPolicy() !== org.malai.action.Action.RegistrationPolicy.UNLIMITED).findFirst().ifPresent((act) => {
            //                 /* remove */(a => { let index = a.indexOf(act); if(index>=0) { a.splice(index, 1); return true; } else { return false; }})(this.actions);
            //                 act.flush();
            //             });
            //         }
            //         /* add */(this.actions.push(action)>0);
            //         {
            //             this.handlers.forEach((handler) => handler.onActionAdded(action));
            //         };
            //         if(action != null && (action["__interfaces"] != null && action["__interfaces"].indexOf("org.malai.undo.Undoable") >= 0 || action.constructor != null && action.constructor["__interfaces"] != null && action.constructor["__interfaces"].indexOf("org.malai.undo.Undoable") >= 0)) {
            //             org.malai.undo.UndoCollector.INSTANCE_$LI$().add(<org.malai.undo.Undoable><any>action, actionHandler);
            //         }
            //     }
            // };
        }

        /**
         * Removes the action from the register. The action is then flushed.
         * @param {*} action The action to remove.
         */
        public removeAction(action : org.malai.action.Action) {
            if(action != null) {
                {
                    /* remove */(a => { let index = a.indexOf(action); if(index>=0) { a.splice(index, 1); return true; } else { return false; }})(this.actions);
                };
                action.flush();
            }
        }

        /**
         * Adds an action handler.
         * @param {*} handler The handler to add.
         */
        public addHandler(handler : org.malai.action.ActionHandler) {
            if(handler != null) {
                {
                    /* add */(this.handlers.push(handler)>0);
                };
            }
        }

        /**
         * Removes the given handler.
         * @param {*} handler The handler to remove.
         */
        public removeHandler(handler : org.malai.action.ActionHandler) {
            if(handler != null) {
                {
                    /* remove */(a => { let index = a.indexOf(handler); if(index>=0) { a.splice(index, 1); return true; } else { return false; }})(this.handlers);
                };
            }
        }

        /**
         * Removes all the action handler.
         */
        public removeAllHandlers() {
            {
                /* clear */(this.handlers.length = 0);
            };
        }

        /**
         * Flushes and removes all the stored actions.
         */
        public clear() {
            {
                this.actions.forEach((action) => action.flush());
                /* clear */(this.actions.length = 0);
            };
        }

        /**
         * Aborts the given action, i.e. the action is cancelled and removed from the register.
         * Handlers are then notified. The action is finally flushed.
         * @param {*} action The action to cancel.
         */
        public cancelAction(action : org.malai.action.Action) {
            if(action != null) {
                action.cancel();
                {
                    /* remove */(a => { let index = a.indexOf(action); if(index>=0) { a.splice(index, 1); return true; } else { return false; }})(this.actions);
                };
                {
                    this.handlers.forEach((handler) => handler.onActionCancelled(action));
                };
                action.flush();
            }
        }

        /**
         * @return {number} The maximal number of actions that the register can contain.
         */
        public getSizeMax() : number {
            return this.sizeMax;
        }

        /**
         * Changes the number of actions that the register can contain.
         * In the case that actions have to be removed (because the new size is smaller than the old one),
         * the necessary number of the oldest and cleanable actions (cf. Action::getRegistrationPolicy)
         * are flushed and removed from the register.
         * @param {number} newSizeMax The max number of actions that can contain the register. Must be equal or greater than 0.
         */
        public setSizeMax(newSizeMax : number) {
            //FIXME
            // if(newSizeMax >= 0) {
            //     {
            //         let i : number = 0;
            //         let nb : number = 0;
            //         let toRemove : number = /* size */(<number>this.actions.length) - newSizeMax;
            //         while((nb < toRemove && i < /* size */(<number>this.actions.length))) {
            //             if(/* get */this.actions[i].getRegistrationPolicy() !== org.malai.action.Action.RegistrationPolicy.UNLIMITED) {
            //                 /* remove */this.actions.splice(i, 1).flush();
            //                 nb++;
            //             } else {
            //                 i++;
            //             }
            //         };
            //     };
            //     this.sizeMax = newSizeMax;
            // }
        }
    }
    ActionsRegistry["__class"] = "org.malai.action.ActionsRegistry";

}


org.malai.action.ActionsRegistry.INSTANCE_$LI$();

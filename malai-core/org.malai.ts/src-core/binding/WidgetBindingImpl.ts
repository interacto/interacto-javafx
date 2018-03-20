/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Creates a widget binding. This constructor must initialise the interaction. The widget binding is (de-)activated if the given
     * instrument is (de-)activated.
     * @param {*} ins The instrument that contains the widget binding.
     * @param {boolean} exec Specifies if the action must be execute or update on each evolution of the interaction.
     * @param {*} actionClass The type of the action that will be created. Used to instantiate the action by reflexivity.
     * The class must be public and must have a constructor with no parameter.
     * @param {InteractionImpl} interaction The user interaction of the binding.
     * @throws IllegalArgumentException If the given interaction or instrument is null.
     * @class
     * @author Arnaud BLOUIN
     */
    export abstract class WidgetBindingImpl<A extends ActionImpl, I extends InteractionImpl<any, any>> implements WidgetBinding { //, N extends Instrument<any>
        // protected loggerBinding : java.util.logging.Logger;

        // protected loggerAction : java.util.logging.Logger;

        /**
         * The source interaction.
         */
        protected interaction : I;

        /**
         * The current action in progress.
         */
        protected action : A;

        // /**
        //  * The instrument that contains the widget binding.
        //  */
        // protected instrument : N;

        /**
         * Specifies if the action must be execute or update * on each evolution of the interaction.
         */
        protected execute : boolean;

        /**
         * Defines whether the action must be executed in a specific thread.
         */
        protected async : boolean;

        /**
         * The action class to instantiate.
         */
        protected clazzAction : () => A;

        public constructor(/* ins : N,  */ exec : boolean, actionClass : () => A, interaction : I) {
            // if(this.loggerBinding===undefined) this.loggerBinding = null;
            // if(this.loggerAction===undefined) this.loggerAction = null;
            if(this.interaction===undefined) this.interaction = null;
            if(this.action===undefined) this.action = null;
            // if(this.instrument===undefined) this.instrument = null;
            if(this.execute===undefined) this.execute = false;
            if(this.async===undefined) this.async = false;
            if(this.clazzAction===undefined) this.clazzAction = null;
            this.clazzAction = actionClass;
            this.interaction = interaction;
            this.action = null;
            // this.instrument = ins;
            this.execute = exec;
            this.interaction.getFsm().addHandler(this);
            this.setActivated(true);//ins.isActivated());
            this.async = false;
        }

        public logBinding(log : boolean) {
            // if(log) {
            //     if(this.loggerBinding == null) {
            //         this.loggerBinding = java.util.logging.Logger.getLogger(/* getName */(c => c["__class"]?c["__class"]:c["name"])((<any>this.constructor)));
            //     }
            // } else {
            //     this.loggerBinding = null;
            // }
        }

        public logAction(log : boolean) {
            // if(log) {
            //     if(this.loggerAction == null) {
            //         this.loggerAction = java.util.logging.Logger.getLogger(/* getName */(c => c["__class"]?c["__class"]:c["name"])((<any>this.constructor)));
            //     }
            // } else {
            //     this.loggerAction = null;
            // }
        }

        public logInteraction(log : boolean) {
            this.interaction.log(log);
        }

        /**
         * Whether the action must be executed in a specific thread.
         * @return {boolean} True: the action will be executed asynchronously.
         */
        public isAsync() : boolean {
            return this.async;
        }

        /**
         * Sets wether the action must be executed in a specific thread.
         * @param {boolean} asyncAction True: the action will be executed asynchronously.
         */
        public setAsync(asyncAction : boolean) {
            this.async = asyncAction;
        }

        /**
         * 
         */
        public clearEvents() {
            this.interaction.fullReinit();
        }

        /**
         * creates the action of the widget binding. If the attribute 'action' is not null, nothing will be done.
         * @return {ActionImpl} The created action.
         */
        protected map() : A {
            return this.clazzAction();
        }

        /**
         * 
         */
        public abstract first();

        /**
         * 
         */
        public then() {
        }

        /**
         * 
         * @return {boolean}
         */
        public abstract when() : boolean;

        public getInteraction() : I {
            return this.interaction;
        }

        /**
         * 
         * @return {ActionImpl}
         */
        public getAction() : A {
            return this.action;
        }

        /**
         * 
         * @return {boolean}
         */
        public isActivated() : boolean {
            return true; //this.instrument.isActivated();
        }

        /**
         * 
         * @return {boolean}
         */
        public isRunning() : boolean {
            return this.interaction.isRunning();
        }

        /**
         * 
         * @return {boolean}
         */
        public isStrictStart() : boolean {
            return false;
        }

        protected unbindActionAttributes() {
            if(this.action != null) {
                this.unbindActionAttributesClass((<any>this.action.constructor));
                // if(this.loggerAction != null) {
                //     this.loggerAction.log(java.util.logging.Level.INFO, "Action unbound: " + this.action);
                // }
            }
        }

        private unbindActionAttributesClass(clazz : any) {
            //FIXME
            // java.util.Arrays.stream<any>(clazz.getDeclaredFields()).filter((field) => field.isAnnotationPresent("AutoUnbind") && "javafx.beans.property.Property".isAssignableFrom(field.getType())).forEach((field) => {
            //     try {
            //         let access : boolean = field.isAccessible();
            //         let o : any = /* get */this.action[field.name];
            //         if(o != null && (o["__interfaces"] != null && o["__interfaces"].indexOf("javafx.beans.property.Property") >= 0 || o.constructor != null && o.constructor["__interfaces"] != null && o.constructor["__interfaces"].indexOf("javafx.beans.property.Property") >= 0)) {
            //             (<javafx.beans.property.Property<any>><any>o).unbind();
            //         }
            //     } catch(ex) {
            //         console.error(ex.message, ex);
            //     };
            // });
            // let superClass : any = clazz.getSuperclass();
            // if(superClass != null && superClass !== ActionImpl && ActionImpl.isAssignableFrom(superClass)) {
            //     this.unbindActionAttributesClass(<any>superClass);
            // }
        }

        /**
         * 
         */
        public fsmCancels() {
            if(this.action != null) {
                // if(this.loggerBinding != null) {
                //     this.loggerBinding.log(java.util.logging.Level.INFO, "Binding cancelled");
                // }
                this.action.cancel();
                // if(this.loggerAction != null) {
                //     this.loggerAction.log(java.util.logging.Level.INFO, "Action cancelled");
                // }
                this.unbindActionAttributes();
                // this.instrument.onActionCancelled(this.action);
                if(this.isExecute() && this.action.hadEffect()) {
                    if(this.action != null && (this.action["__interfaces"] != null && this.action["__interfaces"].indexOf("Undoable") >= 0 || this.action.constructor != null && this.action.constructor["__interfaces"] != null && this.action.constructor["__interfaces"].indexOf("Undoable") >= 0)) {
                        (<any>this.action).undo();
                        // if(this.loggerAction != null) {
                        //     this.loggerAction.log(java.util.logging.Level.INFO, "Action undone");
                        // }
                    } else {
                        throw new MustBeUndoableActionException((<any>this.action.constructor));
                    }
                }
                this.action = null;
                // this.instrument.interimFeedback();
            }
        }

        /**
         * 
         */
        public fsmStarts() {
            let ok : boolean = this.action == null && this.isActivated() && this.when();
            // if(this.loggerBinding != null) {
            //     this.loggerBinding.log(java.util.logging.Level.INFO, "Starting binding: " + ok);
            // }
            if(ok) {
                this.action = this.map();
                this.first();
                // if(this.loggerAction != null) {
                //     this.loggerAction.log(java.util.logging.Level.INFO, "Action created and init: " + this.action);
                // }
                this.feedback();
            } else {
                if(this.isStrictStart()) {
                    // if(this.loggerBinding != null) {
                    //     this.loggerBinding.log(java.util.logging.Level.INFO, "Cancelling starting interaction: " + this.interaction);
                    // }
                    throw new CancelFSMException();
                }
            }
        }

        /**
         * 
         */
        public fsmStops() {
            let ok : boolean = this.when();
            // if(this.loggerBinding != null) {
            //     this.loggerBinding.log(java.util.logging.Level.INFO, "Binding stops with condition: " + ok);
            // }
            if(ok) {
                if(this.action == null) {
                    this.action = this.map();
                    this.first();
                    // if(this.loggerAction != null) {
                    //     this.loggerAction.log(java.util.logging.Level.INFO, "Action created and init: " + this.action);
                    // }
                }
                if(!this.execute) {
                    this.then();
                    // if(this.loggerAction != null) {
                    //     this.loggerAction.log(java.util.logging.Level.INFO, "Action updated: " + this.action);
                    // }
                }
                this.executeAction(this.action, this.async);
                this.unbindActionAttributes();
                this.action = null;
                // this.instrument.interimFeedback();
            } else {
                if(this.action != null) {
                    // if(this.loggerAction != null) {
                    //     this.loggerAction.log(java.util.logging.Level.INFO, "Cancelling the action: " + this.action);
                    // }
                    this.action.cancel();
                    this.unbindActionAttributes();
                    // this.instrument.onActionCancelled(this.action);
                    this.action = null;
                    // this.instrument.interimFeedback();
                }
            }
        }

        private executeAction(act : Action, async : boolean) {
            if(async) {
                this.executeActionAsync(act);
            } else {
                this.afterActionExecuted(act, act.doIt());
            }
        }

        protected abstract executeActionAsync(act : Action);

        protected afterActionExecuted(act : Action, ok : boolean) {
            // if(this.loggerAction != null) {
            //     this.loggerAction.log(java.util.logging.Level.INFO, "Action execution did it: " + ok);
            // }
            if(ok) {
                // this.instrument.onActionExecuted(act);
                act.done();
                // this.instrument.onActionDone(act);
            }
            let hadEffect : boolean = act.hadEffect();
            // if(this.loggerAction != null) {
            //     this.loggerAction.log(java.util.logging.Level.INFO, "Action execution had effect: " + hadEffect);
            // }
            if(hadEffect) {
                if(act.getRegistrationPolicy() !== RegistrationPolicy.NONE) {
                    // ActionsRegistry.INSTANCE_$LI$().addAction(act, this.instrument);//FIXME
                    // this.instrument.onActionAdded(act);
                } else {
                    ActionsRegistry.INSTANCE_$LI$().unregisterActions(act);
                }
                act.followingActions().forEach((actFollow) => this.executeAction(actFollow, false));
            }
        }

        /**
         * 
         */
        public fsmUpdates() {
            let ok : boolean = this.when();
            // if(this.loggerBinding != null) {
            //     this.loggerBinding.log(java.util.logging.Level.INFO, "Binding updates with condition: " + ok);
            // }
            if(ok) {
                if(this.action == null) {
                    // if(this.loggerAction != null) {
                    //     this.loggerAction.log(java.util.logging.Level.INFO, "Action creation");
                    // }
                    this.action = this.map();
                    this.first();
                }
                // if(this.loggerAction != null) {
                //     this.loggerAction.log(java.util.logging.Level.INFO, "Action update");
                // }
                this.then();
                if(this.execute && this.action.canDo()) {
                    // if(this.loggerAction != null) {
                    //     this.loggerAction.log(java.util.logging.Level.INFO, "Action execution");
                    // }
                    this.action.doIt();
                    // this.instrument.onActionExecuted(this.action);
                }
                this.feedback();
            }
        }

        /**
         * 
         * @return {boolean}
         */
        public isExecute() : boolean {
            return this.execute;
        }

        /**
         * 
         */
        public feedback() {
        }

        /**
         * 
         * @param {boolean} activ
         */
        public setActivated(activ : boolean) {
            // if(this.loggerBinding != null) {
            //     this.loggerBinding.log(java.util.logging.Level.INFO, "Binding Activated: " + activ);
            // }
            this.interaction.setActivated(activ);
        }

        // /**
        //  *
        //  * @return {*}
        //  */
        // public getInstrument() : N {
        //     return this.instrument;
        // }
    }
    WidgetBindingImpl["__class"] = "malai.WidgetBindingImpl";
    WidgetBindingImpl["__interfaces"] = ["malai.FSMHandler","malai.WidgetBinding"];
}


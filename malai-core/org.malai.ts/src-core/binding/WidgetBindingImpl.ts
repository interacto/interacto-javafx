/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */

import {ActionImpl} from "../action/ActionImpl";
import {InteractionImpl} from "../interaction/InteractionImpl";
import {WidgetBinding} from "./WidgetBinding";
import {CancelFSMException} from "../fsm/CancelFSMException";
import {Action, RegistrationPolicy} from "../action/Action";
import {ActionsRegistry} from "../action/ActionsRegistry";
import {isUndoableType} from "../undo/Undoable";
import {factory} from "../logging/ConfigLog";
import {Logger} from "typescript-logging";
import {MustBeUndoableActionException} from "./MustBeUndoableActionException";
import {FSM} from "../fsm/FSM";

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
export abstract class WidgetBindingImpl<A extends ActionImpl, I extends InteractionImpl<{}, FSM<{}>>> implements WidgetBinding {
    //, N extends Instrument<any>
    protected loggerBinding: Logger | undefined;

    protected loggerAction: Logger | undefined;

    /**
     * The source interaction.
     */
    protected readonly interaction: I;

    /**
     * The current action in progress.
     */
    protected action: A | undefined;

    // /**
    //  * The instrument that contains the widget binding.
    //  */
    // protected readonly instrument : N;

    /**
     * Specifies if the action must be execute or update * on each evolution of the interaction.
     */
    protected execute: boolean;

    /**
     * Defines whether the action must be executed in a specific thread.
     */
    protected async: boolean;

    /**
     * The action class to instantiate.
     */
    protected readonly clazzAction: () => A;

    public constructor(/* ins : N,  */ exec: boolean, actionClass: () => A, interaction: I) {
        this.execute = false;
        this.async = false;
        this.clazzAction = actionClass;
        this.interaction = interaction;
        this.action = undefined;
        // this.instrument = ins;
        this.execute = exec;
        this.interaction.getFsm().addHandler(this);
        this.setActivated(true); //ins.isActivated());
    }

    public logBinding(log: boolean): void {
        if (log) {
            if (this.loggerBinding === undefined) {
                this.loggerBinding = factory.getLogger("Binding");
            }
        } else {
            //     this.loggerBinding = undefined;
        }
    }

    public logAction(log: boolean): void {
        if (log) {
            if (this.loggerAction === undefined) {
                this.loggerAction = factory.getLogger("Action");
            }
        } else {
            this.loggerAction = undefined;
        }
    }

    public logInteraction(log: boolean): void {
        this.interaction.log(log);
    }

    /**
     * Whether the action must be executed in a specific thread.
     * @return {boolean} True: the action will be executed asynchronously.
     */
    public isAsync(): boolean {
        return this.async;
    }

    /**
     * Sets wether the action must be executed in a specific thread.
     * @param {boolean} asyncAction True: the action will be executed asynchronously.
     */
    public setAsync(asyncAction: boolean) {
        this.async = asyncAction;
    }

    /**
     *
     */
    public clearEvents(): void {
        this.interaction.fullReinit();
    }

    /**
     * creates the action of the widget binding. If the attribute 'action' is not null, nothing will be done.
     * @return {ActionImpl} The created action.
     */
    protected map(): A {
        return this.clazzAction();
    }

    public abstract first(): void;

    public then(): void {
    }

    /**
     * @return {boolean}
     */
    public abstract when(): boolean;

    public getInteraction(): I {
        return this.interaction;
    }

    /**
     * @return {ActionImpl}
     */
    public getAction(): A | undefined {
        return this.action;
    }

    /**
     * @return {boolean}
     */
    public isActivated(): boolean {
        return true; //this.instrument.isActivated();
    }

    /**
     * @return {boolean}
     */
    public isRunning(): boolean {
        return this.interaction.isRunning();
    }

    /**
     *
     * @return {boolean}
     */
    public isStrictStart(): boolean {
        return false;
    }

    protected unbindActionAttributes(): void {
        if (this.action !== undefined) {
            this.unbindActionAttributesClass(this.action.constructor);
            if (this.loggerAction !== undefined) {
                this.loggerAction.info("Action unbound: " + this.action);
            }
        }
    }

    private unbindActionAttributesClass(clazz: Object): void {
        //FIXME
        // java.util.Arrays.stream<any>(clazz.getDeclaredFields()).filter((field) =>
        // field.isAnnotationPresent("AutoUnbind") && "javafx.beans.property.Property".isAssignableFrom(field.getType())).
        // forEach((field) => {
        //     try {
        //         let access : boolean = field.isAccessible();
        //         let o : any = /* get */this.action[field.name];
        //         if(o instanceof Property) {
        //             (<javafx.beans.property.Property<any>><any>o).unbind();
        //         }
        //     } catch(ex) {
        //         console.error(ex.message, ex);
        //     };
        // });
        // let superClass : any = clazz.getSuperclass();
        // if(superClass !== undefined && superClass !== ActionImpl && ActionImpl.isAssignableFrom(superClass)) {
        //     this.unbindActionAttributesClass(<any>superClass);
        // }
    }

    /**
     *
     */
    public fsmCancels(): void {
        if (this.action !== undefined) {
            if (this.loggerBinding !== undefined) {
                this.loggerBinding.info("Binding cancelled");
            }
            this.action.cancel();
            if (this.loggerAction !== undefined) {
                this.loggerAction.info("Action cancelled");
            }
            this.unbindActionAttributes();
            // this.instrument.onActionCancelled(this.action);
            if (this.isExecute() && this.action.hadEffect()) {
                if (isUndoableType(this.action)) {
                    this.action.undo();
                    if (this.loggerAction !== undefined) {
                        this.loggerAction.info("Action undone");
                    }
                } else {
                    throw new MustBeUndoableActionException(this.clazzAction);
                }
            }
            this.action = undefined;
            // this.instrument.interimFeedback();
        }
    }

    /**
     *
     */
    public fsmStarts(): void {
        const ok: boolean = this.action === undefined && this.isActivated() && this.when();
        if (this.loggerBinding !== undefined) {
            this.loggerBinding.info("Starting binding: " + ok);
        }
        if (ok) {
            this.action = this.map();
            this.first();
            if (this.loggerAction !== undefined) {
                this.loggerAction.info("Action created and init: " + this.action);
            }
            this.feedback();
        } else {
            if (this.isStrictStart()) {
                if (this.loggerBinding !== undefined) {
                    this.loggerBinding.info("Cancelling starting interaction: " + this.interaction);
                }
                throw new CancelFSMException();
            }
        }
    }

    /**
     *
     */
    public fsmStops(): void {
        const ok: boolean = this.when();
        if (this.loggerBinding !== undefined) {
            this.loggerBinding.info("Binding stops with condition: " + ok);
        }
        if (ok) {
            if (this.action === undefined) {
                this.action = this.map();
                this.first();
                if (this.loggerAction !== undefined) {
                    this.loggerAction.info("Action created and init: " + this.action);
                }
            }
            if (!this.execute) {
                this.then();
                if (this.loggerAction !== undefined) {
                    this.loggerAction.info("Action updated: " + this.action);
                }
            }
            this.executeAction(this.action, this.async);
            this.unbindActionAttributes();
            this.action = undefined;
            // this.instrument.interimFeedback();
        } else {
            if (this.action !== undefined) {
                if (this.loggerAction !== undefined) {
                    this.loggerAction.info("Cancelling the action: " + this.action);
                }
                this.action.cancel();
                this.unbindActionAttributes();
                // this.instrument.onActionCancelled(this.action);
                this.action = undefined;
                // this.instrument.interimFeedback();
            }
        }
    }

    private executeAction(act: Action, async: boolean): void {
        if (async) {
            this.executeActionAsync(act);
        } else {
            this.afterActionExecuted(act, act.doIt());
        }
    }

    protected abstract executeActionAsync(act: Action): void;

    protected afterActionExecuted(act: Action, ok: boolean): void {
        if (this.loggerAction !== undefined) {
            this.loggerAction.info("Action execution did it: " + ok);
        }
        if (ok) {
            // this.instrument.onActionExecuted(act);
            act.done();
            // this.instrument.onActionDone(act);
        }
        const hadEffect: boolean = act.hadEffect();
        if (this.loggerAction !== undefined) {
            this.loggerAction.info("Action execution had effect: " + hadEffect);
        }
        if (hadEffect) {
            if (act.getRegistrationPolicy() !== RegistrationPolicy.NONE) {
                ActionsRegistry.INSTANCE.addAction(act); //, this.instrument
                // this.instrument.onActionAdded(act);
            } else {
                ActionsRegistry.INSTANCE.unregisterActions(act);
            }
            act.followingActions().forEach(actFollow => this.executeAction(actFollow, false));
        }
    }

    public fsmUpdates(): void {
        const ok: boolean = this.when();
        if (this.loggerBinding !== undefined) {
            this.loggerBinding.info("Binding updates with condition: " + ok);
        }
        if (ok) {
            if (this.action === undefined) {
                if (this.loggerAction !== undefined) {
                    this.loggerAction.info("Action creation");
                }
                this.action = this.map();
                this.first();
            }
            if (this.loggerAction !== undefined) {
                this.loggerAction.info("Action update");
            }
            this.then();
            if (this.execute && this.action.canDo()) {
                if (this.loggerAction !== undefined) {
                    this.loggerAction.info("Action execution");
                }
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
    public isExecute(): boolean {
        return this.execute;
    }

    /**
     *
     */
    public feedback(): void {
    }

    /**
     *
     * @param {boolean} activ
     */
    public setActivated(activ: boolean): void {
        if (this.loggerBinding !== undefined) {
            this.loggerBinding.info("Binding Activated: " + activ);
        }
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

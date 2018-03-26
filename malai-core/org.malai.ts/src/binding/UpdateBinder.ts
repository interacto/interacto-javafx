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

import {ActionImpl} from "../../src-core/action/ActionImpl";
import {TSInteraction} from "../interaction/TSInteraction";
import {FSM} from "../../src-core/fsm/FSM";
import {Binder} from "./Binder";
import {TSWidgetBinding} from "./TSWidgetBinding";
import {AnonNodeBinding} from "./AnonNodeBinding";

/**
 * The base binding builder for bindings where actions can be updated while the user interaction is running.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
export abstract class UpdateBinder<A extends ActionImpl, I extends TSInteraction<FSM<Event>, {}>, B extends UpdateBinder<A, I, B>>
    extends Binder<A, I, B> {
    protected updateFct: (a: A | undefined, i: I) => void;
    protected cancelFct: (a: A | undefined, i: I) => void;
    protected endOrCancelFct: (a: A | undefined, i: I) => void;
    protected feedbackFct: () => void;
    protected execOnChanges: boolean;
    protected _strictStart: boolean;

    public constructor(actionProducer: () => A, interaction: I) {
        super(actionProducer, interaction);
        this.updateFct = () => {};
        this.cancelFct = () => {};
        this.endOrCancelFct = () => {};
        this.feedbackFct = () => {};
        this.execOnChanges = false;
        this._strictStart = false;
    }

    /**
     * Specifies the update of the action on interaction updates.
     * @param update The callback method that updates the action.
     * This callback takes as arguments the action to update and the ongoing interactions (and its parameters).
     * @return The builder to chain the buiding configuration.
     */
    public then(update: (a: A | undefined, i: I) => void): B {
        this.updateFct = update;
        return this as {} as B;
    }

    /**
     * Defines whether the action must be executed on each interaction updates (if 'when' predicate is ok).
     * @return The builder to chain the buiding configuration.
     */
    public exec(): B {
        this.execOnChanges = true;
        return this as {} as B;
    }

    /**
     * Defines what to do when an action is aborted (because the interaction is abord map).
     * The undoable action is autmatically cancelled so that nothing must be done on the action.
     * @return The builder to chain the buiding configuration.
     */
    public cancel(cancel: (a: A | undefined, i: I) => void): B {
        this.cancelFct = cancel;
        return this as {} as B;
    }

    /**
     * Defines what to do when an action is aborted (because the interaction is abord map).
     * The undoable action is autmatically cancelled so that nothing must be done on the action.
     * @return The builder to chain the buiding configuration.
     */
    public endOrCancel(endOrCancel: (a: A | undefined, i: I) => void): B {
        this.endOrCancelFct = endOrCancel;
        return this as {} as B;
    }

    /**
     * Defines interim feedback provided to users on each interaction updates.
     * @return The builder to chain the buiding configuration.
     */
    public feedback(feedback: () => void): B {
        this.feedbackFct = feedback;
        return this as {} as B;
    }

    /**
     * The interaction does not start if the condition of the binding ('when') is not fulfilled.
     * @return The builder to chain the buiding configuration.
     */
    public strictStart(): B {
        this._strictStart = true;
        return this as {} as B;
    }

    public bind(): TSWidgetBinding<A, I> {
        return new AnonNodeBinding(this.execOnChanges, this.actionClass, this.interaction, this.initAction, this.updateFct,
            this.checkConditions, this.onEnd, this.cancelFct, this.endOrCancelFct, this.feedbackFct, this.widgets, this._async,
            this._strictStart, new Array(...this.logLevels));
    }
}

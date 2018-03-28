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

import {TSInteraction} from "../interaction/TSInteraction";
import {FSM} from "../../src-core/fsm/FSM";
import {Binder} from "./Binder";
import {TSWidgetBinding} from "./TSWidgetBinding";
import {AnonNodeBinding} from "./AnonNodeBinding";
import {CommandImpl} from "../../src-core/command/CommandImpl";

/**
 * The base binding builder for bindings where actions can be updated while the user interaction is running.
 * @param <A> The type of the command to produce.
 * @author Arnaud Blouin
 */
export abstract class UpdateBinder<C extends CommandImpl, I extends TSInteraction<FSM<Event>, {}>, B extends UpdateBinder<C, I, B>>
    extends Binder<C, I, B> {
    protected updateFct: (c: C | undefined, i: I) => void;
    protected cancelFct: (c: C | undefined, i: I) => void;
    protected endOrCancelFct: (c: C | undefined, i: I) => void;
    protected feedbackFct: () => void;
    protected execOnChanges: boolean;
    protected _strictStart: boolean;

    protected constructor(cmdProducer: () => C, interaction: I) {
        super(cmdProducer, interaction);
        this.updateFct = () => {};
        this.cancelFct = () => {};
        this.endOrCancelFct = () => {};
        this.feedbackFct = () => {};
        this.execOnChanges = false;
        this._strictStart = false;
    }

    /**
     * Specifies the update of the command on interaction updates.
     * @param update The callback method that updates the command.
     * This callback takes as arguments the command to update and the ongoing interactions (and its parameters).
     * @return The builder to chain the buiding configuration.
     */
    public then(update: (c: C | undefined, i: I) => void): B {
        this.updateFct = update;
        return this as {} as B;
    }

    /**
     * Defines whether the command must be executed on each interaction updates (if 'when' predicate is ok).
     * @return The builder to chain the building configuration.
     */
    public exec(): B {
        this.execOnChanges = true;
        return this as {} as B;
    }

    /**
     * Defines what to do when a command is aborted (because the interaction is aborted first).
     * The undoable command is automatically cancelled so that nothing must be done on the command.
     * @return The builder to chain the building configuration.
     */
    public cancel(cancel: (c: C | undefined, i: I) => void): B {
        this.cancelFct = cancel;
        return this as {} as B;
    }

    /**
     * Defines what to do when a command is aborted (because the interaction is aborted first).
     * The undoable command is automatically cancelled so that nothing must be done on the command.
     * @return The builder to chain the building configuration.
     */
    public endOrCancel(endOrCancel: (c: C | undefined, i: I) => void): B {
        this.endOrCancelFct = endOrCancel;
        return this as {} as B;
    }

    /**
     * Defines interim feedback provided to users on each interaction updates.
     * @return The builder to chain the building configuration.
     */
    public feedback(feedback: () => void): B {
        this.feedbackFct = feedback;
        return this as {} as B;
    }

    /**
     * The interaction does not start if the condition of the binding ('when') is not fulfilled.
     * @return The builder to chain the building configuration.
     */
    public strictStart(): B {
        this._strictStart = true;
        return this as {} as B;
    }

    public bind(): TSWidgetBinding<C, I> {
        return new AnonNodeBinding(this.execOnChanges, this.cmdClass, this.interaction, this.initCmd, this.updateFct,
            this.checkConditions, this.onEnd, this.cancelFct, this.endOrCancelFct, this.feedbackFct, this.widgets, this._async,
            this._strictStart, new Array(...this.logLevels));
    }
}

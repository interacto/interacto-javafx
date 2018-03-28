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

import {Action, ActionStatus, RegistrationPolicy} from "./Action";
import {ActionsRegistry} from "./ActionsRegistry";

/**
 * The default constructor.
 * Initialises the current status to created.
 * @class
 * @author Arnaud BLOUIN
 */
export abstract class ActionImpl implements Action {
    /**
     * The state of the action.
     */
    protected status: ActionStatus;

    protected constructor() {
        this.status = ActionStatus.CREATED;
    }

    /**
     *
     */
    public flush(): void {
        this.status = ActionStatus.FLUSHED;
    }

    /**
     * Actions may need to create a memento before their first execution.
     * This is the goal of the operation that should be overriden.
     * This operator is called a single time before the first execution of the action.
     */
    protected createMemento(): void {
    }

    /**
     *
     * @return {boolean}
     */
    public doIt(): boolean {
        let ok: boolean;
        if ((this.status === ActionStatus.CREATED || this.status === ActionStatus.EXECUTED) && this.canDo()) {
            if (this.status === ActionStatus.CREATED) {
                this.createMemento();
            }
            ok = true;
            this.doActionBody();
            this.status = ActionStatus.EXECUTED;
            ActionsRegistry.INSTANCE.onActionExecuted(this);
        } else {
            ok = false;
        }
        return ok;
    }

    /**
     * This method contains the statements to execute the action.
     * This method is automatically called by DoIt and must not be called explicitly.
     * @since 0.1
     */
    protected abstract doActionBody(): void;

    public getRegistrationPolicy(): RegistrationPolicy {
        return this.hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
    }

    /**
     *
     * @return {boolean}
     */
    public hadEffect(): boolean {
        return this.isDone();
    }

    /**
     *
     * @param {*} action
     * @return {boolean}
     */
    public unregisteredBy(action: Action): boolean {
        return false;
    }

    /**
     *
     */
    public done(): void {
        if (this.status === ActionStatus.CREATED || this.status === ActionStatus.EXECUTED) {
            this.status = ActionStatus.DONE;
            ActionsRegistry.INSTANCE.onActionDone(this);
        }
    }

    /**
     *
     * @return {boolean}
     */
    public isDone(): boolean {
        return this.status === ActionStatus.DONE;
    }

    /**
     *
     */
    public cancel(): void {
        this.status = ActionStatus.CANCELLED;
    }

    public getStatus(): ActionStatus {
        return this.status;
    }

    public followingActions(): Array<Action> {
        return [];
    }

    public abstract canDo(): boolean;
}

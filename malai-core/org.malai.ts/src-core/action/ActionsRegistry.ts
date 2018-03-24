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

import {ActionHandler} from "./ActionHandler";
import {Action, RegistrationPolicy} from "./Action";
import {isUndoableType} from "../undo/Undoable";
import {UndoCollector} from "../undo/UndoCollector";
import {MArray} from "../../src/util/ArrayUtil";

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
    public static readonly INSTANCE: ActionsRegistry = new ActionsRegistry();

    /**
     * The saved actions.
     */
    private readonly actions: MArray<Action>;

    /**
     * The actions handler.
     */
    private readonly handlers: MArray<ActionHandler>;

    /**
     * The max number of cleanable actions (cf. Action::getRegistrationPolicy) that can contain the register.
     */
    private sizeMax: number;

    constructor() {
        this.actions = new MArray();
        this.handlers = new MArray();
        this.sizeMax = 50;
    }

    public getHandlers(): Array<ActionHandler> {
        return [...this.handlers];
    }

    /**
     * Notifies handler that an action has been executed.
     * @param {*} action The executed action.
     */
    public onActionExecuted(action: Action): void {
        this.handlers.forEach(handler => handler.onActionExecuted(action));
    }

    /**
     * Notifies handler that an action ends.
     * @param {*} action The ending action.
     */
    public onActionDone(action: Action): void {
        this.handlers.forEach(handler => handler.onActionDone(action));
    }

    /**
     * @return {*[]} The stored actions. Cannot be null. Because of concurrency, you should not modify this list.
     */
    public getActions(): Array<Action> {
        return this.actions;
    }

    /**
     * Removes and flushes the actions from the register that use the given action type.
     * @see Action::unregisteredBy
     * @param {*} action The action that may cancels others.
     */
    public unregisterActions(action: Action): void {
        let i: number = 0;
        while ((i < this.actions.length)) {
            if (this.actions[i].unregisteredBy(action)) {
                const delAction = this.actions.removeAt(i);
                if (delAction !== undefined) {
                    delAction.flush();
                }
            } else {
                i++;
            }
        }
    }

    /**
     * Adds an action to the register. Before being added, the given action is used to cancel actions
     * already added. Handlers are notified of the add of the given action. If Undoable, the action is
     * added to the undo collector as well.
     * @param {*} action The action to add. If null, nothing is done.
     * @param {*} actionHandler The handler that produced or is associated to the action. If null, nothing is done.
     */
    public addAction(action: Action, actionHandler?: ActionHandler): void {
        if (this.actions.indexOf(action) < 0 &&
            (this.sizeMax > 0 || action.getRegistrationPolicy() === RegistrationPolicy.UNLIMITED)) {
            this.unregisterActions(action);

            // If there is too many actions in the register, the oldest removable action is removed and flushed.
            if (this.actions.length >= this.sizeMax) {
                const act = this.actions.find(a => a.getRegistrationPolicy() !== RegistrationPolicy.UNLIMITED);

                if (act !== undefined) {
                    this.actions.remove(act);
                    act.flush();
                }
            }

            this.actions.push(action);

            this.handlers.forEach(handler => handler.onActionAdded(action));

            if (isUndoableType(action)) {
                UndoCollector.INSTANCE.add(action, actionHandler);
            }
        }
    }

    /**
     * Removes the action from the register. The action is then flushed.
     * @param {*} action The action to remove.
     */
    public removeAction(action: Action): void {
        this.actions.remove(action);
        action.flush();
    }

    /**
     * Adds an action handler.
     * @param {*} handler The handler to add.
     */
    public addHandler(handler: ActionHandler): void {
        this.handlers.push(handler);
    }

    /**
     * Removes the given handler.
     * @param {*} handler The handler to remove.
     */
    public removeHandler(handler: ActionHandler): void {
        this.handlers.push(handler);
    }

    /**
     * Removes all the action handler.
     */
    public removeAllHandlers(): void {
        this.handlers.clear();
    }

    /**
     * Flushes and removes all the stored actions.
     */
    public clear(): void {
        this.actions.forEach(action => action.flush());
        this.actions.clear();
    }

    /**
     * Aborts the given action, i.e. the action is cancelled and removed from the register.
     * Handlers are then notified. The action is finally flushed.
     * @param {*} action The action to cancel.
     */
    public cancelAction(action: Action): void {
        action.cancel();
        this.actions.remove(action);
        this.handlers.forEach(handler => handler.onActionCancelled(action));
        action.flush();
    }

    /**
     * @return {number} The maximal number of actions that the register can contain.
     */
    public getSizeMax(): number {
        return this.sizeMax;
    }

    /**
     * Changes the number of actions that the register can contain.
     * In the case that actions have to be removed (because the new size is smaller than the old one),
     * the necessary number of the oldest and cleanable actions (cf. Action::getRegistrationPolicy)
     * are flushed and removed from the register.
     * @param {number} newSizeMax The max number of actions that can contain the register. Must be equal or greater than 0.
     */
    public setSizeMax(newSizeMax: number): void {
        if (newSizeMax >= 0) {
            let i: number = 0;
            let nb: number = 0;
            const toRemove: number = this.actions.length - newSizeMax;

            while (nb < toRemove && i < this.actions.length) {
                if (this.actions[i].getRegistrationPolicy() !== RegistrationPolicy.UNLIMITED) {
                    const removed = this.actions.removeAt(i);
                    if (removed !== undefined) {
                        removed.flush();
                    }
                    nb++;
                } else {
                    i++;
                }
            }
            this.sizeMax = newSizeMax;
        }
    }
}

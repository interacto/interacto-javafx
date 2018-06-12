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

import {StateImpl} from "./StateImpl";
import {OutputState} from "./OutputState";
import {Transition} from "./Transition";
import {FSM} from "./FSM";
import {MArray} from "../../util/ArrayUtil";

export abstract class OutputStateImpl<E> extends StateImpl<E> implements OutputState<E> {
    protected readonly transitions: MArray<Transition<E>>;

    protected constructor(stateMachine: FSM<E>, stateName: string) {
        super(stateMachine, stateName);
        this.transitions = new MArray<Transition<E>>();
    }

    public process(event: E): boolean {
        return this.getTransitions().find(tr => {
            try {
                if (tr.execute(event).isPresent()) {
                    return true;
                }
            } catch (ignored) {
                // Already processed
            }
            return false;
        }) !== undefined;
    }

    public checkStartingState(): void {
        if (!this.getFSM().isStarted() && this.getFSM().startingState === this) {
            this.getFSM().onStarting();
        }
    }

    public clearTransitions(): void {
        this.transitions.clear();
    }

    public getTransitions(): Array<Transition<E>> {
        return [...this.transitions];
    }

    public addTransition(tr: Transition<E>): void {
        this.transitions.push(tr);
    }

    public abstract exit(): void;

    public uninstall(): void {
        this.transitions.forEach(tr => tr.uninstall());
        this.transitions.clear();
    }
}

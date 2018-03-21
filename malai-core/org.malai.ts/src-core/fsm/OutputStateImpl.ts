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

export abstract class OutputStateImpl<E> extends StateImpl<E> implements OutputState<E> {
    protected readonly transitions: Array<Transition<E>>;

    protected constructor(stateMachine: FSM<E>, stateName: string) {
        super(stateMachine, stateName);
        this.transitions = [];
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
        if (!this.getFSM().isStarted() && this.getFSM().getStartingState() === this) {
            this.getFSM().onStarting();
        }
    }

    public getTransitions(): Array<Transition<E>> {
        return [...this.transitions];
    }

    /**
     *
     * @param {Transition} tr
     */
    public addTransition(tr: Transition<E>): void {
        if (tr !== undefined) {
            this.transitions.push(tr);
        }
    }

    public abstract exit(): void;
}

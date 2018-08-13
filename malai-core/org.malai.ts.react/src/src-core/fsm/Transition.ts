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

import {OutputState} from "./OutputState";
import {InputState} from "./InputState";
import {Optional} from "../../util/Optional";

export abstract class Transition<E> {
    public readonly src: OutputState<E>;

    public readonly tgt: InputState<E>;

    protected constructor(srcState: OutputState<E>, tgtState: InputState<E>) {
        this.src = srcState;
        this.tgt = tgtState;
        this.src.addTransition(this);
    }

    public execute(event: E): Optional<InputState<E>> {
        if (this.accept(event) && this.isGuardOK(event)) {
            this.src.getFSM().stopCurrentTimeout();
            this.action(event);
            this.src.exit();
            this.tgt.enter();
            return Optional.of<InputState<E>>(this.tgt);
        }
        return Optional.empty<InputState<E>>();
    }

    protected action(event: E | undefined): void {
    }

    public abstract accept(event: E): boolean;

    public abstract isGuardOK(event: E): boolean;

    public abstract getAcceptedEvents(): Set<string>;

    /**
     * Clean the transition when not used anymore.
     */
    public uninstall(): void {
    }
}

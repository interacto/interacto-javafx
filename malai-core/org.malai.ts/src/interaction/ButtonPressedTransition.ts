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

import {TSTransition} from "./TSTransition";
import {OutputState} from "../../src-core/fsm/OutputState";
import {InputState} from "../../src-core/fsm/InputState";
import {EventTypeName, isButton} from "./Events";

export abstract class ButtonPressedTransition extends TSTransition {
    public constructor(srcState: OutputState<UIEvent>, tgtState: InputState<UIEvent>) {
        super(srcState, tgtState);
    }

    public accept(e: Event): boolean {
        return isButton(e.target);
    }

    public getAcceptedEvents(): Set<string> {
        return new Set([EventTypeName.ButtonPressed]);
    }

    public isGuardOK(event: Event): boolean {
        return true;
    }
}

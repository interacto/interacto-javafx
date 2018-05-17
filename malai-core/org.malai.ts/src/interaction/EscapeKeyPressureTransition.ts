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

import {InputState} from "../../src-core/fsm/InputState";
import {OutputState} from "../../src-core/fsm/OutputState";
import {KeyCode} from "./Events";
import {KeyPressureTransition} from "./KeyPressureTransition";

/**
 * This transition should be used to cancel an interaction using key ESCAPE.
 * @author Arnaud BLOUIN
 */
export class EscapeKeyPressureTransition extends KeyPressureTransition {
    public constructor(srcState: OutputState<Event>, tgtState: InputState<Event>) {
        super(srcState, tgtState);
    }

    public isGuardOK(event: Event): boolean {
        return event instanceof KeyboardEvent && (event.code === "Escape" || event.code === String(KeyCode.ESCAPE));
    }
}

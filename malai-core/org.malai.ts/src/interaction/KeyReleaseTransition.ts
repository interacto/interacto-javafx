/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN Gwendal DIDOT
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */

import {TSTransition} from "./TSTransition";
import {InputState} from "../src-core/fsm/InputState";
import {OutputState} from "../src-core/fsm/OutputState";
import {EventRegistrationToken} from "./Events";

/**
 * A transition for a release of a key of a keyboard.
 * @author Gwendal DIDOT
 */
export class KeyReleaseTransition extends TSTransition {
    /**
     * Creates the transition.
     */
    public constructor(srcState: OutputState<Event>, tgtState: InputState<Event>) {
        super(srcState, tgtState);
    }

    public accept(event: Event): boolean {
        return event instanceof KeyboardEvent && event.type === EventRegistrationToken.KeyUp;
    }

    public isGuardOK(event: Event): boolean {
        return true;
    }

    public getAcceptedEvents(): Set<string> {
        return new Set([EventRegistrationToken.KeyUp]);
    }
}

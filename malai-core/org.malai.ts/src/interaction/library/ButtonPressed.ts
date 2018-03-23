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

import {FSMDataHandler} from "../FSMDataHandler";
import {TSFSM} from "../TSFSM";
import {ButtonPressedTransition} from "../ButtonPressedTransition";
import {TerminalState} from "../../../src-core/fsm/TerminalState";
import {ButtonPressEvent} from "../Events";

export class ButtonPressedFSM extends TSFSM<ButtonPressedFSMHandler> {
    public constructor() {
        super();
    }

    protected buildFSM(dataHandler: ButtonPressedFSMHandler): void {
        if (this.states.length > 1) {
            return;
        }
        super.buildFSM(dataHandler);
        const pressed: TerminalState<UIEvent> = new TerminalState<UIEvent>(this, "pressed");
        this.addState(pressed);

        new class extends ButtonPressedTransition {
            public action(event: UIEvent): void {
                if (dataHandler !== undefined && event instanceof ButtonPressEvent) {
                    dataHandler.initToPressedHandler(event);
                }
            }
        }(this.initState, pressed);
    }
}

interface ButtonPressedFSMHandler extends FSMDataHandler {
    initToPressedHandler(event: ButtonPressEvent): void;
}

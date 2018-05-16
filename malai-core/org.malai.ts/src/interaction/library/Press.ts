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

import {TSFSM} from "../TSFSM";
import {FSMDataHandler} from "../FSMDataHandler";
import {TerminalState} from "../../../src-core/fsm/TerminalState";
import {PressureTransition} from "../PressureTransition";
import {isMouseDownEvent} from "../Events";
import {PointInteraction} from "./PointInteraction";
import {PointData} from "./PointData";

class PressFSM extends TSFSM<PressFSMHandler> {

    public constructor() {
        super();
    }

    public buildFSM(dataHandler?: PressFSMHandler): void {
        if (this.states.length > 1) {
            return;
        }
        super.buildFSM(dataHandler);
        const pressed: TerminalState<Event> = new TerminalState<Event>(this, "pressed");
        this.addState(pressed);

        new class extends PressureTransition {
            public action(event: Event): void {
                if (event.target !== null && isMouseDownEvent(event) && dataHandler !== undefined) {
                    dataHandler.initToPress(event);
                }
            }
        }(this.initState, pressed);
    }
}

interface PressFSMHandler extends FSMDataHandler {
    initToPress(event: Event): void;
}

/**
 * A user interaction for pressing down the mouse button.
 * @author Gwendal DIDOT
 */
export class Press extends PointInteraction<PointData, PressFSM, Node> {
    /**
     * Creates the interaction.
     */
    private readonly handler : PressFSMHandler;

    public constructor(fsm?: PressFSM) {
        super(fsm === undefined ? new PressFSM() : fsm);

        this.handler = new class implements PressFSMHandler {
            private readonly _parent: Press;

            constructor(parent: Press) {
                this._parent = parent;
            }

            public initToPress(event: MouseEvent): void {
                this._parent.setPointData(event);
            }

            public reinitData(): void {
                this._parent.reinitData();
            }
        }(this);
        this.getFsm().buildFSM(this.handler);
    }

    public getData(): PointData {
        return this;
    }
}

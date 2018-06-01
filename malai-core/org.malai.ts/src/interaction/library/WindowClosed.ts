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
import {isWindowClosed} from "../Events";
import {TSInteraction} from "../TSInteraction";
import {InteractionData} from "../../../src-core/interaction/InteractionData";
import {WindowCloseTransition} from "../WindowCloseTransition";

export class WindowClosedFSM extends TSFSM<WindowClosedFSMHandler> {
    private checkButton: number | undefined;

    public constructor() {
        super();
    }

    public buildFSM(dataHandler?: WindowClosedFSMHandler): void {
        if (this.states.length > 1) {
            return;
        }
        super.buildFSM(dataHandler);
        const pressed: TerminalState<Event> = new TerminalState<Event>(this, "pressed");
        this.addState(pressed);

        new class extends WindowCloseTransition {
            public action(event: Event): void {
                if (event.target !== null && isWindowClosed(event) && dataHandler !== undefined) {
                    dataHandler.initToCloseHandler(event);
                }
            }
        }(this.initState, pressed);
    }

    public setCheckButton(buttonToCheck: number): void {
        if (this.checkButton === undefined) {
            this.checkButton = buttonToCheck;
        }
    }

    public getCheckButton(): number {
        return this.checkButton === undefined ? -1 : this.checkButton;
    }
}

interface WindowClosedFSMHandler extends FSMDataHandler {
    initToCloseHandler(event: Event): void;
}

/**
 * A user interaction for pressing down the mouse button.
 * @author Gwendal DIDOT
 */
export class WindowClosed extends TSInteraction<InteractionData, WindowClosedFSM, Node> {
    /**
     * Creates the interaction.
     */
    private readonly handler : WindowClosedFSMHandler;

    public constructor(fsm?: WindowClosedFSM) {
        super(fsm === undefined ? new WindowClosedFSM() : fsm);

        this.handler = new class implements WindowClosedFSMHandler {
            private readonly _parent: WindowClosed;

            constructor(parent: WindowClosed) {
                this._parent = parent;
            }

            public initToCloseHandler(event: Event): void {
            }

            public reinitData(): void {
                this._parent.reinitData();
            }
        }(this);
        this.getFsm().buildFSM(this.handler);
    }

    public getData(): InteractionData {
        return this;
    }

}

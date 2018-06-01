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
import {KeyPressureTransition} from "../KeyPressureTransition";
import {OutputState} from "../../../src-core/fsm/OutputState";
import {InputState} from "../../../src-core/fsm/InputState";
import {KeyData} from "./KeyData";
import {KeyInteraction} from "./KeyInteraction";

export class KeyPressedFSM extends TSFSM<KeyPressedFSMHandler> {
    private readonly modifiersAccepted: boolean;

    public constructor(modifierAccepted: boolean) {
        super();
        this.modifiersAccepted = modifierAccepted;
    }

    public buildFSM(dataHandler?: KeyPressedFSMHandler) {
        if (this.states.length > 1) {
            return;
        }

        super.buildFSM(dataHandler);
        const pressed: TerminalState<Event> = new TerminalState<Event>(this, "pressed");

        this.addState(pressed);

        new class extends KeyPressureTransition {
            private readonly _parent: KeyPressedFSM;

            public constructor(parent: KeyPressedFSM, srcState: OutputState<Event>, tgtState: InputState<Event>) {
                super(srcState, tgtState);
                this._parent = parent;
            }

            public action(event: Event): void {
                if (event instanceof KeyboardEvent && dataHandler !== undefined) {
                    dataHandler.onKeyPressed(event);
                }
            }

            public isGuardOK(event: Event): boolean {
                return this._parent.modifiersAccepted || (event instanceof KeyboardEvent && !event.altKey && !event.ctrlKey
                    && !event.shiftKey && !event.metaKey);
            }

        }(this, this.initState, pressed);
    }

    public reinit(): void {
        super.reinit();
    }

}

interface KeyPressedFSMHandler  extends FSMDataHandler {
   onKeyPressed(event: Event): void;
}

/**
 * A user interaction for pressing a key on a keyboard
 * @author Gwendal DIDOT
 */

export class KeyPressed extends KeyInteraction<KeyData, KeyPressedFSM, Node> {

    private readonly handler: KeyPressedFSMHandler;

    public constructor(modifierAccepted: boolean, fsm?: KeyPressedFSM) {
        super(fsm === undefined ? new KeyPressedFSM(modifierAccepted) : fsm);

        this.handler = new class implements KeyPressedFSMHandler {
            private readonly _parent: KeyPressed;

            public constructor(parent: KeyPressed) {
                this._parent = parent;
            }

            public onKeyPressed(event: KeyboardEvent): void {
                this._parent.setKeyData(event);
            }

            public reinitData(): void {
                this._parent.reinitData();
            }
        }(this);
        this.getFsm().buildFSM(this.handler);

    }

    public getData(): KeyData {
        return this;
    }
}

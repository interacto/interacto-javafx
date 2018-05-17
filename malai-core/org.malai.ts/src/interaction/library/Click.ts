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
import {Press, PressFSM} from "./Press";
import {InputState} from "../../../src-core/fsm/InputState";
import {OutputState} from "../../../src-core/fsm/OutputState";
import {PointInteraction} from "./PointInteraction";
import {PointData} from "./PointData";
import {CancellingState} from "../../../src-core/fsm/CancellingState";
import {SubFSMTransition} from "../../../src-core/fsm/SubFSMTransition";
import {FSM} from "../../../src-core/fsm/FSM";
import {StdState} from "../../../src-core/fsm/StdState";
import {MoveTransition} from "../MoveTransition";
import {ReleaseTransition} from "../ReleaseTransition";
import {isMouseUpEvent} from "../Events";

export class ClickFSM extends TSFSM<ClickFSMHandler> {
    private checkButton: number | undefined;

    public readonly pressFSM: PressFSM;

    public constructor() {
        super();
        this.pressFSM = new PressFSM();
    }

    public buildFSM(dataHandler?: ClickFSMHandler): void {
        if (this.states.length > 1) {
            return;
        }

        super.buildFSM(dataHandler);
        this.pressFSM.buildFSM();
        const clicked = new TerminalState<Event>(this, "clicked");
        const cancelled = new CancellingState<Event>(this, "cancelled");
        const pressed = new StdState<Event>(this, "pressed");

        this.addState(clicked);
        this.addState(cancelled);
        this.addState(pressed);

        new class extends SubFSMTransition<Event> { //Press Transition
            private readonly _parent: ClickFSM;

            public constructor(parent: ClickFSM, srcState: OutputState<Event>, tgtState: InputState<Event>, fsm: FSM<Event>) {
                super(srcState, tgtState, fsm);
                this._parent = parent;
            }

            protected action(event: Event): void {
                this._parent.setCheckButton(this._parent.pressFSM.getCheckButton());
            }
        }(this, this.initState, pressed, this.pressFSM);

        new class extends MoveTransition {
            private readonly _parent: ClickFSM;

            public constructor(parent: ClickFSM, srcState: OutputState<Event>, tgtState: InputState<Event>) {
                super(srcState, tgtState);
                this._parent = parent;
            }

            public isGuardOK(event: Event): boolean {
                return super.isGuardOK(event) && (this._parent.checkButton === undefined || event instanceof MouseEvent &&
                    event.button === this._parent.checkButton);
            }
        }(this, pressed, cancelled);

        new class extends ReleaseTransition {
            private readonly _parent: ClickFSM;

            public constructor(parent: ClickFSM, srcState: OutputState<Event>, tgtState: InputState<Event>) {
                super(srcState, tgtState);
                this._parent = parent;
            }

            public isGuardOK(event: Event): boolean {
                return super.isGuardOK(event) && (this._parent.checkButton === undefined || event instanceof MouseEvent &&
                    event.button === this._parent.checkButton);
            }

            protected action(event: Event): void {
                if (event.target !== null && isMouseUpEvent(event) && dataHandler !== undefined) {
                    dataHandler.onRelease(event);
                }
            }
        }(this, pressed, clicked);
    }

    public getCheckButton(): number {
        return this.checkButton === undefined ? -1 : this.checkButton;
    }

    public setCheckButton(buttonToCheck: number): void {
        if (this.checkButton === undefined) {
            this.checkButton = buttonToCheck;
        }
    }

    public reinit(): void {
        super.reinit();
        this.checkButton = undefined;
    }
}

interface ClickFSMHandler extends FSMDataHandler {
    onRelease(event: MouseEvent): void;
}

export class Click extends PointInteraction<PointData, ClickFSM, Node> {
    private readonly handler: ClickFSMHandler;
    public readonly press: Press;

    public constructor(fsm?: ClickFSM) {
        super(fsm === undefined ? new ClickFSM() : fsm);

        this.handler = new class implements ClickFSMHandler {
            private readonly _parent: Click;

            constructor(parent: Click) {
                this._parent = parent;
            }

            public onRelease(event: MouseEvent): void {
                this._parent.setPointData(event);
            }

            public reinitData(): void {
                this._parent.reinitData();
            }
        }(this);

        this.press = new Press(this.getFsm().pressFSM);
        this.getFsm().buildFSM(this.handler);
    }

    public reinitData(): void {
        super.reinitData();
        this.press.reinitData();
    }

    public getData(): PointData {
        return this;
    }
}

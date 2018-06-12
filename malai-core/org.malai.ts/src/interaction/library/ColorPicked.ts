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
import {TerminalState} from "../../src-core/fsm/TerminalState";
import {isColorChoice} from "../Events";
import {FSMDataHandler} from "../FSMDataHandler";
import {TSInteraction} from "../TSInteraction";
import {WidgetData} from "../../src-core/interaction/WidgetData";
import {ColorPickedTransition} from "../ColorPickedTransition";

export class ColorPickedFSM extends TSFSM<ColorPickedHandler> {
    public constructor() {
        super();
    }

    public buildFSM(dataHandler?: ColorPickedHandler): void {
        if (this.states.length > 1) {
            return ;
        }

        super.buildFSM(dataHandler);
        const picked: TerminalState<Event> = new TerminalState<Event>(this, "picked");
        this.addState(picked);

        new class extends ColorPickedTransition {
            public action(event: Event): void {
                if (event.target !== null && isColorChoice(event.target) && dataHandler !== undefined) {
                    dataHandler.initToPickedHandler(event);
                }
            }
        }(this.initState, picked);
    }
}


export interface ColorPickedHandler  extends FSMDataHandler {
    initToPickedHandler(event: Event): void;
}

/**
 * A user interaction for CheckBox
 * @author Gwendal DIDOT
 */

export class ColorPicked extends TSInteraction<WidgetData<Element>, ColorPickedFSM, Element> {
    private readonly handler: ColorPickedHandler;

    /**
     * Creates the interaction.
     */
    public constructor() {
        super(new ColorPickedFSM());

        this.handler = new class implements ColorPickedHandler {
            private readonly _parent: ColorPicked;

            constructor(parent: ColorPicked) {
                this._parent = parent;
            }

            public initToPickedHandler(event: Event): void {
                if (event.target !== null && isColorChoice(event.target)) {
                    this._parent._widget = event.currentTarget as Element;
                }
            }

            public reinitData(): void {
                this._parent.reinitData();
            }

        }(this);

        this.fsm.buildFSM(this.handler);
    }

    public onNewNodeRegistered(node: EventTarget): void {
        if (isColorChoice(node)) {
            this.registerActionHandlerInput(node);
        }
    }

    public onNodeUnregistered(node: EventTarget): void {
        if (isColorChoice(node)) {
            this.unregisterActionHandlerInput(node);
        }
    }

    public getData(): WidgetData<Element> {
        return this;
    }
}

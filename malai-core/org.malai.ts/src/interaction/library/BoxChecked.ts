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
import {TerminalState} from "../../../src-core/fsm/TerminalState";
import {BoxCheckPressedTransition} from "../BoxCheckPressedTransition";
import {isCheckBox} from "../Events";
import {FSMDataHandler} from "../FSMDataHandler";
import {TSInteraction} from "../TSInteraction";
import {WidgetData} from "../../../src-core/interaction/WidgetData";

/**
 * Class use to implement the FSM of {@link BoxChecked}, it's a single transition FSM.
 * @author Gwendal DIDOT
 */
class BoxCheckedFSM extends TSFSM<BoxCheckedHandler> {

    public constructor() {
        super();
    }

    /**
     *  Function use to build the FSM of the interaction {@link BoxChecked}. This function check if the FSM already have other state than
     *  initState, if not it's call {@link TSFSM.buildFSM()}, then add the different state of the FSM and finally the different transitions
     *  of the FSM, with override of existing methods, as well as new methods, in this transitions if needed.
     * @param {BoxCheckedHandler} dataHandler : This dataHandler is a instance of {@link BoxCheckedHandler}, it is use to change
     * the data of the widget the interaction is bind with.
     */
    public buildFSM(dataHandler?: BoxCheckedHandler): void {
        if (this.states.length > 1) {
            return ;
        }

        super.buildFSM(dataHandler);
        const checked: TerminalState<Event> = new TerminalState<Event>(this, "checked");
        this.addState(checked);

        new class extends BoxCheckPressedTransition {

            /**
             * Function use to check if event.target is a
             * {@link https://developer.mozilla.org/fr/docs/Web/HTML/Element/Input/checkbox HTML CheckBox Element},
             * and call {@link BoxCheckedHandler.initToCheckHandler}
             * @param {Event} event
             */
            public action(event: Event): void {
                if (event.target !== null && isCheckBox(event.target) && dataHandler !== undefined) {
                    dataHandler.initToCheckHandler(event);
                }
            }
        }(this.initState, checked);
    }
}

/**
 * Interface extending {@link FSMDataHandler}. It use to handle data of the widget the interaction is bind with.
 * @interface
 */
interface BoxCheckedHandler  extends FSMDataHandler {
    /**
     * This function is use to change the data the {@link BoxCheckedHandler} is handling.
     * @param {Event} event The event the user generate by interacting with the application/HTML element.
     */
    initToCheckHandler(event: Event): void;
}

/**
 * A user interaction for CheckBox.
 * @author Gwendal DIDOT
 */

export class BoxChecked extends TSInteraction<WidgetData<Element>, BoxCheckedFSM, Element> {
        private readonly handler: BoxCheckedHandler;

    /**
     * Creates the interaction.
     */
    public constructor() {
            super(new BoxCheckedFSM());

            this.handler = new class implements BoxCheckedHandler {
                private readonly _parent: BoxChecked;

                constructor(parent: BoxChecked) {
                    this._parent = parent;
                }

                /**
                 * Function implementing {@link BoxCheckedHandler.initToCheckHandler},
                 * it set the interaction {@link TSInteraction._widget widget} to the current target of event
                 * @param {Event} event The event the user generate by interacting with the application/HTML element.
                 */
                public initToCheckHandler(event: Event): void {
                    if (event.target !== null && isCheckBox(event.target)) {
                        this._parent._widget = event.currentTarget as Element;
                    }
                }

                /**
                 * Function use to reset the data of the widget bind to the interaction.
                 */
                public reinitData(): void {
                    this._parent.reinitData();
                }

            }(this);

            this.fsm.buildFSM(this.handler);
        }

        public onNewNodeRegistered(node: EventTarget): void {
            if (isCheckBox(node)) {
                this.registerActionHandler(node);
            }
        }

        public onNodeUnregistered(node: EventTarget): void {
            if (isCheckBox(node)) {
                this.unregisterActionHandler(node);
            }
        }

        public getData(): WidgetData<Element> {
            return this;
        }
}

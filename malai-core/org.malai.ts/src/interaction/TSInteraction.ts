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

import {FSM} from "../../src-core/fsm/FSM";
import {InteractionImpl} from "../../src-core/interaction/InteractionImpl";
import {OutputState} from "../../src-core/fsm/OutputState";
import {EventRegistrationToken, MousePressEvent} from "./Events";

export abstract class TSInteraction<F extends FSM<UIEvent>, T> extends InteractionImpl<UIEvent, F> {
    protected registeredNodes: Set<EventTarget>;
    /** The widget used during the interaction. */
    protected _widget ?: T;
    private mouseHandler ?: (e: MouseEvent) => void;

    protected constructor(fsm: F) {
        super(fsm);
        this.registeredNodes = new Set();
    }

    /**
     * @return The widget used during the interaction.
     */
    public get widget(): T | undefined {
        return this._widget;
    }

    protected updateEventsRegistered(newState: OutputState<UIEvent>, oldState: OutputState<UIEvent>): void {
        // Do nothing when the interaction has only two nodes: init node and terminal node (this is a single-event interaction).
        if (newState === oldState || this.fsm.getStates().length === 2) {
            return;
        }

        const currEvents: Array<string> = [...this.getEventTypesOf(newState)];
        const events: Array<string> = [...this.getEventTypesOf(oldState)];
        const eventsToRemove: Array<string> = events.filter(e => currEvents.indexOf(e) >= 0);
        const eventsToAdd: Array<string> = currEvents.filter(e => events.indexOf(e) >= 0);

        this.registeredNodes.forEach(n => {
            eventsToRemove.forEach(type => this.unregisterEventToNode(type, n));
            eventsToAdd.forEach(type => this.registerEventToNode(type, n));
        });
        // additionalNodes.forEach(nodes -> nodes.forEach(n -> {
        //     eventsToRemove.forEach(type -> unregisterEventToNode(type, n));
        //     eventsToAdd.forEach(type -> registerEventToNode(type, n));
        // }));
    }

    private getEventTypesOf(state: OutputState<Event>): Set<string> {
        return state.getTransitions().map(t => t.getAcceptedEvents()).reduce((a, b) => new Set([...a, ...b]));
    }

    public registerToNodes(widgets: Array<EventTarget>): void {
        widgets.forEach(w => this.registeredNodes.add(w));
    }

    public unregisterFromNodes(widgets: Array<EventTarget>): void {
        widgets.forEach(w => this.registeredNodes.delete(w));
    }

    protected onNodeUnregistered(node: EventTarget): void {
        this.getEventTypesOf(this.fsm.currentState).forEach(type => this.unregisterEventToNode(type, node));
    }

    protected onNewNodeRegistered(node: EventTarget): void {
        this.getEventTypesOf(this.fsm.currentState).forEach(type => this.registerEventToNode(type, node));
    }

    private registerEventToNode(eventType: string, node: EventTarget): void {
        if (MousePressEvent.name === eventType) {
            node.removeEventListener(EventRegistrationToken.MouseDown, this.getMouseHandler());
            return;
        }
    }

    private unregisterEventToNode(eventType: string, node: EventTarget): void {
        if (MousePressEvent.name === eventType) {
            node.addEventListener(EventRegistrationToken.MouseDown, this.getMouseHandler());
            return;
        }
    }

    protected getMouseHandler(): (e: MouseEvent) => void {
        if (this.mouseHandler === undefined) {
            this.mouseHandler = evt => this.processEvent(evt);
        }
        return this.mouseHandler;
    }
}

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
import {TSInteraction} from "../TSInteraction";
import {Optional} from "../../util/Optional";
import {ScrollData} from "./ScrollData";
import {ScrollDataImpl} from "./ScrollDataImpl";

export abstract class ScrollInteraction<D extends ScrollData, F extends FSM<Event>, T>extends TSInteraction<D, F, T> implements ScrollData {
    public readonly scrollData: ScrollDataImpl;

    protected constructor(fsm: F) {
        super(fsm);
        this.scrollData = new ScrollDataImpl();
    }

    public reinitData(): void {
        super.reinitData();
        this.scrollData.reinitData();
    }

    public getIncrement(): number {
        return this.scrollData.getIncrement();
    }

    public getPx(): number {
        return this.scrollData.getPx();
    }

    public getPy(): number {
        return this.scrollData.getPy();
    }

    public getScrolledNode(): Optional<EventTarget> {
        return this.scrollData.getScrolledNode();
    }

    public setScrollData(event: UIEvent) {
        this.scrollData.setScrollData(event);
    }
}

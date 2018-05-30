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

import {ScrollData} from "./ScrollData";
import {Optional} from "../../util/Optional";

export class ScrollDataImpl implements ScrollData {

    protected scrolledNode: EventTarget | undefined;

    protected px: number | undefined;

    protected py: number | undefined;

    protected increment: number | undefined;

    public constructor() {}

    public reinitData(): void {
        this.increment = undefined;
        this.px = undefined;
        this.py = undefined;
        this.scrolledNode = undefined;
    }

    public getIncrement(): number {
        return this.increment === undefined ? 0 : this.increment;
    }

    public getPx(): number {
        return this.px === undefined ? 0 : this.px;
    }

    public getPy(): number {
        return this.py === undefined ? 0 : this.py;
    }

    public getScrolledNode(): Optional<EventTarget> {
        return Optional.ofNullable(this.scrolledNode);
    }

    public setScrollData(event: UIEvent) {
        this.scrolledNode = event.target === null ? undefined : event.target;
        this.increment = this.getIncrement() + (event.view.scrollY === undefined
        || event.view.scrollY < this.getIncrement() ? 0 : event.view.scrollY
            - (this.py === undefined || event.view.scrollY  < this.getIncrement() ? 0 : this.py));
        this.px = event.view.scrollX;
        this.py = event.view.scrollY;
    }
}

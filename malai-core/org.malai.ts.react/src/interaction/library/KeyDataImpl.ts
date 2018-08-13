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

import {Optional} from "../../util/Optional";
import {KeyData} from "./KeyData";

export class KeyDataImpl implements KeyData {
    /**
     * The key involve in the interaction
     */
    protected key: String | undefined;

    /**
     * The target of the event that trigger the interaction
     */
    protected target : EventTarget | undefined;

    public constructor() {}

    public reinitData(): void {
        this.key = undefined;
        this.target = undefined;
    }

    public getTarget(): Optional<EventTarget> {
        return Optional.ofNullable(this.target);
    }

    public getKey(): String {
        return this.key === undefined ? "" : this.key;
    }

    public setKeyData(event: KeyboardEvent): void {
        this.target = event.target === null ? undefined : event.target;
        this.key =  event.code;
    }
}

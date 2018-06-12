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

import {MArray} from "../../util/ArrayUtil";

/**
 * A simple value property. We do not use the JFX one (SimpleObjectProperty) since
 * this library is transpiled to other languages where SimpleObjectProperty may not be supported.
 * This also avoids a dependency to JFX.
 * @param <T> The type of the contained object.
 * @param {*} value
 * @class
 */
export class ObsValue<T> {
    private value: T;

    private readonly handlers: MArray<(oldValue: T, newValue: T) => void>;

    public constructor(value: T) {
        this.value = value;
        this.handlers = new MArray();
    }

    public get(): T {
        return this.value;
    }

    public set(value: T): void {
        if (value !== undefined) {
            const oldValue: T = this.value;
            this.value = value;
            this.notifyChange(oldValue, value);
        }
    }

    private notifyChange(oldValue: T, newValue: T): void {
        this.handlers.forEach(handler => handler(oldValue, newValue));
    }

    public obs(handler: (oldValue: T, newValue: T) => void): void {
        this.handlers.push(handler);
    }

    public unobsAll(): void {
        this.handlers.clear();
    }

    public unobs(handler: (oldValue: T, newValue: T) => void): void {
        this.handlers.remove(handler);
    }
}


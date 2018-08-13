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

export class MArray<T> extends Array<T> {
    public constructor(...items: Array<T>) {
        super(...items);
        Object.setPrototypeOf(this, MArray.prototype);
    }

    public remove(elt: T): boolean {
        const index = this.indexOf(elt);
        if (index > -1) {
            this.splice(index, 1);
            return true;
        }
        return false;
    }

    public removeAt(index: number): T | undefined {
        if (index > -1) {
            return this.splice(index, 1)[0];
        }
        return undefined;
    }

    public isEmpty(): boolean {
        return this.length === 0;
    }

    public peek(): T | undefined {
        return this.length === 0 ? undefined : this[this.length - 1];
    }

    public clear(): void {
        this.length = 0;
    }
}

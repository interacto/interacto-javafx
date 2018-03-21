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

interface Array<T> {
    remove(elt: T): boolean;

    removeAt(index: number): T | undefined;

    isEmpty(): boolean;

    peek(): T | undefined;

    clear(): void;
}


Array.prototype.clear = function (): void {
    this.length = 0;
};

Array.prototype.peek = function (): Object | undefined {
    return this.length === 0 ? undefined : this[this.length - 1];
};

Array.prototype.isEmpty = function (): boolean {
    return this.length === 0;
};

Array.prototype.remove = function (elt: Object): boolean {
    const index = this.indexOf(elt);
    if (index > -1) {
        this.splice(index, 1);
        return true;
    }
    return false;
};

Array.prototype.removeAt = function (index: number): Object | undefined {
    if (index > -1) {
        return this.splice(index, 1)[0];
    }
    return undefined;
};

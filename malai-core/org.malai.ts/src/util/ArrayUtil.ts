interface Array<T> {
    remove(elt: T): boolean

    removeAt(index: number): T | undefined

    isEmpty(): boolean

    peek(): T | undefined

    clear(): void;
}


Array.prototype.clear = function (): void {
    this.length = 0;
};

Array.prototype.peek = function (): any | undefined {
    return this.length == 0 ? undefined : this[this.length - 1];
};

Array.prototype.isEmpty = function (): boolean {
    return this.length == 0;
};

Array.prototype.remove = function (elt: any): boolean {
    const index = this.indexOf(elt);
    if (index > -1) {
        this.splice(index, 1);
        return true;
    }
    return false;
};

Array.prototype.removeAt = function (index: number): any | undefined {
    if (index > -1) {
        return this.splice(index, 1)[0];
    }
    return undefined;
};

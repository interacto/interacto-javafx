export declare class MArray<T> extends Array<T> {
    constructor(...items: Array<T>);
    remove(elt: T): boolean;
    removeAt(index: number): T | undefined;
    isEmpty(): boolean;
    peek(): T | undefined;
    clear(): void;
}

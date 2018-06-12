export declare class ObsValue<T> {
    private value;
    private readonly handlers;
    constructor(value: T);
    get(): T;
    set(value: T): void;
    private notifyChange(oldValue, newValue);
    obs(handler: (oldValue: T, newValue: T) => void): void;
    unobsAll(): void;
    unobs(handler: (oldValue: T, newValue: T) => void): void;
}

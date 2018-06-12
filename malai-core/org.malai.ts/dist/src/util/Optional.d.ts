export declare class Optional<T> {
    private static readonly EMPTY;
    private readonly value;
    private constructor();
    static empty<T>(): Optional<T>;
    static of<T>(obj: T): Optional<T>;
    static ofNullable<T>(obj: T | undefined): Optional<T>;
    get(): T | undefined;
    isPresent(): boolean;
    ifPresent(lambda: (t: T) => void): void;
    filter(predicate: (obj: T) => boolean): Optional<T>;
    map<U>(fct: (t: T) => U): Optional<U>;
}

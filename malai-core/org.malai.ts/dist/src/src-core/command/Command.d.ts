export interface Command {
    flush(): void;
    getRegistrationPolicy(): RegistrationPolicy;
    doIt(): boolean;
    canDo(): boolean;
    hadEffect(): boolean;
    unregisteredBy(cmd: Command): boolean;
    done(): void;
    isDone(): boolean;
    cancel(): void;
    getStatus(): CmdStatus;
    followingCmds(): Array<Command>;
}
export declare enum RegistrationPolicy {
    NONE = 0,
    UNLIMITED = 1,
    LIMITED = 2,
}
export declare enum CmdStatus {
    CREATED = 0,
    EXECUTED = 1,
    CANCELLED = 2,
    DONE = 3,
    FLUSHED = 4,
}

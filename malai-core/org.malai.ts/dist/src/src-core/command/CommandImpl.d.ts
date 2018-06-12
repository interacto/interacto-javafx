import { CmdStatus, Command, RegistrationPolicy } from "./Command";
export declare abstract class CommandImpl implements Command {
    protected status: CmdStatus;
    protected constructor();
    flush(): void;
    protected createMemento(): void;
    doIt(): boolean;
    protected abstract doCmdBody(): void;
    getRegistrationPolicy(): RegistrationPolicy;
    hadEffect(): boolean;
    unregisteredBy(cmd: Command): boolean;
    done(): void;
    isDone(): boolean;
    cancel(): void;
    getStatus(): CmdStatus;
    followingCmds(): Array<Command>;
    abstract canDo(): boolean;
}

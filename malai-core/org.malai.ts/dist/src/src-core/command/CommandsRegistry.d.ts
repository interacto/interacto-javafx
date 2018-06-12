import { Command } from "./Command";
import { CommandHandler } from "./CommandHandler";
export declare class CommandsRegistry {
    static readonly INSTANCE: CommandsRegistry;
    private readonly cmds;
    private readonly handlers;
    private sizeMax;
    constructor();
    getHandlers(): Array<CommandHandler>;
    onActionExecuted(cmd: Command): void;
    onActionDone(cmd: Command): void;
    getActions(): Array<Command>;
    unregisterCmd(cmd: Command): void;
    addCommand(cmd: Command, cmdHandler?: CommandHandler): void;
    removeAction(cmd: Command): void;
    addHandler(handler: CommandHandler): void;
    removeHandler(handler: CommandHandler): void;
    removeAllHandlers(): void;
    clear(): void;
    cancelAction(cmd: Command): void;
    getSizeMax(): number;
    setSizeMax(newSizeMax: number): void;
}

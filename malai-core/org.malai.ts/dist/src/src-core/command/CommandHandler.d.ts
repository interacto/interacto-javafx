import { UndoHandler } from "../undo/UndoHandler";
import { Command } from "./Command";
export interface CommandHandler extends UndoHandler {
    onCmdAdded(cmd: Command): void;
    onCmdCancelled(cmd: Command): void;
    onCmdExecuted(cmd: Command): void;
    onCmdDone(cmd: Command): void;
}

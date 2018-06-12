import { Undoable } from "./Undoable";
import { UndoHandler } from "./UndoHandler";
export declare class EmptyUndoHandler implements UndoHandler {
    constructor();
    onUndoableCleared(): void;
    onUndoableAdded(undoable: Undoable): void;
    onUndoableUndo(undoable: Undoable): void;
    onUndoableRedo(undoable: Undoable): void;
}

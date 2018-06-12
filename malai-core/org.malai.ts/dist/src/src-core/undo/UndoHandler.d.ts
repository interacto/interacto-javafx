import { Undoable } from "./Undoable";
export interface UndoHandler {
    onUndoableCleared(): void;
    onUndoableAdded(undoable: Undoable): void;
    onUndoableUndo(undoable: Undoable): void;
    onUndoableRedo(undoable: Undoable): void;
}

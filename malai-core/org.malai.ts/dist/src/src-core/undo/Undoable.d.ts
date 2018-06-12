export interface Undoable {
    undo(): void;
    redo(): void;
    getUndoName(): string;
}
export declare function isUndoableType(obj: Undoable | Object): obj is Undoable;

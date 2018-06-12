export interface Modifiable {
    setModified(modified: boolean): void;
    isModified(): boolean;
}

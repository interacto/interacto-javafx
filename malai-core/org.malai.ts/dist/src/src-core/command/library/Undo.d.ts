import { CommandImpl } from "../CommandImpl";
export declare class Undo extends CommandImpl {
    constructor();
    canDo(): boolean;
    protected doCmdBody(): void;
}

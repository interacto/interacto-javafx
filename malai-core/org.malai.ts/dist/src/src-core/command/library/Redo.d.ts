import { CommandImpl } from "../CommandImpl";
export declare class Redo extends CommandImpl {
    constructor();
    canDo(): boolean;
    protected doCmdBody(): void;
}

import { CommandImpl } from "../../src/src-core/command/CommandImpl";
export declare class StubCmd extends CommandImpl {
    constructor();
    protected doCmdBody(): void;
    canDo(): boolean;
}

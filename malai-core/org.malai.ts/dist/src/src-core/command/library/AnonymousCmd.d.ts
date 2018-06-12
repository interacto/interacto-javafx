import { CommandImpl } from "../CommandImpl";
export declare class AnonymousCmd extends CommandImpl {
    protected cmdBody: (() => void) | undefined;
    constructor();
    protected doCmdBody(): void;
    canDo(): boolean;
    setCmdBody(body: () => void): void;
    getCmdBody(): (() => void) | undefined;
}

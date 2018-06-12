import { CommandImpl } from "./CommandImpl";
export declare class AnonCmd extends CommandImpl {
    private readonly exec;
    constructor(fct: () => void);
    canDo(): boolean;
    protected doCmdBody(): void;
}

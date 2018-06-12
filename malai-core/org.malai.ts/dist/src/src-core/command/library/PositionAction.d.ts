import { CommandImpl } from "../CommandImpl";
export declare abstract class PositionAction extends CommandImpl {
    protected px: number;
    protected py: number;
    protected constructor();
    canDo(): boolean;
    setPx(px: number): void;
    setPy(py: number): void;
}

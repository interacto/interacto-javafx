import { FSMDataHandler } from "../FSMDataHandler";
import { TSFSM } from "../TSFSM";
import { DoubleClickFSM } from "./DoubleClick";
import { PointInteraction } from "./PointInteraction";
import { SrcTgtPointsData } from "./SrcTgtPointsData";
import { Optional } from "../../util/Optional";
export declare class DragLockFSM extends TSFSM<DragLockFSMHandler> {
    readonly firstDbleClick: DoubleClickFSM;
    readonly sndDbleClick: DoubleClickFSM;
    protected checkButton: number | undefined;
    constructor();
    buildFSM(dataHandler: DragLockFSMHandler): void;
}
export interface DragLockFSMHandler extends FSMDataHandler {
    onMove(event: MouseEvent): void;
}
export declare class DragLock extends PointInteraction<SrcTgtPointsData, DragLockFSM, Event> implements SrcTgtPointsData {
    private readonly handler;
    private readonly firstClick;
    private readonly sndClick;
    constructor();
    reinitData(): void;
    getData(): SrcTgtPointsData;
    getTgtObject(): Optional<EventTarget>;
    getTgtClientX(): number;
    getTgtClientY(): number;
    getTgtScreenX(): number;
    getTgtScreenY(): number;
    isAltPressed(): boolean;
    isCtrlPressed(): boolean;
    isShiftPressed(): boolean;
    isMetaPressed(): boolean;
    getButton(): number | undefined;
}

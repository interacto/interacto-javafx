import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { Click, ClickFSM } from "./Click";
import { TSInteraction } from "../TSInteraction";
import { PointData } from "./PointData";
export declare class DoubleClickFSM extends TSFSM<FSMDataHandler> {
    private static timeGap;
    private static readonly SUPPLY_TIME_GAP;
    static getTimeGap(): number;
    static setTimeGap(timeGapBetweenClicks: number): void;
    readonly firstClickFSM: ClickFSM;
    private readonly sndClick;
    private checkButton;
    constructor();
    buildFSM(dataHandler?: FSMDataHandler): void;
    setCheckButton(buttonToCheck: number): void;
    getCheckButton(): number;
    reinit(): void;
}
export declare class DoubleClick extends TSInteraction<PointData, DoubleClickFSM, Node> {
    readonly firstClick: Click;
    constructor(fsm?: DoubleClickFSM);
    reinitData(): void;
    getData(): PointData;
}

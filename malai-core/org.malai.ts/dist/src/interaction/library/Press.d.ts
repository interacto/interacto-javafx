import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { PointInteraction } from "./PointInteraction";
import { PointData } from "./PointData";
export declare class PressFSM extends TSFSM<PressFSMHandler> {
    private checkButton;
    constructor();
    buildFSM(dataHandler?: PressFSMHandler): void;
    setCheckButton(buttonToCheck: number): void;
    getCheckButton(): number;
}
export interface PressFSMHandler extends FSMDataHandler {
    initToPress(event: Event): void;
}
export declare class Press extends PointInteraction<PointData, PressFSM, Node> {
    private readonly handler;
    constructor(fsm?: PressFSM);
    getData(): PointData;
}

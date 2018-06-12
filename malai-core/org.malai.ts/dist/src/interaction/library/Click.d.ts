import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { PointInteraction } from "./PointInteraction";
import { PointData } from "./PointData";
export declare class ClickFSM extends TSFSM<ClickFSMHandler> {
    private checkButton;
    constructor();
    buildFSM(dataHandler?: ClickFSMHandler): void;
    getCheckButton(): number;
    setCheckButton(buttonToCheck: number): void;
    reinit(): void;
}
export interface ClickFSMHandler extends FSMDataHandler {
    initToClicked(event: MouseEvent): void;
}
export declare class Click extends PointInteraction<PointData, ClickFSM, Node> {
    private readonly handler;
    constructor(fsm?: ClickFSM);
    getData(): PointData;
}

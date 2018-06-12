import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { InteractionData } from "../../src-core/interaction/InteractionData";
export declare class WindowClosedFSM extends TSFSM<WindowClosedFSMHandler> {
    private checkButton;
    constructor();
    buildFSM(dataHandler?: WindowClosedFSMHandler): void;
    setCheckButton(buttonToCheck: number): void;
    getCheckButton(): number;
}
export interface WindowClosedFSMHandler extends FSMDataHandler {
    initToCloseHandler(event: Event): void;
}
export declare class WindowClosed extends TSInteraction<InteractionData, WindowClosedFSM, Node> {
    private readonly handler;
    constructor(fsm?: WindowClosedFSM);
    getData(): InteractionData;
}

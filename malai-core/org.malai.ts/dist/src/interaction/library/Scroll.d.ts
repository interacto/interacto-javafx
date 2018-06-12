import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { ScrollInteraction } from "./ScrollInteraction";
import { ScrollData } from "./ScrollData";
export declare class ScrollFSM extends TSFSM<ScrollFSMHandler> {
    constructor();
    buildFSM(dataHandler?: ScrollFSMHandler): void;
}
export interface ScrollFSMHandler extends FSMDataHandler {
    initToScroll(event: Event): void;
}
export declare class Scroll extends ScrollInteraction<ScrollData, ScrollFSM, undefined> {
    private readonly handler;
    constructor(fsm?: ScrollFSM);
    getData(): ScrollData;
}

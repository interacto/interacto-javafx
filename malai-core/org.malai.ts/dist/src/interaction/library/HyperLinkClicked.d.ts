import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class HyperLinkClickedFSM extends TSFSM<HyperLinkClickedFSMHandler> {
    constructor();
    buildFSM(dataHandler?: HyperLinkClickedFSMHandler): void;
}
export interface HyperLinkClickedFSMHandler extends FSMDataHandler {
    initToClickedHandler(event: Event): void;
}
export declare class HyperLinkClicked extends TSInteraction<WidgetData<Element>, HyperLinkClickedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

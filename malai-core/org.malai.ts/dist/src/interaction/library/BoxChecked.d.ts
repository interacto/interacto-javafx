import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class BoxCheckedFSM extends TSFSM<BoxCheckedHandler> {
    constructor();
    buildFSM(dataHandler?: BoxCheckedHandler): void;
}
export interface BoxCheckedHandler extends FSMDataHandler {
    initToCheckHandler(event: Event): void;
}
export declare class BoxChecked extends TSInteraction<WidgetData<Element>, BoxCheckedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

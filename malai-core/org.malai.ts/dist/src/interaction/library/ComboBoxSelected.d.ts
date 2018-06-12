import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class ComboBoxSelectedFSM extends TSFSM<ComboBoxSelectedHandler> {
    constructor();
    buildFSM(dataHandler?: ComboBoxSelectedHandler): void;
}
export interface ComboBoxSelectedHandler extends FSMDataHandler {
    initToSelectedHandler(event: Event): void;
}
export declare class ComboBoxSelected extends TSInteraction<WidgetData<Element>, ComboBoxSelectedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

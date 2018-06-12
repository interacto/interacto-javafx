import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class ChoiceBoxSelectedSFM extends TSFSM<ChoiceBoxSelectedHandler> {
    constructor();
    buildFSM(dataHandler?: ChoiceBoxSelectedHandler): void;
}
export interface ChoiceBoxSelectedHandler extends FSMDataHandler {
    initToSelectedHandler(event: Event): void;
}
export declare class ChoiceBoxSelected extends TSInteraction<WidgetData<Element>, ChoiceBoxSelectedSFM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

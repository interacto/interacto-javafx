import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class SpinnerChangedFSM extends TSFSM<SpinnerChangedHandler> {
    constructor();
    buildFSM(dataHandler?: SpinnerChangedHandler): void;
}
export interface SpinnerChangedHandler extends FSMDataHandler {
    initToChangedHandler(event: Event): void;
}
export declare class SpinnerChanged extends TSInteraction<WidgetData<Element>, SpinnerChangedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

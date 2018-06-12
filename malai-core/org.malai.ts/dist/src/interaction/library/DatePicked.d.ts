import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class DatePickedFSM extends TSFSM<DatePickedHandler> {
    constructor();
    buildFSM(dataHandler?: DatePickedHandler): void;
}
export interface DatePickedHandler extends FSMDataHandler {
    initToPickedHandler(event: Event): void;
}
export declare class DatePicked extends TSInteraction<WidgetData<Element>, DatePickedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

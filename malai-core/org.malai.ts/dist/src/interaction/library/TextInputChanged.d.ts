import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class TextInputChangedFSM extends TSFSM<TextInputChangedHandler> {
    private static readonly timeGap;
    private static readonly SUPPLY_TIME_GAP;
    static getTimeGap(): number;
    constructor();
    buildFSM(dataHandler?: TextInputChangedHandler): void;
}
export interface TextInputChangedHandler extends FSMDataHandler {
    initToChangedHandler(event: Event): void;
}
export declare class TextInputChanged extends TSInteraction<WidgetData<Element>, TextInputChangedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

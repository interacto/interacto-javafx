import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class ColorPickedFSM extends TSFSM<ColorPickedHandler> {
    constructor();
    buildFSM(dataHandler?: ColorPickedHandler): void;
}
export interface ColorPickedHandler extends FSMDataHandler {
    initToPickedHandler(event: Event): void;
}
export declare class ColorPicked extends TSInteraction<WidgetData<Element>, ColorPickedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

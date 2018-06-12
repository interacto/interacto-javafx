import { FSMDataHandler } from "../FSMDataHandler";
import { TSFSM } from "../TSFSM";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class ButtonPressedFSM extends TSFSM<ButtonPressedFSMHandler> {
    constructor();
    buildFSM(dataHandler?: ButtonPressedFSMHandler): void;
}
export interface ButtonPressedFSMHandler extends FSMDataHandler {
    initToPressedHandler(event: Event): void;
}
export declare class ButtonPressed extends TSInteraction<WidgetData<Element>, ButtonPressedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

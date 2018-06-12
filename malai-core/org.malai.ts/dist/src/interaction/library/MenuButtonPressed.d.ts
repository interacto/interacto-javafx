import { FSMDataHandler } from "../FSMDataHandler";
import { TSFSM } from "../TSFSM";
import { TSInteraction } from "../TSInteraction";
import { WidgetData } from "../../src-core/interaction/WidgetData";
export declare class MenuButtonPressedFSM extends TSFSM<MenuButtonPressedFSMHandler> {
    constructor();
    buildFSM(dataHandler?: MenuButtonPressedFSMHandler): void;
}
export interface MenuButtonPressedFSMHandler extends FSMDataHandler {
    initToPressedHandler(event: Event): void;
}
export declare class MenuButtonPressed extends TSInteraction<WidgetData<Element>, MenuButtonPressedFSM, Element> {
    private readonly handler;
    constructor();
    onNewNodeRegistered(node: EventTarget): void;
    onNodeUnregistered(node: EventTarget): void;
    getData(): WidgetData<Element>;
}

import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { KeyData } from "./KeyData";
import { KeyInteraction } from "./KeyInteraction";
export declare class KeyPressedFSM extends TSFSM<KeyPressedFSMHandler> {
    private readonly modifiersAccepted;
    constructor(modifierAccepted: boolean);
    buildFSM(dataHandler?: KeyPressedFSMHandler): void;
    reinit(): void;
}
export interface KeyPressedFSMHandler extends FSMDataHandler {
    onKeyPressed(event: Event): void;
}
export declare class KeyPressed extends KeyInteraction<KeyData, KeyPressedFSM, Node> {
    private readonly handler;
    constructor(modifierAccepted: boolean, fsm?: KeyPressedFSM);
    getData(): KeyData;
}

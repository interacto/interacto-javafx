import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { KeyData } from "./KeyData";
import { KeyInteraction } from "./KeyInteraction";
export declare class KeyTypedFSM extends TSFSM<KeyTypedFSMHandler> {
    private checkKey;
    private static readonly timeGap;
    private static readonly SUPPLY_TIME_GAP;
    constructor();
    buildFSM(dataHandler?: KeyTypedFSMHandler): void;
    getCheckKey(): string;
    setCheckKey(keyToCheck: string): void;
    reinit(): void;
    private static getTimeGap();
}
export interface KeyTypedFSMHandler extends FSMDataHandler {
    onKeyTyped(event: Event): void;
}
export declare class KeyTyped extends KeyInteraction<KeyData, KeyTypedFSM, Node> {
    private readonly handler;
    constructor(fsm?: KeyTypedFSM);
    getData(): KeyData;
}

import { KeyData } from "./KeyData";
import { FSM } from "../../src-core/fsm/FSM";
import { TSInteraction } from "../TSInteraction";
import { Optional } from "../../util/Optional";
import { KeyDataImpl } from "./KeyDataImpl";
export declare abstract class KeyInteraction<D extends KeyData, F extends FSM<Event>, T> extends TSInteraction<D, F, T> implements KeyData {
    readonly keyData: KeyDataImpl;
    protected constructor(fsm: F);
    reinitData(): void;
    setKeyData(event: KeyboardEvent): void;
    getKey(): String;
    getTarget(): Optional<EventTarget>;
}

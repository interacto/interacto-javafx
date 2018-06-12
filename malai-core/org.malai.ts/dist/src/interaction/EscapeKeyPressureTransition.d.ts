import { InputState } from "../src-core/fsm/InputState";
import { OutputState } from "../src-core/fsm/OutputState";
import { KeyPressureTransition } from "./KeyPressureTransition";
export declare class EscapeKeyPressureTransition extends KeyPressureTransition {
    constructor(srcState: OutputState<Event>, tgtState: InputState<Event>);
    isGuardOK(event: Event): boolean;
}

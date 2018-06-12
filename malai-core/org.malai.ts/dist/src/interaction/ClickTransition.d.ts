import { TSTransition } from "./TSTransition";
import { InputState } from "../src-core/fsm/InputState";
import { OutputState } from "../src-core/fsm/OutputState";
export declare class ClickTransition extends TSTransition {
    constructor(srcState: OutputState<Event>, tgtState: InputState<Event>);
    accept(event: Event): boolean;
    isGuardOK(event: Event): boolean;
    getAcceptedEvents(): Set<string>;
}

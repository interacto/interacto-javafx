import { Transition } from "../../src/src-core/fsm/Transition";
import { StubEvent } from "./StubEvent";
import { InputState } from "../../src/src-core/fsm/InputState";
import { OutputState } from "../../src/src-core/fsm/OutputState";
export declare class StubTransitionOK extends Transition<StubEvent> {
    guard: boolean;
    constructor(srcState: OutputState<StubEvent>, tgtState: InputState<StubEvent>, guard?: boolean);
    accept(event: StubEvent): boolean;
    isGuardOK(event: StubEvent): boolean;
    getAcceptedEvents(): Set<string>;
}
export declare class SubStubTransition1 extends StubTransitionOK {
    constructor(srcState: OutputState<StubEvent>, tgtState: InputState<StubEvent>, guard?: boolean);
    accept(event: StubEvent): boolean;
    getAcceptedEvents(): Set<string>;
}
export declare class SubStubTransition2 extends StubTransitionOK {
    constructor(srcState: OutputState<StubEvent>, tgtState: InputState<StubEvent>, guard?: boolean);
    accept(event: StubEvent): boolean;
    getAcceptedEvents(): Set<string>;
}
export declare class SubStubTransition3 extends StubTransitionOK {
    constructor(srcState: OutputState<StubEvent>, tgtState: InputState<StubEvent>, guard?: boolean);
    accept(event: StubEvent): boolean;
    getAcceptedEvents(): Set<string>;
}

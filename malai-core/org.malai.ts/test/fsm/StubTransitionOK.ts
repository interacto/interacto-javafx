import {Transition} from "../../src-core/fsm/Transition";
import {StubEvent, StubSubEvent1, StubSubEvent2, StubSubEvent3} from "./StubEvent";
import {InputState} from "../../src-core/fsm/InputState";
import {OutputState} from "../../src-core/fsm/OutputState";

export class StubTransitionOK extends Transition<StubEvent> {
    public guard: boolean;

    public constructor(srcState: OutputState<StubEvent>, tgtState: InputState<StubEvent>, guard ?: boolean) {
        super(srcState, tgtState);
        this.guard = guard === undefined ? true : guard;
    }

    public accept(event: StubEvent): boolean {
        return true;
    }

    public isGuardOK(event: StubEvent): boolean {
        return this.guard;
    }

    public getAcceptedEvents(): Set<string> {
        return new Set(["StubEvent"]);
    }
}

export class SubStubTransition1 extends StubTransitionOK {
    public constructor(srcState: OutputState<StubEvent>, tgtState: InputState<StubEvent>, guard ?: boolean) {
        super(srcState, tgtState, guard);
    }

    public accept(event: StubEvent): boolean {
        return event instanceof StubSubEvent1;
    }

    public getAcceptedEvents(): Set<string> {
        return new Set("StubSubEvent1");
    }
}

export class SubStubTransition2 extends StubTransitionOK {
    public constructor(srcState: OutputState<StubEvent>, tgtState: InputState<StubEvent>, guard ?: boolean) {
        super(srcState, tgtState, guard);
    }

    public accept(event: StubEvent): boolean {
        return event instanceof StubSubEvent2;
    }

    public getAcceptedEvents(): Set<string> {
        return new Set("StubSubEvent2");
    }
}

export class SubStubTransition3 extends StubTransitionOK {
    public constructor(srcState: OutputState<StubEvent>, tgtState: InputState<StubEvent>, guard ?: boolean) {
        super(srcState, tgtState, guard);
    }

    public accept(event: StubEvent): boolean {
        return event instanceof StubSubEvent3;
    }

    public getAcceptedEvents(): Set<string> {
        return new Set("StubSubEvent3");
    }
}

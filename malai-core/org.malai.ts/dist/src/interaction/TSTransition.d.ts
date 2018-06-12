import { Transition } from "../src-core/fsm/Transition";
import { OutputState } from "../src-core/fsm/OutputState";
import { InputState } from "../src-core/fsm/InputState";
export declare abstract class TSTransition extends Transition<Event> {
    protected constructor(srcState: OutputState<Event>, tgtState: InputState<Event>);
}

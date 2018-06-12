import { Transition } from "./Transition";
import { OutputState } from "./OutputState";
import { InputState } from "./InputState";
export declare class EpsilonTransition<E> extends Transition<E> {
    constructor(srcState: OutputState<E>, tgtState: InputState<E>);
    accept(event: E): boolean;
    isGuardOK(event: E): boolean;
    getAcceptedEvents(): Set<string>;
}

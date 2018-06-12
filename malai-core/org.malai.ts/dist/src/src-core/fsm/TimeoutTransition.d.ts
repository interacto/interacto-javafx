import { Transition } from "./Transition";
import { OutputState } from "./OutputState";
import { InputState } from "./InputState";
import { Optional } from "../../util/Optional";
export declare class TimeoutTransition<E> extends Transition<E> {
    private readonly timeoutDuration;
    private timeoutThread;
    private timeouted;
    constructor(srcState: OutputState<E>, tgtState: InputState<E>, timeout: () => number);
    startTimeout(): void;
    stopTimeout(): void;
    accept(event: E | undefined): boolean;
    isGuardOK(event: E | undefined): boolean;
    execute(event?: E): Optional<InputState<E>>;
    getAcceptedEvents(): Set<string>;
}

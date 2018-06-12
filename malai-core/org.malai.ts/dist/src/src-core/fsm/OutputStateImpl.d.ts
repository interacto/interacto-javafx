import { StateImpl } from "./StateImpl";
import { OutputState } from "./OutputState";
import { Transition } from "./Transition";
import { FSM } from "./FSM";
import { MArray } from "../../util/ArrayUtil";
export declare abstract class OutputStateImpl<E> extends StateImpl<E> implements OutputState<E> {
    protected readonly transitions: MArray<Transition<E>>;
    protected constructor(stateMachine: FSM<E>, stateName: string);
    process(event: E): boolean;
    checkStartingState(): void;
    clearTransitions(): void;
    getTransitions(): Array<Transition<E>>;
    addTransition(tr: Transition<E>): void;
    abstract exit(): void;
    uninstall(): void;
}

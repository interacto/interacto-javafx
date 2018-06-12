import { OutputStateImpl } from "./OutputStateImpl";
import { InputState } from "./InputState";
import { FSM } from "./FSM";
export declare class StdState<E> extends OutputStateImpl<E> implements InputState<E> {
    constructor(stateMachine: FSM<E>, stateName: string);
    checkStartingState(): void;
    enter(): void;
    exit(): void;
}

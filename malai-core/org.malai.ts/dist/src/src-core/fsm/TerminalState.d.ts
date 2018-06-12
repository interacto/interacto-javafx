import { StateImpl } from "./StateImpl";
import { InputState } from "./InputState";
import { FSM } from "./FSM";
export declare class TerminalState<E> extends StateImpl<E> implements InputState<E> {
    constructor(stateMachine: FSM<E>, stateName: string);
    checkStartingState(): void;
    enter(): void;
}

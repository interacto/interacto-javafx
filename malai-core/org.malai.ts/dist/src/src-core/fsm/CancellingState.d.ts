import { StateImpl } from "./StateImpl";
import { InputState } from "./InputState";
import { FSM } from "./FSM";
export declare class CancellingState<E> extends StateImpl<E> implements InputState<E> {
    constructor(stateMachine: FSM<E>, stateName: string);
    checkStartingState(): void;
    enter(): void;
    uninstall(): void;
}

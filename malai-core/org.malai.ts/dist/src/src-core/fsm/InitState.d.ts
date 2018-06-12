import { OutputStateImpl } from "./OutputStateImpl";
import { FSM } from "./FSM";
export declare class InitState<E> extends OutputStateImpl<E> {
    constructor(stateMachine: FSM<E>, stateName: string);
    exit(): void;
}

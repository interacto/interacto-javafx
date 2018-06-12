import { State } from "./State";
import { FSM } from "./FSM";
export declare abstract class StateImpl<E> implements State<E> {
    protected readonly fsm: FSM<E>;
    protected readonly name: string;
    protected constructor(stateMachine: FSM<E>, stateName: string);
    checkStartingState(): void;
    getName(): string;
    getFSM(): FSM<E>;
    uninstall(): void;
}

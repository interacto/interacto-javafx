import { FSM } from "./FSM";
export interface State<E> {
    getName(): string;
    getFSM(): FSM<E>;
    checkStartingState(): void;
    uninstall(): void;
}

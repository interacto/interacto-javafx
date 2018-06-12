import { State } from "./State";
import { Transition } from "./Transition";
export interface OutputState<E> extends State<E> {
    exit(): void;
    process(event: E): boolean;
    getTransitions(): Array<Transition<E>>;
    addTransition(tr: Transition<E>): void;
}
export declare function isOutputStateType<E>(obj: OutputState<E> | Object): obj is OutputState<E>;

import { State } from "./State";
export interface InputState<E> extends State<E> {
    enter(): void;
}

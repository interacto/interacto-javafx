import { Transition } from "./Transition";
import { OutputState } from "./OutputState";
import { InputState } from "./InputState";
export declare abstract class WidgetTransition<E, T> extends Transition<E> {
    protected widget: T;
    protected constructor(srcState: OutputState<E>, tgtState: InputState<E>);
    getWidget(): T;
    setWidget(widget: T): void;
}

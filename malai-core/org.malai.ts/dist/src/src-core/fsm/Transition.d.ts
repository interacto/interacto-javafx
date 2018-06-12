import { OutputState } from "./OutputState";
import { InputState } from "./InputState";
import { Optional } from "../../util/Optional";
export declare abstract class Transition<E> {
    readonly src: OutputState<E>;
    readonly tgt: InputState<E>;
    protected constructor(srcState: OutputState<E>, tgtState: InputState<E>);
    execute(event: E): Optional<InputState<E>>;
    protected action(event: E | undefined): void;
    abstract accept(event: E): boolean;
    abstract isGuardOK(event: E): boolean;
    abstract getAcceptedEvents(): Set<string>;
    uninstall(): void;
}

import { Transition } from "./Transition";
import { FSM } from "./FSM";
import { OutputState } from "./OutputState";
import { InputState } from "./InputState";
import { Optional } from "../../util/Optional";
export declare class SubFSMTransition<E> extends Transition<E> {
    private readonly subFSM;
    private readonly subFSMHandler;
    constructor(srcState: OutputState<E>, tgtState: InputState<E>, fsm: FSM<E>);
    execute(event: E): Optional<InputState<E>>;
    accept(event: E): boolean;
    isGuardOK(event: E): boolean;
    private findTransition(event);
    getAcceptedEvents(): Set<string>;
    uninstall(): void;
}

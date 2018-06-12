import { FSM } from "../fsm/FSM";
import { OutputState } from "../fsm/OutputState";
import { Logger } from "typescript-logging";
import { InteractionData } from "./InteractionData";
export declare abstract class InteractionImpl<D extends InteractionData, E, F extends FSM<E>> {
    protected logger: Logger | undefined;
    protected readonly fsm: F;
    protected activated: boolean;
    protected constructor(fsm: F);
    protected abstract updateEventsRegistered(newState: OutputState<E>, oldState: OutputState<E>): void;
    isRunning(): boolean;
    fullReinit(): void;
    processEvent(event: E): void;
    log(log: boolean): void;
    isActivated(): boolean;
    setActivated(activated: boolean): void;
    getFsm(): F;
    abstract getData(): D;
    protected reinit(): void;
    protected abstract reinitData(): void;
    uninstall(): void;
}

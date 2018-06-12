import { FSM } from "../src-core/fsm/FSM";
import { FSMDataHandler } from "./FSMDataHandler";
export declare abstract class TSFSM<H extends FSMDataHandler> extends FSM<Event> {
    protected dataHandler: H | undefined;
    protected buildFSM(dataHandler?: H): void;
    reinit(): void;
    process(event: Event): boolean;
    private removeKeyEvent(key);
    getDataHandler(): H | undefined;
    uninstall(): void;
}

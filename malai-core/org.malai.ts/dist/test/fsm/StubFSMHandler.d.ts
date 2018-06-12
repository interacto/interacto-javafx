import { FSMHandler } from "../../src/src-core/fsm/FSMHandler";
export declare class StubFSMHandler implements FSMHandler {
    fsmStarts(): void;
    fsmUpdates(): void;
    fsmStops(): void;
    fsmCancels(): void;
}

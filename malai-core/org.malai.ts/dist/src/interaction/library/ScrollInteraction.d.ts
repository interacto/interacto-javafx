import { FSM } from "../../src-core/fsm/FSM";
import { TSInteraction } from "../TSInteraction";
import { Optional } from "../../util/Optional";
import { ScrollData } from "./ScrollData";
import { ScrollDataImpl } from "./ScrollDataImpl";
export declare abstract class ScrollInteraction<D extends ScrollData, F extends FSM<Event>, T> extends TSInteraction<D, F, T> implements ScrollData {
    readonly scrollData: ScrollDataImpl;
    protected constructor(fsm: F);
    reinitData(): void;
    getIncrement(): number;
    getPx(): number;
    getPy(): number;
    getScrolledNode(): Optional<EventTarget>;
    setScrollData(event: UIEvent): void;
}

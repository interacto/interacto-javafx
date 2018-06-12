import { ScrollData } from "./ScrollData";
import { Optional } from "../../util/Optional";
export declare class ScrollDataImpl implements ScrollData {
    protected scrolledNode: EventTarget | undefined;
    protected px: number | undefined;
    protected py: number | undefined;
    protected increment: number | undefined;
    constructor();
    reinitData(): void;
    getIncrement(): number;
    getPx(): number;
    getPy(): number;
    getScrolledNode(): Optional<EventTarget>;
    setScrollData(event: UIEvent): void;
}

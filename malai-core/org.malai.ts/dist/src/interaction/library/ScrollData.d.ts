import { Optional } from "../../util/Optional";
export interface ScrollData {
    getScrolledNode(): Optional<EventTarget>;
    getPx(): number;
    getPy(): number;
    getIncrement(): number;
}

import { Optional } from "../../util/Optional";
export interface PointData {
    isAltPressed(): boolean;
    isCtrlPressed(): boolean;
    isShiftPressed(): boolean;
    isMetaPressed(): boolean;
    getSrcClientX(): number;
    getSrcClientY(): number;
    getSrcScreenX(): number;
    getSrcScreenY(): number;
    getButton(): number | undefined;
    getSrcObject(): Optional<EventTarget>;
}

import { FSM } from "../../src-core/fsm/FSM";
import { TSInteraction } from "../TSInteraction";
import { PointData } from "./PointData";
import { PointDataImpl } from "./PointDataImpl";
import { Optional } from "../../util/Optional";
export declare abstract class PointInteraction<D extends PointData, F extends FSM<Event>, T> extends TSInteraction<D, F, T> implements PointData {
    readonly pointData: PointDataImpl;
    protected constructor(fsm: F);
    reinitData(): void;
    setPointData(event: MouseEvent): void;
    getButton(): number | undefined;
    getSrcClientX(): number;
    getSrcClientY(): number;
    getSrcObject(): Optional<EventTarget>;
    getSrcScreenX(): number;
    getSrcScreenY(): number;
    isAltPressed(): boolean;
    isCtrlPressed(): boolean;
    isMetaPressed(): boolean;
    isShiftPressed(): boolean;
}

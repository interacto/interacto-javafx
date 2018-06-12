import { Optional } from "../../util/Optional";
import { PointData } from "./PointData";
export declare class PointDataImpl implements PointData {
    protected srcClientX: number | undefined;
    protected srcClientY: number | undefined;
    protected srcScreenX: number | undefined;
    protected srcScreenY: number | undefined;
    protected button: number | undefined;
    protected altPressed: boolean;
    protected ctrlPressed: boolean;
    protected shiftPressed: boolean;
    protected metaPressed: boolean;
    protected srcObject: EventTarget | undefined;
    constructor();
    reinitData(): void;
    isAltPressed(): boolean;
    isCtrlPressed(): boolean;
    isShiftPressed(): boolean;
    isMetaPressed(): boolean;
    getButton(): number | undefined;
    getSrcObject(): Optional<EventTarget>;
    getSrcScreenY(): number;
    getSrcScreenX(): number;
    getSrcClientY(): number;
    getSrcClientX(): number;
    setModifiersData(event: MouseEvent): void;
    setPointData(event: MouseEvent): void;
}

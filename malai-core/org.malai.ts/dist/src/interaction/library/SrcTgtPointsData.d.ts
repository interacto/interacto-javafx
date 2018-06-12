import { PointData } from "./PointData";
import { Optional } from "../../util/Optional";
export interface SrcTgtPointsData extends PointData {
    getTgtObject(): Optional<EventTarget>;
    getTgtClientX(): number;
    getTgtClientY(): number;
    getTgtScreenX(): number;
    getTgtScreenY(): number;
}

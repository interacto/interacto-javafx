import { TSFSM } from "../TSFSM";
import { FSMDataHandler } from "../FSMDataHandler";
import { PointInteraction } from "./PointInteraction";
import { SrcTgtPointsData } from "./SrcTgtPointsData";
import { Optional } from "../../util/Optional";
export declare class DnDFSM extends TSFSM<DnDFSMHandler> {
    private readonly cancellable;
    private buttonToCheck;
    constructor(cancellable: boolean);
    buildFSM(dataHandler?: DnDFSMHandler): void;
    reinit(): void;
}
export interface DnDFSMHandler extends FSMDataHandler {
    onPress(event: Event): void;
    onDrag(event: Event): void;
    onRelease(event: Event): void;
}
export declare class DnD extends PointInteraction<SrcTgtPointsData, DnDFSM, Node> implements SrcTgtPointsData {
    private readonly handler;
    constructor(srcOnUpdate: boolean, cancellable: boolean, fsm?: DnDFSM);
    reinitData(): void;
    getData(): SrcTgtPointsData;
    getTgtClientX(): number;
    getTgtClientY(): number;
    getTgtScreenX(): number;
    getTgtScreenY(): number;
    getTgtObject(): Optional<EventTarget>;
}

import { PositionAction } from "./PositionAction";
import { Zoomable } from "../../properties/Zoomable";
export declare class Zoom extends PositionAction {
    protected zoomable: Zoomable | undefined;
    protected zoomLevel: number;
    constructor();
    flush(): void;
    canDo(): boolean;
    protected doCmdBody(): void;
    setZoomable(newZoomable: Zoomable): void;
    setZoomLevel(newZoomLevel: number): void;
}

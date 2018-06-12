export interface Zoomable {
    getZoomIncrement(): number;
    getMaxZoom(): number;
    getMinZoom(): number;
    getZoom(): number;
    setZoom(x: number, y: number, zoomingLevel: number): void;
    getZoomedPoint(x: number, y: number): void;
}

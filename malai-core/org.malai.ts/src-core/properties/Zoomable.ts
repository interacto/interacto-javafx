/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Defines an interface to zoomable objects.
     * @author Arnaud BLOUIN
     * @since 0.1
     * @class
     */
    export interface Zoomable {
        /**
         * @return {number} The zoom increment used when zooming in/out.
         */
        getZoomIncrement() : number;

        /**
         * @return {number} The maximal level of zooming allowed.
         */
        getMaxZoom() : number;

        /**
         * @return {number} The minimal level of zooming allowed.
         */
        getMinZoom() : number;

        /**
         * @return {number} The zoom level.
         * @since 0.1
         */
        getZoom() : number;

        /**
         * Zooms in the zoomable object.
         * @param {number} zoomingLevel The zooming level.
         * @param {number} x The X-coordinate of the location to zoom.
         * @param {number} y The Y-coordinate of the location to zoom.
         * @since 0.1
         */
        setZoom(x : number, y : number, zoomingLevel : number);

        /**
         * Transforms the given point in a point which coordinates have been modified to
         * take account of the zoom level.
         * @param {number} x The X-coordinate of the point to modify.
         * @param {number} y The Y-coordinate of the point to modify.
         * @return {java.awt.geom.Point2D} The transformed point.
         * @since 0.2
         */
        getZoomedPoint(x? : any, y? : any) : any;
    }
}


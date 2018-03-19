/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action.library {
    /**
     * Initialises a Zoom action.
     * @since 0.2
     * @class
     * @extends org.malai.action.library.PositionAction
     * @author Arnaud BLOUIN
     */
    export class Zoom extends org.malai.action.library.PositionAction {
        /**
         * The object to zoom.
         */
        zoomable : org.malai.properties.Zoomable;

        /**
         * The zooming level.
         */
        zoomLevel : number;

        public constructor() {
            super();
            if(this.zoomable===undefined) this.zoomable = null;
            if(this.zoomLevel===undefined) this.zoomLevel = 0;
            this.zoomLevel = NaN;
            this.zoomable = null;
        }

        /**
         * 
         */
        public flush() {
            super.flush();
            this.zoomable = null;
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return this.zoomable != null && this.zoomLevel >= this.zoomable.getMinZoom() && this.zoomLevel <= this.zoomable.getMaxZoom();
        }

        /**
         * 
         */
        doActionBody() {
            this.zoomable.setZoom(this.px, this.py, this.zoomLevel);
        }

        /**
         * @param {*} newZoomable the zoomable to set.
         * @since 0.2
         */
        public setZoomable(newZoomable : org.malai.properties.Zoomable) {
            this.zoomable = newZoomable;
        }

        /**
         * @param {number} newZoomLevel the zoomLevel to set.
         * @since 0.2
         */
        public setZoomLevel(newZoomLevel : number) {
            this.zoomLevel = newZoomLevel;
        }
    }
    Zoom["__class"] = "org.malai.action.library.Zoom";
    Zoom["__interfaces"] = ["org.malai.action.Action"];


}


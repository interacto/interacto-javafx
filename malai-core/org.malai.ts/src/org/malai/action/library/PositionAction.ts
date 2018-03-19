/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action.library {
    /**
     * Creates the action.
     * @class
     * @extends org.malai.action.ActionImpl
     * @author Arnaud BLOUIN
     */
    export abstract class PositionAction extends org.malai.action.ActionImpl {
        /**
         * The X-coordinate of the location to zoom.
         */
        px : number;

        /**
         * The Y-coordinate of the location to zoom.
         */
        py : number;

        public constructor() {
            super();
            if(this.px===undefined) this.px = 0;
            if(this.py===undefined) this.py = 0;
            this.px = NaN;
            this.py = NaN;
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return !/* isNaN */isNaN(this.px) && !/* isNaN */isNaN(this.py);
        }

        /**
         * @param {number} px The x-coordinate to set.
         * @since 0.2
         */
        public setPx(px : number) {
            this.px = px;
        }

        /**
         * @param {number} py The y-coordinate to set.
         * @since 0.2
         */
        public setPy(py : number) {
            this.py = py;
        }
    }
    PositionAction["__class"] = "org.malai.action.library.PositionAction";
    PositionAction["__interfaces"] = ["org.malai.action.Action"];


}


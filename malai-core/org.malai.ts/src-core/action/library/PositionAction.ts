/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Creates the action.
     * @class
     * @extends ActionImpl
     * @author Arnaud BLOUIN
     */
    export abstract class PositionAction extends ActionImpl {
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
    PositionAction["__class"] = "malai.PositionAction";
    PositionAction["__interfaces"] = ["malai.Action"];


}


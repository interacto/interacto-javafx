/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="InstrumentAction.ts" />

namespace malai {
    /**
     * This action inactivates an instrument.
     * @author Arnaud Blouin
     * @param {*} instrument
     * @class
     * @extends malai.InstrumentAction
     */
    export class InactivateInstrument extends InstrumentAction {
        public constructor(instrument : Instrument<any> = null) {
            super(instrument);
        }

        /**
         * 
         */
        doActionBody() {
            this.instrument.setActivated(false);
        }
    }
    InactivateInstrument["__class"] = "malai.InactivateInstrument";
    InactivateInstrument["__interfaces"] = ["malai.Action"];


}


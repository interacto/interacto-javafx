/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="InstrumentAction.ts" />

namespace malai {
    /**
     * This action activates an instrument.
     * @author Arnaud Blouin
     * @param {*} instrument
     * @class
     * @extends InstrumentAction
     */
    export class ActivateInstrument extends InstrumentAction {
        public constructor(instrument : Instrument<any> = null) {
            super(instrument);
        }

        /**
         * 
         */
        doActionBody() {
            this.instrument.setActivated(true);
        }
    }
    ActivateInstrument["__class"] = "malai.ActivateInstrument";
    ActivateInstrument["__interfaces"] = ["malai.Action"];


}


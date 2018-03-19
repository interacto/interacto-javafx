/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="InstrumentAction.ts" />

namespace org.malai.action.library {
    /**
     * This action activates an instrument.
     * @author Arnaud Blouin
     * @param {*} instrument
     * @class
     * @extends org.malai.action.library.InstrumentAction
     */
    export class ActivateInstrument extends org.malai.action.library.InstrumentAction {
        public constructor(instrument : org.malai.instrument.Instrument<any> = null) {
            super(instrument);
        }

        /**
         * 
         */
        doActionBody() {
            this.instrument.setActivated(true);
        }
    }
    ActivateInstrument["__class"] = "org.malai.action.library.ActivateInstrument";
    ActivateInstrument["__interfaces"] = ["org.malai.action.Action"];


}


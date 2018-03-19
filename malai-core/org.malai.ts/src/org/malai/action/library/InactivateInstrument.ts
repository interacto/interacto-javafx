/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */

/// <reference path="InstrumentAction.ts" />

namespace org.malai.action.library {
    /**
     * This action inactivates an instrument.
     * @author Arnaud Blouin
     * @param {*} instrument
     * @class
     * @extends org.malai.action.library.InstrumentAction
     */
    export class InactivateInstrument extends org.malai.action.library.InstrumentAction {
        public constructor(instrument : org.malai.instrument.Instrument<any> = null) {
            super(instrument);
        }

        /**
         * 
         */
        doActionBody() {
            this.instrument.setActivated(false);
        }
    }
    InactivateInstrument["__class"] = "org.malai.action.library.InactivateInstrument";
    InactivateInstrument["__interfaces"] = ["org.malai.action.Action"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action.library {
    /**
     * This action manipulates an instrument.
     * @author Arnaud Blouin
     * @param {*} instrument
     * @class
     * @extends org.malai.action.ActionImpl
     */
    export abstract class InstrumentAction extends org.malai.action.ActionImpl {
        /**
         * The manipulated instrument.
         */
        instrument : org.malai.instrument.Instrument<any>;

        public constructor(instrument? : any) {
            if(((instrument != null && (instrument["__interfaces"] != null && instrument["__interfaces"].indexOf("org.malai.instrument.Instrument") >= 0 || instrument.constructor != null && instrument.constructor["__interfaces"] != null && instrument.constructor["__interfaces"].indexOf("org.malai.instrument.Instrument") >= 0)) || instrument === null)) {
                let __args = Array.prototype.slice.call(arguments);
                super();
                if(this.instrument===undefined) this.instrument = null;
                if(this.instrument===undefined) this.instrument = null;
                (() => {
                    this.instrument = instrument;
                })();
            } else if(instrument === undefined) {
                let __args = Array.prototype.slice.call(arguments);
                {
                    let __args = Array.prototype.slice.call(arguments);
                    let instrument : any = null;
                    super();
                    if(this.instrument===undefined) this.instrument = null;
                    if(this.instrument===undefined) this.instrument = null;
                    (() => {
                        this.instrument = instrument;
                    })();
                }
            } else throw new Error('invalid overload');
        }

        /**
         * 
         */
        public flush() {
            super.flush();
            this.instrument = null;
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return this.instrument != null;
        }

        /**
         * @return {*} The manipulated instrument.
         */
        public getInstrument() : org.malai.instrument.Instrument<any> {
            return this.instrument;
        }

        /**
         * Sets the manipulated instrument.
         * @param {*} newInstrument The manipulated instrument.
         */
        public setInstrument(newInstrument : org.malai.instrument.Instrument<any>) {
            this.instrument = newInstrument;
        }
    }
    InstrumentAction["__class"] = "org.malai.action.library.InstrumentAction";
    InstrumentAction["__interfaces"] = ["org.malai.action.Action"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * This action manipulates an instrument.
     * @author Arnaud Blouin
     * @param {*} instrument
     * @class
     * @extends ActionImpl
     */
    export abstract class InstrumentAction extends ActionImpl {
        /**
         * The manipulated instrument.
         */
        instrument : Instrument<any>;

        public constructor(instrument? : any) {
            if(((instrument != null && (instrument["__interfaces"] != null && instrument["__interfaces"].indexOf("Instrument") >= 0 || instrument.constructor != null && instrument.constructor["__interfaces"] != null && instrument.constructor["__interfaces"].indexOf("Instrument") >= 0)) || instrument === null)) {
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
        public getInstrument() : Instrument<any> {
            return this.instrument;
        }

        /**
         * Sets the manipulated instrument.
         * @param {*} newInstrument The manipulated instrument.
         */
        public setInstrument(newInstrument : Instrument<any>) {
            this.instrument = newInstrument;
        }
    }
    InstrumentAction["__class"] = "malai.InstrumentAction";
    InstrumentAction["__interfaces"] = ["malai.Action"];


}


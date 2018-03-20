/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Initialises the action with the value to set.
     * @param {*} value The value to set.
     * @class
     * @extends ActionImpl
     * @author Arnaud Blouin
     */
    export abstract class ModifyValue extends ActionImpl {
        /**
         * The new value of the property.
         */
        value : any;

        public constructor(value? : any) {
            if(((value != null) || value === null)) {
                let __args = Array.prototype.slice.call(arguments);
                super();
                if(this.value===undefined) this.value = null;
                if(this.value===undefined) this.value = null;
                (() => {
                    this.value = value;
                })();
            } else if(value === undefined) {
                let __args = Array.prototype.slice.call(arguments);
                super();
                if(this.value===undefined) this.value = null;
                if(this.value===undefined) this.value = null;
            } else throw new Error('invalid overload');
        }

        /**
         * 
         */
        public flush() {
            super.flush();
            this.value = null;
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return this.value != null && this.isValueMatchesProperty();
        }

        /**
         * Sets the new value of the parameter to change.
         * @param {*} newValue The new value.
         */
        public setValue(newValue : any) {
            this.value = newValue;
        }

        /**
         * This method executes the job of methods undo, redo, and do
         * @param {*} obj The value to set. Must not be null.
         * @throws NullPointerException If the given value is null.
         */
        abstract applyValue(obj : any);

        /**
         * @return {boolean} True: the object to modified supports the selected property.
         */
        abstract isValueMatchesProperty() : boolean;
    }
    ModifyValue["__class"] = "malai.ModifyValue";
    ModifyValue["__interfaces"] = ["malai.Action"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.utils {
    /**
     * A simple value property. We do not use the JFX one (SimpleObjectProperty) since
     * this library is transpiled to other languages where SimpleObjectProperty may not be supported.
     * This also avoids a dependency to JFX.
     * @param <T> The type of the contained object.
     * @param {*} value
     * @class
     */
    export class ObsValue<T> {
        /*private*/ value : T;

        /*private*/ handlers : Array<ObsValue.Change<T>>;

        public constructor(value? : any) {
            if(((value != null) || value === null)) {
                let __args = Array.prototype.slice.call(arguments);
                if(this.value===undefined) this.value = null;
                if(this.handlers===undefined) this.handlers = null;
                if(this.value===undefined) this.value = null;
                if(this.handlers===undefined) this.handlers = null;
                (() => {
                    this.value = value;
                    this.handlers = <any>([]);
                })();
            } else if(value === undefined) {
                let __args = Array.prototype.slice.call(arguments);
                {
                    let __args = Array.prototype.slice.call(arguments);
                    let value : any = null;
                    if(this.value===undefined) this.value = null;
                    if(this.handlers===undefined) this.handlers = null;
                    if(this.value===undefined) this.value = null;
                    if(this.handlers===undefined) this.handlers = null;
                    (() => {
                        this.value = value;
                        this.handlers = <any>([]);
                    })();
                }
            } else throw new Error('invalid overload');
        }

        public get() : T {
            return this.value;
        }

        public set(value : T) {
            let oldValue : T = this.value;
            this.value = value;
            this.notifyChange(oldValue, value);
        }

        notifyChange(oldValue : T, newValue : T) {
            this.handlers.forEach((handler) => handler.onChange(oldValue, newValue));
        }

        public obs(handler : ObsValue.Change<T>) {
            if(handler != null) {
                /* add */((s, e) => { if(s.indexOf(e)==-1) { s.push(e); return true; } else { return false; } })(this.handlers, handler);
            }
        }

        public unobs(handler : ObsValue.Change<T>) {
            if(handler != null) {
                /* remove */(a => { let index = a.indexOf(handler); if(index>=0) { a.splice(index, 1); return true; } else { return false; }})(this.handlers);
            }
        }
    }
    ObsValue["__class"] = "org.malai.utils.ObsValue";


    export namespace ObsValue {

        export interface Change<T> {
            onChange(oldValue : T, newValue : T);
        }
    }

}


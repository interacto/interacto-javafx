/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action {
    /**
     * An anonymous action that takes an anonymous function as a parameter corresponding to the command to execute.
     * The goal of this action is to avoid the creation of an action class for a small action.
     * @author Arnaud Blouin
     * @param {() => void} function
     * @class
     * @extends org.malai.action.ActionImpl
     */
    export class AnonAction extends org.malai.action.ActionImpl {
        /*private*/ exec : () => void;

        public constructor(__function : () => void) {
            super();
            if(this.exec===undefined) this.exec = null;
            this.exec = <any>(__function);
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return this.exec != null;
        }

        /**
         * 
         */
        doActionBody() {
            (target => (typeof target === 'function')?target():(<any>target).run())(this.exec);
        }
    }
    AnonAction["__class"] = "org.malai.action.AnonAction";
    AnonAction["__interfaces"] = ["org.malai.action.Action"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Create the action.
     * @class
     * @extends malai.ActionImpl
     * @author Arnaud Blouin
     */
    export class AnonymousAction extends ActionImpl {
        /**
         * The runnable executed when the action is executed.
         */
        actionBody : () => void;

        public constructor() {
            super();
            if(this.actionBody===undefined) this.actionBody = null;
        }

        /**
         * 
         */
        doActionBody() {
            (target => (typeof target === 'function')?target():(<any>target).run())(this.actionBody);
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return this.actionBody != null;
        }

        /**
         * Sets the runnable of the action.
         * @param {() => void} body The runnable executed when the action is executed.
         */
        public setActionBody(body : () => void) {
            this.actionBody = <any>(body);
        }

        /**
         * @return {() => void} The runnable of the action.
         */
        public getActionBody() : () => void {
            return <any>(this.actionBody);
        }
    }
    AnonymousAction["__class"] = "malai.AnonymousAction";
    AnonymousAction["__interfaces"] = ["malai.Action"];


}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Initialises a Redo action.
     * @since 0.2
     * @class
     * @extends ActionImpl
     * @author Arnaud BLOUIN
     */
    export class Redo extends ActionImpl {
        public constructor() {
            super();
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return UndoCollector.INSTANCE_$LI$().getLastRedo().isPresent();
        }

        /**
         * 
         */
        doActionBody() {
            UndoCollector.INSTANCE_$LI$().redo();
            this.done();
        }
    }
    Redo["__class"] = "malai.Redo";
    Redo["__interfaces"] = ["malai.Action"];


}


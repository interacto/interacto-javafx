/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action.library {
    /**
     * Initialises a Redo action.
     * @since 0.2
     * @class
     * @extends org.malai.action.ActionImpl
     * @author Arnaud BLOUIN
     */
    export class Redo extends org.malai.action.ActionImpl {
        public constructor() {
            super();
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return org.malai.undo.UndoCollector.INSTANCE_$LI$().getLastRedo().isPresent();
        }

        /**
         * 
         */
        doActionBody() {
            org.malai.undo.UndoCollector.INSTANCE_$LI$().redo();
            this.done();
        }
    }
    Redo["__class"] = "org.malai.action.library.Redo";
    Redo["__interfaces"] = ["org.malai.action.Action"];


}


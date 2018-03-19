/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.action.library {
    /**
     * Initialises an Undo action.
     * @since 0.2
     * @class
     * @extends org.malai.action.ActionImpl
     * @author Arnaud BLOUIN
     */
    export class Undo extends org.malai.action.ActionImpl {
        public constructor() {
            super();
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return org.malai.undo.UndoCollector.INSTANCE_$LI$().getLastUndo().isPresent();
        }

        /**
         * 
         */
        doActionBody() {
            org.malai.undo.UndoCollector.INSTANCE_$LI$().undo();
            this.done();
        }
    }
    Undo["__class"] = "org.malai.action.library.Undo";
    Undo["__interfaces"] = ["org.malai.action.Action"];


}


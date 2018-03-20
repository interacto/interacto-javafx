/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Initialises an Undo action.
     * @since 0.2
     * @class
     * @extends ActionImpl
     * @author Arnaud BLOUIN
     */
    export class Undo extends ActionImpl {
        public constructor() {
            super();
        }

        /**
         * 
         * @return {boolean}
         */
        public canDo() : boolean {
            return UndoCollector.INSTANCE_$LI$().getLastUndo().isPresent();
        }

        /**
         * 
         */
        doActionBody() {
            UndoCollector.INSTANCE_$LI$().undo();
            this.done();
        }
    }
    Undo["__class"] = "malai.Undo";
    Undo["__interfaces"] = ["malai.Action"];


}


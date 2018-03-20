/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * The default constructor of the exception.
     * @param {*} clazz The class of the action that want to be undone/redone.
     * @class
     * @extends Error
     * @author Arnaud BLOUIN
     */
    export class MustBeUndoableActionException extends Error {
        public constructor(clazz : any) {
            super("The following action must be undoable: " + (clazz == null?"":" " + /* getName */(c => c["__class"]?c["__class"]:c["name"])(clazz))); this.message="The following action must be undoable: " + (clazz == null?"":" " + /* getName */(c => c["__class"]?c["__class"]:c["name"])(clazz));
            (<any>Object).setPrototypeOf(this, MustBeUndoableActionException.prototype);
        }
    }
    MustBeUndoableActionException["__class"] = "malai.MustBeUndoableActionException";
    MustBeUndoableActionException["__interfaces"] = ["java.io.Serializable"];


}


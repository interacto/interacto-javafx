/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * Defines an interface for object that can be modified and set as modified. This interface can also be used
     * to notify objects that the Modifiable object as been modified.
     * @author Arnaud BLOUIN
     * @since 0.2
     * @class
     */
    export interface Modifiable {
        /**
         * Sets the Modifiable object as modified.
         * @param {boolean} modified True: the element is will tagged as modified.
         * @since 0.2
         */
        setModified(modified : boolean);

        /**
         * @return {boolean} True: the object has been modified. False otherwise.
         * @since 3.0
         */
        isModified() : boolean;
    }
}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.error {
    /**
     * This interface must be used by objects that want to be notified about the exceptions collected by the ErrorCatcher.
     * @author Arnaud BLOUIN
     * @since 0.2
     * @class
     */
    export interface ErrorNotifier {
        /**
         * Notifies that an exception has been thrown.
         * @param {Error} exception The thrown exception.
         * @since 0.2
         */
        onException(exception : Error);
    }
}


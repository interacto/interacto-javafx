/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * The singleton ErrorCatcher collects errors.
     * The ErrorCatcher sends the gathered exception to an ErrorNotifier (if one is defined).
     * @author Arnaud BLOUIN
     * @since 0.2
     * @class
     */
    export class ErrorCatcher {
        /**
         * The singleton.
         */
        public static INSTANCE : ErrorCatcher; public static INSTANCE_$LI$() : ErrorCatcher { if(ErrorCatcher.INSTANCE == null) ErrorCatcher.INSTANCE = new ErrorCatcher(); return ErrorCatcher.INSTANCE; };

        /**
         * The notifier object.
         */
        /*private*/ notifier : ErrorNotifier;

        constructor() {
            if(this.notifier===undefined) this.notifier = null;
        }

        /**
         * Sets the notifier that will be notified about the collected exceptions.
         * @param {*} newNotifier The notifier that will be notified the collected exceptions. Can be null.
         * @since 0.2
         */
        public setNotifier(newNotifier : ErrorNotifier) {
            this.notifier = newNotifier;
        }

        /**
         * @return {*} The notifier that is notified about the collected exceptions.
         * @since 0.2
         */
        public getErrorNotifier() : ErrorNotifier {
            return this.notifier;
        }

        /**
         * Gathers exceptions. The notifier is then notified of the exceptions (if defined).
         * @param {Error} exception The errors to gather.
         * @since 0.1
         */
        public reportError(exception : Error) {
            if(exception != null && this.notifier != null) {
                this.notifier.onException(exception);
            }
        }
    }
    ErrorCatcher["__class"] = "malai.ErrorCatcher";

}


malai.ErrorCatcher.INSTANCE_$LI$();

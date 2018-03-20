/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * The concept of instrument and its related services.
     * @author Arnaud BLOUIN
     * @class
     */
    export interface Instrument<T extends WidgetBinding> extends Modifiable, Reinitialisable, ActionHandler {
        /**
         * @return {number} The number of widget bindings that compose the instrument.
         */
        getNbWidgetBindings() : number;

        /**
         * @return {boolean} True: the instrument has at least one widget binding. False otherwise.
         */
        hasWidgetBindings() : boolean;

        /**
         * @return {*[]} The widget bindings that compose the instrument. Cannot be null.
         */
        getWidgetBindings() : Array<T>;

        /**
         * Stops the interactions of the instrument and clears all its events waiting for a process.
         */
        clearEvents();

        /**
         * @return {boolean} True if the instrument is activated.
         */
        isActivated() : boolean;

        /**
         * Activates or deactivates the instrument.
         * @param {boolean} activated True = activation.
         */
        setActivated(activated : boolean);

        /**
         * Reinitialises the interim feedback of the instrument.
         * Must be overridden.
         */
        interimFeedback();
    }
}


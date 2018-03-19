/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.binding {
    /**
     * The concept of widget binding and its related services.
     * @author Arnaud BLOUIN
     * @class
     */
    export interface WidgetBinding extends org.malai.fsm.FSMHandler {
        /**
         * Stops the interaction and clears all its events waiting for a process.
         */
        clearEvents();

        /**
         * After being created by method map, the action must be initialised by this method.
         */
        first();

        /**
         * Updates the current action. To override.
         */
        then();

        /**
         * @return {boolean} True if the condition of the widget binding is respected.
         */
        when() : boolean;

        /**
         * @return {org.malai.interaction.InteractionImpl} The interaction.
         */
        getInteraction() : org.malai.interaction.InteractionImpl<any, any>;

        /**
         * @return {*} The action in progress or null.
         */
        getAction() : org.malai.action.Action;

        /**
         * @return {boolean} True if the widget binding is activated.
         */
        isActivated() : boolean;

        /**
         * Activates the widget binding.
         * @param {boolean} activ True: the widget binding is activated. Otherwise, it is desactivated.
         */
        setActivated(activ : boolean);

        /**
         * @return {boolean} True: if the widget binding is currently used.
         */
        isRunning() : boolean;

        /**
         * States whether the interaction must continue to run while the condition of the binding is not fulfilled at the interaction start.
         * @return {boolean}
         */
        isStrictStart() : boolean;

        /**
         * @return {boolean} True if the action is executed on each evolution of the interaction.
         */
        isExecute() : boolean;

        /**
         * Defines the interim feedback of the widget binding. If overridden, the interim
         * feedback of its instrument should be define too.
         */
        feedback();

        /**
         * @return {*} The instrument that contains the widget binding.
         */
        getInstrument() : org.malai.instrument.Instrument<any>;
    }
}


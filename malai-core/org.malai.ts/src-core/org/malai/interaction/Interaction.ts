/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.interaction {
    /**
     * An interface defining the concept of interaction and its related services.
     * @author Arnaud BLOUIN
     * @class
     */
    export interface Interaction extends org.malai.stateMachine.StateMachine, org.malai.interaction.EventProcessor {
        /**
         * @return {*[]} The handler that listens to the interaction.
         */
        getHandlers() : Array<org.malai.interaction.InteractionHandler>;

        /**
         * Adds an interaction handler.
         * @param {*} handler The handler to add.
         */
        addHandler(handler : org.malai.interaction.InteractionHandler);

        /**
         * @return {number} The ID of last HID that has been used by the interaction. If the interaction has stopped or is
         * aborted, the value of the attribute is -1.
         */
        getLastHIDUsed() : number;

        /**
         * @param {number} hid The ID of last HID that has been used by the interaction. If the interaction has stopped or is
         * aborted, the value of the attribute is -1.
         */
        setLastHIDUsed(hid : number);

        /**
         * Clears the events of the interaction still in process.
         */
        clearEventsStillInProcess();

        /**
         * Stops the interaction and clears all its events waiting for a process.
         */
        clearEvents();

        log(log : boolean);
    }
}


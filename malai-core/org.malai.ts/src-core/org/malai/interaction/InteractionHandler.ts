/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.interaction {
    /**
     * Defines an interaction for objects that want to by notified when the state of an interaction changed.
     * @author Arnaud BLOUIN
     * @class
     */
    export interface InteractionHandler {
        /**
         * When the interaction quits its initial state.
         * @throws MustCancelStateMachineException If the interaction must be cancelled.
         */
        interactionStarts();

        /**
         * When the interaction goes to standard state.
         * @throws MustCancelStateMachineException If the interaction must be cancelled.
         */
        interactionUpdates();

        /**
         * When the interaction goes to a terminal state.
         * @throws MustCancelStateMachineException If the interaction must be cancelled.
         */
        interactionStops();

        /**
         * When the interaction goes to a cancelling state.
         */
        interactionCancels();
    }
}


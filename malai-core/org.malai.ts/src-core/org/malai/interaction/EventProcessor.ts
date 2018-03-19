/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.interaction {
    /**
     * This interface can be used for object that want to gather events (mouse pressed, etc.) produced by HIDs.
     * @author Arnaud BLOUIN
     * @since 0.1
     * @class
     */
    export interface EventProcessor {
        /**
         * Defines action to do when a timeout is elapsed.
         * @param {org.malai.interaction.TimeoutTransition} timeoutTransition The transition which produced the timeout event.
         * @since 0.2
         */
        onTimeout(timeoutTransition : org.malai.interaction.TimeoutTransition);
    }
}


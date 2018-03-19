/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.stateMachine {
    /**
     * This interface defines the notion of state that composes a state machine.
     * @author Arnaud BLOUIN
     * @since 0.2
     * @class
     */
    export interface State {
        /**
         * Adds a transition to the state.
         * @param {*} trans The new transition. Must not be null.
         * @since 0.1
         */
        addTransition(trans : org.malai.stateMachine.Transition);

        /**
         * @return {string} The name of the state.
         * @since 0.1
         */
        getName() : string;

        /**
         * @return {*[]} The transitions that leaves the state.
         * @since 0.1
         */
        getTransitions() : Array<org.malai.stateMachine.Transition>;

        /**
         * @param {number} index The position of the transition to get.
         * @return {*} The corresponding transition or null if the given index is not valid.
         * @since 0.2
         */
        getTransition(index : number) : org.malai.stateMachine.Transition;

        /**
         * Sets the state machine of the state.
         * @param {*} sm The new state machine. If null, nothing is done.
         * @since 0.2
         */
        setStateMachine(sm : org.malai.stateMachine.StateMachine);

        /**
         * @return {*} The state machine that contains the state.
         * @since 0.2
         */
        getStateMachine() : org.malai.stateMachine.StateMachine;
    }
}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.stateMachine {
    /**
     * This interface defines the notion of state machine.
     * @author Arnaud BLOUIN
     * @class
     */
    export interface StateMachine {
        /**
         * Terminates the state machine.
         * @throws MustCancelStateMachineException If something happens requiring the interaction to cancel.
         */
        onTerminating();

        /**
         * Cancels the state machine.
         */
        onCancelling();

        /**
         * Starts the state machine.
         * @throws MustCancelStateMachineException If something happens requiring the interaction to cancel.
         */
        onStarting();

        /**
         * Updates the state machine.
         * @throws MustCancelStateMachineException If something happens requiring the interaction to cancel.
         */
        onUpdating();

        /**
         * Adds a state to the state machine.
         * @param {*} state The state to add. Must not be null.
         */
        addState(state : org.malai.stateMachine.State);

        /**
         * Reinits the state machine.
         */
        reinit();

        /**
         * @return {*} The current state of the running state machine. Or null when the machine is not running.
         */
        getCurrentState() : org.malai.stateMachine.State;

        /**
         * @return {boolean} True or false depending on whether the state machine is activated.
         */
        isActivated() : boolean;

        /**
         * Defines if the state machine is activated.
         * @param {boolean} activated True: the state machine will be activated.
         */
        setActivated(activated : boolean);

        /**
         * Checks whether the transition can be executed and executes it when possible.
         * @param {*} transition The transition to check.
         * @return {boolean} True: the transition has been executed. False otherwise.
         */
        checkTransition(transition : org.malai.stateMachine.Transition) : boolean;

        /**
         * @return {boolean} True: the state machine is running.
         */
        isRunning() : boolean;
    }
}


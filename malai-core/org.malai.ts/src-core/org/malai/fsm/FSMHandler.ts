/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.fsm {
    export interface FSMHandler {
        /**
         * When the FSM starts.
         * @throws CancelFSMException If the FSM must be cancelled.
         */
        fsmStarts();

        /**
         * When the FSM runs to new state.
         * @throws CancelFSMException If the FSM must be cancelled.
         */
        fsmUpdates();

        /**
         * When the FSM enters a terminal state.
         * @throws CancelFSMException If the FSM must be cancelled.
         */
        fsmStops();

        /**
         * When the interaction enters a cancelling state.
         */
        fsmCancels();
    }
}


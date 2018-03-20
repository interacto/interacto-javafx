/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export interface State<E> {
        getName() : string;

        getFSM() : FSM<E>;

        /**
         * Checks whether the starting state of the fsm is this state.
         * In this case, the fsm is notified about the starting of the FSM.
         * @throws CancelFSMException
         */
        checkStartingState();
    }
}


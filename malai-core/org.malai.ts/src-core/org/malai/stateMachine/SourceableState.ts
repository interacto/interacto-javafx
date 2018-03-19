/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.stateMachine {
    /**
     * This interface defines a state that can be the source state of a transition.
     * @author Arnaud BLOUIN
     * @since 0.2
     * @class
     */
    export interface SourceableState extends org.malai.stateMachine.State {
        /**
         * @throws MustCancelStateMachineException To launch when the state machine must stop.
         */
        onOutgoing();
    }
}


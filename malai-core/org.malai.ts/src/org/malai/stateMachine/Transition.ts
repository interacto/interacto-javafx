/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.stateMachine {
    /**
     * This interface defines the notion of transition of a state machine.
     * @author Arnaud BLOUIN
     * @since 0.2
     * @class
     */
    export interface Transition {
        /**
         * Performs the actions to do when the transition is executed.
         * Should be overridden.
         * @since 0.2
         */
        action();

        /**
         * @return {boolean} True: the condition defining if the transition can be executed is correct.
         * By default: true. Should be overridden.
         * @since 0.2
         */
        isGuardRespected() : boolean;

        /**
         * @return {*} The source state of the transition.
         * @since 0.2
         */
        getInputState() : org.malai.stateMachine.SourceableState;

        /**
         * @return {*} The target state of the transition.
         * @since 0.2
         */
        getOutputState() : org.malai.stateMachine.TargetableState;

        /**
         * @return {number} The ID of the HID that produced the transition.
         * @since 0.2
         */
        getHid() : number;

        /**
         * @param {number} hid The ID of the HID that produced the transition.
         * @since 0.2
         */
        setHid(hid : number);

        getEventType<T>() : T;
    }
}


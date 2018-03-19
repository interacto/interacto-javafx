/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.fsm {
    export interface OutputState<E> extends org.malai.fsm.State<E> {
        exit();

        /**
         * Asks to the state to process of the given event.
         * @param {*} event The event to process. Can be null.
         * @return {boolean}
         */
        process(event : E) : boolean;

        getTransitions() : Array<org.malai.fsm.Transition<E>>;

        addTransition(tr : org.malai.fsm.Transition<E>);
    }
}


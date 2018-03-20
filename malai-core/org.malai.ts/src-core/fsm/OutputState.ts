/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    export interface OutputState<E> extends State<E> {
        exit();

        /**
         * Asks to the state to process of the given event.
         * @param {*} event The event to process. Can be null.
         * @return {boolean}
         */
        process(event : E) : boolean;

        getTransitions() : Array<Transition<E>>;

        addTransition(tr : Transition<E>);
    }
}


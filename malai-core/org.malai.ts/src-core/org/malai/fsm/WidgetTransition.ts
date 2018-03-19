/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.fsm {
    /**
     * This transition must be used to use a widget within an interaction.
     * @author Arnaud BLOUIN
     * @param {*} srcState
     * @param {*} tgtState
     * @class
     * @extends org.malai.fsm.Transition
     */
    export abstract class WidgetTransition<E, T> extends org.malai.fsm.Transition<E> {
        /**
         * The pressed button.
         */
        widget : T;

        public constructor(srcState : org.malai.fsm.OutputState<E>, tgtState : org.malai.fsm.InputState<E>) {
            super(srcState, tgtState);
            if(this.widget===undefined) this.widget = null;
        }

        /**
         * @return {*} The widget used.
         */
        public getWidget() : T {
            return this.widget;
        }

        /**
         * Sets the widget.
         * @param {*} widget The widget to set. Nothign done if null.
         */
        public setWidget(widget : T) {
            if(widget != null) {
                this.widget = widget;
            }
        }
    }
    WidgetTransition["__class"] = "org.malai.fsm.WidgetTransition";

}


/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace malai {
    /**
     * This transition must be used to use a widget within an interaction.
     * @author Arnaud BLOUIN
     * @param {*} srcState
     * @param {*} tgtState
     * @class
     * @extends Transition
     */
    export abstract class WidgetTransition<E, T> extends Transition<E> {
        /**
         * The pressed button.
         */
        widget : T;

        public constructor(srcState : OutputState<E>, tgtState : InputState<E>) {
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
    WidgetTransition["__class"] = "malai.WidgetTransition";

}


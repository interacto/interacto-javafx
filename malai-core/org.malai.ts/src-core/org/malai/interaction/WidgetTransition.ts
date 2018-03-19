/* Generated from Java with JSweet 2.0.1 - http://www.jsweet.org */
namespace org.malai.interaction {
    /**
     * This transition must be used to use a widget within an interaction.
     * @author Arnaud BLOUIN
     * @param {*} inputState
     * @param {*} outputState
     * @class
     * @extends org.malai.interaction.TransitionImpl
     */
    export abstract class WidgetTransition<T> extends org.malai.interaction.TransitionImpl {
        /**
         * The pressed button.
         */
        widget : T;

        public constructor(inputState : org.malai.stateMachine.SourceableState, outputState : org.malai.stateMachine.TargetableState) {
            super(inputState, outputState);
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
         * @param {*} widget The widget to set. Must not be null.
         */
        public setWidget(widget : T) {
            if(widget != null) {
                this.widget = widget;
            }
        }
    }
    WidgetTransition["__class"] = "org.malai.interaction.WidgetTransition";
    WidgetTransition["__interfaces"] = ["org.malai.stateMachine.Transition"];


}


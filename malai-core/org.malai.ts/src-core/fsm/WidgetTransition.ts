/**
 * This transition must be used to use a widget within an interaction.
 * @author Arnaud BLOUIN
 * @param {*} srcState
 * @param {*} tgtState
 * @class
 * @extends Transition
 */
import {Transition} from "./Transition";
import {OutputState} from "./OutputState";
import {InputState} from "./InputState";

export abstract class WidgetTransition<E, T> extends Transition<E> {
    /**
     * The pressed button.
     */
    protected widget: T;

    public constructor(srcState: OutputState<E>, tgtState: InputState<E>) {
        super(srcState, tgtState);
    }

    /**
     * @return {*} The widget used.
     */
    public getWidget(): T {
        return this.widget;
    }

    /**
     * Sets the widget.
     * @param {*} widget The widget to set. Nothign done if null.
     */
    public setWidget(widget: T): void {
        if (widget !== undefined) {
            this.widget = widget;
        }
    }
}

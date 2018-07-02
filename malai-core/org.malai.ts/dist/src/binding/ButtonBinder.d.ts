import { ButtonPressed } from "../interaction/library/ButtonPressed";
import { CommandImpl } from "../src-core/command/CommandImpl";
import { WidgetData } from "../src-core/interaction/WidgetData";
import { Binder } from "./Binder";
export declare class ButtonBinder<C extends CommandImpl> extends Binder<C, ButtonPressed, WidgetData<Element>, ButtonBinder<C>> {
    constructor(cmd: (i?: WidgetData<Element>) => C);
}

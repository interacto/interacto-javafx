import { ButtonPressed } from "../interaction/library/ButtonPressed";
import { Binder } from "./Binder";
import { CommandImpl } from "../src-core/command/CommandImpl";
import { WidgetData } from "../src-core/interaction/WidgetData";
export declare class ButtonBinder<C extends CommandImpl> extends Binder<C, ButtonPressed, WidgetData<Element>, ButtonBinder<C>> {
    constructor(cmd: () => C);
}

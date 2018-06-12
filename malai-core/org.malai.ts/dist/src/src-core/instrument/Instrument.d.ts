import { WidgetBinding } from "../binding/WidgetBinding";
import { Modifiable } from "../properties/Modifiable";
import { Reinitialisable } from "../properties/Reinitialisable";
import { MArray } from "../../util/ArrayUtil";
import { CommandHandler } from "../command/CommandHandler";
export interface Instrument<T extends WidgetBinding> extends Modifiable, Reinitialisable, CommandHandler {
    getNbWidgetBindings(): number;
    hasWidgetBindings(): boolean;
    getWidgetBindings(): MArray<T>;
    clearEvents(): void;
    isActivated(): boolean;
    setActivated(activated: boolean): void;
    interimFeedback(): void;
}

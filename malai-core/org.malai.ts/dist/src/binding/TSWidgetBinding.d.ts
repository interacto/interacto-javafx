import { TSInteraction } from "../interaction/TSInteraction";
import { WidgetBindingImpl } from "../src-core/binding/WidgetBindingImpl";
import { FSM } from "../src-core/fsm/FSM";
import { CommandImpl } from "../src-core/command/CommandImpl";
import { Command } from "../src-core/command/Command";
import { InteractionData } from "../src-core/interaction/InteractionData";
export declare abstract class TSWidgetBinding<C extends CommandImpl, I extends TSInteraction<D, FSM<Event>, {}>, D extends InteractionData> extends WidgetBindingImpl<C, I, D> {
    protected constructor(exec: boolean, interaction: I, cmdProducer: (d?: D) => C, widgets: Array<EventTarget>);
    when(): boolean;
    protected executeCmdAsync(cmd: Command): void;
}

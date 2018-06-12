import { TSInteraction } from "../interaction/TSInteraction";
import { FSM } from "../src-core/fsm/FSM";
import { UpdateBinder } from "./UpdateBinder";
import { CommandImpl } from "../src-core/command/CommandImpl";
import { InteractionData } from "../src-core/interaction/InteractionData";
export declare class NodeBinder<C extends CommandImpl, I extends TSInteraction<D, FSM<Event>, {}>, D extends InteractionData> extends UpdateBinder<C, I, D, NodeBinder<C, I, D>> {
    constructor(interaction: I, cmdProducer: () => C);
}

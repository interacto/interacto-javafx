import { TSInteraction } from "../interaction/TSInteraction";
import { FSM } from "../src-core/fsm/FSM";
import { Binder } from "./Binder";
import { TSWidgetBinding } from "./TSWidgetBinding";
import { AnonCmd } from "../src-core/command/AnonCmd";
import { InteractionData } from "../src-core/interaction/InteractionData";
export declare class AnonCmdBinder<I extends TSInteraction<D, FSM<Event>, {}>, D extends InteractionData> extends Binder<AnonCmd, I, D, AnonCmdBinder<I, D>> {
    constructor(interaction: I, anonCmd: () => void);
    bind(): TSWidgetBinding<AnonCmd, I, D>;
}

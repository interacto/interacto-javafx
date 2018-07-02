import { TSInteraction } from "../interaction/TSInteraction";
import { FSM } from "../src-core/fsm/FSM";
import { Binder } from "./Binder";
import { TSWidgetBinding } from "./TSWidgetBinding";
import { CommandImpl } from "../src-core/command/CommandImpl";
import { InteractionData } from "../src-core/interaction/InteractionData";
export declare abstract class UpdateBinder<C extends CommandImpl, I extends TSInteraction<D, FSM<Event>, {}>, D extends InteractionData, B extends UpdateBinder<C, I, D, B>> extends Binder<C, I, D, B> {
    protected updateFct: (i: D, c: C | undefined) => void;
    protected cancelFct: (i: D, c: C | undefined) => void;
    protected endOrCancelFct: (i: D, c: C | undefined) => void;
    protected feedbackFct: () => void;
    protected execOnChanges: boolean;
    protected _strictStart: boolean;
    protected constructor(interaction: I, cmdProducer: (i?: D) => C);
    then(update: (i: D, c: C | undefined) => void): B;
    exec(): B;
    cancel(cancel: (i: D, c: C | undefined) => void): B;
    endOrCancel(endOrCancel: (i: D, c: C | undefined) => void): B;
    feedback(feedback: () => void): B;
    strictStart(): B;
    bind(): TSWidgetBinding<C, I, D>;
}

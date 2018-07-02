import { TSWidgetBinding } from "./TSWidgetBinding";
import { TSInteraction } from "../interaction/TSInteraction";
import { LogLevel } from "../src-core/logging/LogLevel";
import { FSM } from "../src-core/fsm/FSM";
import { CommandImpl } from "../src-core/command/CommandImpl";
import { InteractionData } from "../src-core/interaction/InteractionData";
export declare class AnonNodeBinding<C extends CommandImpl, I extends TSInteraction<D, FSM<Event>, {}>, D extends InteractionData> extends TSWidgetBinding<C, I, D> {
    private readonly execInitCmd;
    private readonly execUpdateCmd;
    private readonly checkInteraction;
    private readonly cancelFct;
    private readonly endOrCancelFct;
    private readonly feedbackFct;
    private readonly onEnd;
    private readonly strictStart;
    protected currentCmd: C | undefined;
    constructor(exec: boolean, interaction: I, cmdProducer: (d?: D) => C, initCmdFct: (i: D, c: C | undefined) => void, updateCmdFct: (i: D, c: C | undefined) => void, check: (i: D) => boolean, onEndFct: (i: D, c: C | undefined) => void, cancel: (i: D, c: C | undefined) => void, endOrCancel: (i: D, c: C | undefined) => void, feedback: () => void, widgets: Array<EventTarget>, asyncExec: boolean, strict: boolean, loggers: Array<LogLevel>);
    private configureLoggers(loggers);
    isStrictStart(): boolean;
    first(): void;
    then(): void;
    when(): boolean;
    fsmCancels(): void;
    feedback(): void;
    fsmStops(): void;
}

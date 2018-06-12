import { TSInteraction } from "../interaction/TSInteraction";
import { MArray } from "../util/ArrayUtil";
import { LogLevel } from "../src-core/logging/LogLevel";
import { TSWidgetBinding } from "./TSWidgetBinding";
import { FSM } from "../src-core/fsm/FSM";
import { CommandImpl } from "../src-core/command/CommandImpl";
import { InteractionData } from "../src-core/interaction/InteractionData";
export declare abstract class Binder<C extends CommandImpl, I extends TSInteraction<D, FSM<Event>, {}>, D extends InteractionData, B extends Binder<C, I, D, B>> {
    protected initCmd: (i: D, c: C | undefined) => void;
    protected checkConditions: (i: D) => boolean;
    protected readonly widgets: MArray<EventTarget>;
    protected cmdProducer: (d: D) => C;
    protected readonly interaction: I;
    protected _async: boolean;
    protected onEnd: (i: D, c: C | undefined) => void;
    protected readonly logLevels: Set<LogLevel>;
    protected constructor(interaction: I, cmdProducer: () => C);
    map(cmdFunction: (d: D) => C): B;
    on(widget: EventTarget): B;
    first(initCmdFct: (i: D, c: C) => void): B;
    when(checkCmd: (i: D) => boolean): B;
    async(): B;
    end(onEndFct: (i: D, c: C) => void): B;
    log(level: LogLevel): B;
    bind(): TSWidgetBinding<C, I, D>;
}

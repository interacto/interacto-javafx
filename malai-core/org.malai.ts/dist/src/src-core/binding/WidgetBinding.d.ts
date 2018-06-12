import { FSMHandler } from "../fsm/FSMHandler";
import { InteractionImpl } from "../interaction/InteractionImpl";
import { FSM } from "../fsm/FSM";
import { Command } from "../command/Command";
export interface WidgetBinding extends FSMHandler {
    clearEvents(): void;
    first(): void;
    then(): void;
    when(): boolean;
    getInteraction(): InteractionImpl<{}, {}, FSM<{}>>;
    getCommand(): Command | undefined;
    isActivated(): boolean;
    setActivated(activ: boolean): void;
    isRunning(): boolean;
    isStrictStart(): boolean;
    isExecute(): boolean;
    feedback(): void;
    uninstallBinding(): void;
}

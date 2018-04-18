/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */

import {TSWidgetBinding} from "./TSWidgetBinding";
import {TSInteraction} from "../interaction/TSInteraction";
import {LogLevel} from "../../src-core/logging/LogLevel";
import {FSM} from "../../src-core/fsm/FSM";
import {CommandImpl} from "../../src-core/command/CommandImpl";

export class AnonNodeBinding<C extends CommandImpl, I extends TSInteraction<FSM<Event>, {}>> extends TSWidgetBinding<C, I> {
    private readonly execInitCmd: (i: I, c: C | undefined) => void;
    private readonly execUpdateCmd: (i: I, c: C | undefined) => void;
    private readonly checkInteraction: (i: I) => boolean;
    private readonly cancelFct: (i: I, c: C | undefined) => void;
    private readonly endOrCancelFct: (i: I, c: C | undefined) => void;
    private readonly feedbackFct: () => void;
    private readonly onEnd: (i: I, c: C | undefined) => void;
    private readonly strictStart: boolean;
    /** Used rather than 'cmd' to catch the command during its creation.
     * Sometimes (eg onInteractionStops) can create the command, execute it, and forget it.
     */
    protected currentCmd: C | undefined;

    /**
     * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
     * instrument is (de-)activated.
     * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
     * @param cmdClass The type of the command that will be created. Used to instantiate the command by reflexivity.
     * The class must be public and must have a constructor with no parameter.
     * @param interaction The user interaction of the binding.
     * @param widgets The widgets used by the binding. Cannot be null.
     * @param initCmdFct The function that initialises the command to execute. Cannot be null.
     * @param updateCmdFct The function that updates the command. Can be null.
     * @throws IllegalArgumentException If the given interaction or instrument is null.
     */
    public constructor(exec: boolean, interaction: I, cmdClass: () => C, initCmdFct: (i: I, c: C | undefined) => void,
                       updateCmdFct: (i: I, c: C | undefined) => void, check: (i: I) => boolean,
                       onEndFct: (i: I, c: C | undefined) => void, cancel: (i: I, c: C | undefined) => void,
                       endOrCancel: (i: I, c: C | undefined) => void, feedback: () => void, widgets: Array<EventTarget>,
                       // List<ObservableList<? extends Node>> additionalWidgets, HelpAnimation animation, help : boolean
                       asyncExec: boolean, strict: boolean, loggers: Array<LogLevel>) {
        super(exec, interaction, cmdClass, widgets);
        this.execInitCmd = initCmdFct;
        this.execUpdateCmd = updateCmdFct;
        this.cancelFct = cancel;
        this.endOrCancelFct = endOrCancel;
        this.feedbackFct = feedback;
        this.checkInteraction = check;
        this.async = asyncExec;
        this.onEnd = onEndFct;
        this.strictStart = strict;
        this.currentCmd = undefined;
        this.configureLoggers(loggers);

        // if(additionalWidgets !== undefined) {
        // additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableNodeList(elt));
    }

    private configureLoggers(loggers: Array<LogLevel>): void {
        this.logCmd(loggers.indexOf(LogLevel.COMMAND) >= 0);
        this.logBinding(loggers.indexOf(LogLevel.BINDING) >= 0);
        this.interaction.log(loggers.indexOf(LogLevel.INTERACTION) >= 0);
    }

    public isStrictStart(): boolean {
        return this.strictStart;
    }

    public first(): void {
        if (this.currentCmd !== undefined) {
            this.execInitCmd(this.getInteraction(), this.getCommand());
        }
    }

    public then(): void {
        this.execUpdateCmd(this.getInteraction(), this.getCommand());
    }

    public when(): boolean {
        // if(loggerBinding !== undefined) {
        //     loggerBinding.log(Level.INFO, "Checking condition: " + ok);
        // }
        return this.checkInteraction(this.getInteraction());
    }

    public fsmCancels(): void {
        if (this.currentCmd !== undefined) {
            this.endOrCancelFct(this.interaction, this.cmd);
        }
        if (this.currentCmd !== undefined) {
            this.cancelFct(this.interaction, this.cmd);
        }
        super.fsmCancels();
        this.currentCmd = undefined;
    }

    public feedback(): void {
        // if(loggerBinding !== undefined) {
        //     loggerBinding.log(Level.INFO, "Feedback");
        // }
        this.feedbackFct();
    }

    public fsmStops(): void {
        super.fsmStops();
        if (this.currentCmd !== undefined) {
            this.endOrCancelFct(this.getInteraction(), this.currentCmd);
        }
        if (this.currentCmd !== undefined) {
            this.onEnd(this.getInteraction(), this.currentCmd);
        }
        this.currentCmd = undefined;
    }
}

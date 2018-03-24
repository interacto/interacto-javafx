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
import {ActionImpl} from "../../src-core/action/ActionImpl";
import {LogLevel} from "../../src-core/logging/LogLevel";
import {FSM} from "../../src-core/fsm/FSM";

export class AnonNodeBinding<A extends ActionImpl, I extends TSInteraction<FSM<UIEvent>, {}>> extends TSWidgetBinding<A, I> {
    private readonly execInitAction: (a: A | undefined, i: I) => void;
    private readonly execUpdateAction: (a: A | undefined, i: I) => void;
    private readonly checkInteraction: (i: I) => boolean;
    private readonly cancelFct: (a: A | undefined, i: I) => void;
    private readonly endOrCancelFct: (a: A | undefined, i: I) => void;
    private readonly feedbackFct: () => void;
    private readonly onEnd: (a: A | undefined, i: I) => void;
    private readonly strictStart: boolean;
    /** Used rather than 'action' to catch the action during its creation.
     * Sometimes (eg onInteractionStops) can create the action, execute it, and forget it.
     */
    protected currentAction: A | undefined;

    /**
     * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
     * instrument is (de-)activated.
     * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
     * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
     * The class must be public and must have a constructor with no parameter.
     * @param interaction The user interaction of the binding.
     * @param widgets The widgets used by the binding. Cannot be null.
     * @param initActionFct The function that initialises the action to execute. Cannot be null.
     * @param updateActionFct The function that updates the action. Can be null.
     * @throws IllegalArgumentException If the given interaction or instrument is null.
     */
    public constructor(exec: boolean, clazzAction: () => A, interaction: I, initActionFct: (a: A, i: I) => void,
                       updateActionFct: (a: A, i: I) => void, check: (i: I) => boolean, onEndFct: (a: A | undefined, i: I) => void,
                       cancel: (a: A | undefined, i: I) => void,
                       endOrCancel: (a: A | undefined, i: I) => void, feedback: () => void, widgets: Array<EventTarget>,
                       // List<ObservableList<? extends Node>> additionalWidgets, HelpAnimation animation, help : boolean
                       asyncExec: boolean, strict: boolean, loggers: Array<LogLevel>) {
        super(exec, clazzAction, interaction, widgets);
        this.execInitAction = initActionFct;
        this.execUpdateAction = updateActionFct;
        this.cancelFct = cancel;
        this.endOrCancelFct = endOrCancel;
        this.feedbackFct = feedback;
        this.checkInteraction = check;
        this.async = asyncExec;
        this.onEnd = onEndFct;
        this.strictStart = strict;
        this.currentAction = undefined;
        this.configureLoggers(loggers);

        // if(additionalWidgets !== undefined) {
        // additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableNodeList(elt));
    }

    private configureLoggers(loggers: Array<LogLevel>): void {
        this.logAction(loggers.indexOf(LogLevel.ACTION) >= 0);
        this.logBinding(loggers.indexOf(LogLevel.BINDING) >= 0);
        this.interaction.log(loggers.indexOf(LogLevel.INTERACTION) >= 0);
    }

    public isStrictStart(): boolean {
        return this.strictStart;
    }

    public first(): void {
        if (this.currentAction !== undefined) {
            this.execInitAction(this.getAction(), this.getInteraction());
        }
    }

    public then(): void {
        this.execUpdateAction(this.getAction(), this.getInteraction());
    }

    public when(): boolean {
        // if(loggerBinding !== undefined) {
        //     loggerBinding.log(Level.INFO, "Checking condition: " + ok);
        // }
        return this.checkInteraction(this.getInteraction());
    }

    public fsmCancels(): void {
        if (this.currentAction !== undefined) {
            this.endOrCancelFct(this.action, this.interaction);
        }
        if (this.currentAction !== undefined) {
            this.cancelFct(this.action, this.interaction);
        }
        super.fsmCancels();
        this.currentAction = undefined;
    }

    public feedback(): void {
        // if(loggerBinding !== undefined) {
        //     loggerBinding.log(Level.INFO, "Feedback");
        // }
        this.feedbackFct();
    }

    public fsmStops(): void {
        super.fsmStops();
        if (this.currentAction !== undefined) {
            this.endOrCancelFct(this.currentAction, this.getInteraction());
        }
        if (this.currentAction !== undefined) {
            this.onEnd(this.currentAction, this.getInteraction());
        }
        this.currentAction = undefined;
    }
}

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

import {ActionImpl} from "../../src-core/action/ActionImpl";
import {TSInteraction} from "../interaction/TSInteraction";
import {MArray} from "../util/ArrayUtil";
import {LogLevel} from "../../src-core/logging/LogLevel";
import {TSWidgetBinding} from "./TSWidgetBinding";
import {AnonNodeBinding} from "./AnonNodeBinding";
import {FSM} from "../../src-core/fsm/FSM";

/**
 * The base class that defines the concept of binding builder (called binder).
 * @param <W> The type of the widgets.
 * @param <A> The type of the action to produce.
 * @param <I> The type of the user interaction to bind.
 * @author Arnaud Blouin
 */
export abstract class Binder<A extends ActionImpl, I extends TSInteraction<FSM<Event>, {}>, B extends Binder<A, I, B>> {
    protected initAction: (a: A | undefined, i: I) => void;
    protected checkConditions: (i: I) => boolean;
    protected readonly widgets: MArray<EventTarget>;
    protected readonly actionClass: () => A;
    protected readonly interaction: I;
    protected _async: boolean;
    protected onEnd: (a: A | undefined, i: I) => void;
// protected List<ObservableList<? extends Node>> additionalWidgets;
    protected readonly logLevels: Set<LogLevel>;

    public constructor(action: () => A, interaction: I) {
        this.actionClass = action;
        this.interaction = interaction;
        this.widgets = new MArray();
        this._async = false;
        this.checkConditions = () => true;
        this.initAction = () => {};
        this.onEnd = () => {};
        this.logLevels = new Set<LogLevel>();
    }

    /**
     * Specifies the widgets on which the binding must operate.
     * @param widget The widgets involve in the bindings.
     * @return The builder to chain the building configuration.
     */
    public on(widget: EventTarget): B {
        this.widgets.push(widget);
        return this as {} as B;
    }


// /**
//  * Specifies the observable list that will contain the widgets on which the binding must operate.
//  * When a widget is added to this list, the added widget is binded to this binding.
//  * When widget is removed from this list, this widget is unbinded from this binding.
//  * @param widgets The observable list of the widgets involved in the bindings.
//  * @return The builder to chain the building configuration.
//  */
// public on(final ObservableList<? extends Node> widgets): B {
//     if(additionalWidgets == null) {
//         additionalWidgets = new ArrayList<>();
//     }
//     additionalWidgets.add(widgets);
//     return this as {} as B;
// }


// /**
//  * Specifies how the action is created from the user interaction.
//  * Called a single time per interaction executed (just before 'first' that can still be called to, somehow, configure the action).
//  * Each time the interaction starts, an instance of the action is created and configured by the given callback.
//  * @param actionFunction The function that creates and initialises the action.
//  * This callback takes as arguments the current user interaction.
//  * @return The builder to chain the building configuration.
//  */
// public map(final Function<I, A> actionFunction): B {
//     actionProducer = actionFunction;
//     return this as {} as B;
// }


    /**
     * Specifies the initialisation of the action when the interaction starts.
     * Each time the interaction starts, an instance of the action is created and configured by the given callback.
     * @param initActionFct The callback method that initialises the action.
     * This callback takes as arguments both the action and interaction involved in the binding.
     * @return The builder to chain the building configuration.
     */
    public first(initActionFct: (a: A, i: I) => void): B {
        this.initAction = initActionFct;
        return this as {} as B;
    }

    /**
     * Specifies the conditions to fulfill to initialise, update, or execute the action while the interaction is running.
     * @param checkAction The predicate that checks whether the action can be initialised, updated, or executed.
     * This predicate takes as arguments the ongoing user interaction involved in the binding.
     * @return The builder to chain the building configuration.
     */
    public when(checkAction: (i: I) => boolean): B {
        this.checkConditions = checkAction;
        return this as {} as B;
    }


    /**
     * Specifies that the action will be executed in a separated threads.
     * Beware of UI modifications: UI changes must be done in the JFX UI thread.
     * @return The builder to chain the building configuration.
     */
    public async(): B {
        this._async = true;
        return this as {} as B;
    }

    /**
     * Specifies what to do end when an interaction ends (when the last event of the interaction has occured, but just after
     * the interaction is reinitialised and the action finally executed and discarded / saved).
     * @param onEndFct The callback method to specify what to do when an interaction ends.
     * @return The builder to chain the building configuration.
     */
    public end(onEndFct: (a: A, i: I) => void): B {
        this.onEnd = onEndFct;
        return this as {} as B;
    }

    /**
     * Specifies the loggings to use.
     * Several call to 'log' can be done to log different parts:
     * log(LogLevel.INTERACTION).log(LogLevel.ACTION)
     * @param level The logging level to use.
     * @return The builder to chain the building configuration.
     */
    public log(level: LogLevel): B {
        this.logLevels.add(level);
        return this as {} as B;
    }

    /**
     * Executes the builder to create and install the binding on the instrument.
     * @throws IllegalArgumentException On issues while creating the actions.
     * @throws InstantiationException On issues while creating the actions.
     */
    public bind(): TSWidgetBinding<A, I> {
        return new AnonNodeBinding<A, I>(false, this.actionClass, this.interaction, this.initAction, () => {},
            this.checkConditions, this.onEnd, () => {}, () => {}, () => {},
            this.widgets, this._async, false, new Array(...this.logLevels));
    }
}

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

import {Action} from "../../src-core/action/Action";
import {ActionImpl} from "../../src-core/action/ActionImpl";
import {TSInteraction} from "../interaction/TSInteraction";
import {WidgetBindingImpl} from "../../src-core/binding/WidgetBindingImpl";
import {FSM} from "../../src-core/fsm/FSM";

export abstract class TSWidgetBinding<A extends ActionImpl, I extends TSInteraction<FSM<UIEvent>, {}>> extends WidgetBindingImpl<A, I> {
    /**
     * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
     * instrument is (de-)activated.
     * @param exec Specifies whether the action must be execute or update on each evolution of the interaction.
     * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
     * The class must be public and must have a constructor with no parameter.
     * @param interaction The user interaction of the binding.
     * @param widgets The widgets concerned by the binding. Cannot be null.
     * @throws IllegalArgumentException If the given interaction or instrument is null.
     */
    public constructor(exec: boolean, clazzAction: () => A, interaction: I, widgets: Array<EventTarget>) {
        super(exec, clazzAction, interaction);
        interaction.registerToNodes(widgets);
    }

    public when(): boolean {
        return true;
    }

    protected executeActionAsync(act: Action): void {
        //TODO
    }
}

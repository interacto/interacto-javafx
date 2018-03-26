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
import {FSM} from "../../src-core/fsm/FSM";
import {UpdateBinder} from "./UpdateBinder";

/**
 * The binding builder to create bindings between a given user interaction on a node and a given action.
 * @param <A> The type of the action to produce.
 * @param <I> The type of the user interaction to bind.
 * @author Arnaud Blouin
 */
export class NodeBinder<A extends ActionImpl, I extends TSInteraction<FSM<Event>, {}>> extends UpdateBinder<A, I, NodeBinder<A, I>> {
    public constructor(action: () => A, interaction: I) {
        super(action, interaction);
    }
}

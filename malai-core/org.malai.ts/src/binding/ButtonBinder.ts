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

import {ButtonPressed} from "../interaction/library/ButtonPressed";
import {Binder} from "./Binder";
import {CommandImpl} from "../src-core/command/CommandImpl";
import {WidgetData} from "../src-core/interaction/WidgetData";

/**
 * The binding builder to create bindings between a button interaction and a given command.
 * @param <A> The type of the command to produce.
 * @author Arnaud Blouin
 */
export class ButtonBinder<C extends CommandImpl> extends Binder<C, ButtonPressed, WidgetData<Element>, ButtonBinder<C>> {
    public constructor(cmd: (i?: WidgetData<Element>) => C) {
        super(new ButtonPressed(), cmd);
    }
}

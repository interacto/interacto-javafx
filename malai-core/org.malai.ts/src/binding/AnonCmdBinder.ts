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

import {TSInteraction} from "../interaction/TSInteraction";
import {FSM} from "../../src-core/fsm/FSM";
import {Binder} from "./Binder";
import {TSWidgetBinding} from "./TSWidgetBinding";
import {AnonNodeBinding} from "./AnonNodeBinding";
import {AnonCmd} from "../../src-core/command/AnonCmd";

export class AnonCmdBinder<I extends TSInteraction<FSM<Event>, {}>> extends Binder<AnonCmd, I, AnonCmdBinder<I>> {
    public constructor(interaction: I, anonCmd: () => void) {
        super(interaction, () => new AnonCmd(anonCmd));
    }

    public bind(): TSWidgetBinding<AnonCmd, I> {
        return new AnonNodeBinding(false, this.interaction, this.cmdClass, () => {},
            () => {}, this.checkConditions, this.onEnd, () => {}, () => {}, () => {},
            this.widgets, this._async, false, new Array(...this.logLevels));
    }
}

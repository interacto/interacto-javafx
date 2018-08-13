/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN Gwendal DIDOT
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */

import {CommandImpl} from "../src-core/command/CommandImpl";
import {SrcTgtPointsData} from "../interaction/library/SrcTgtPointsData";
import {SourceTargetBinder} from "./SourceTargetBinder";
import {DragLock} from "../interaction/library/DragLock";

/**
 * The binding builder to create bindings between a checkbox interaction and a given command.
 * @param <C> The type of the command to produce.
 * @author Gwendal Didot
 */

export class DragLockBinder<C extends CommandImpl> extends SourceTargetBinder<C, DragLock, SrcTgtPointsData, DragLockBinder<C>> {
    public constructor (cmd: (i ?: SrcTgtPointsData) => C) {
        super(new DragLock(), cmd);
    }
}

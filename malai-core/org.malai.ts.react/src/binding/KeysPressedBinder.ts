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

import {KeysData} from "../interaction/library/KeysData";
import {CommandImpl} from "../src-core/command/CommandImpl";
import {KeyBinder} from "./KeyBinder";

export class KeysPressedBinder<C extends CommandImpl> extends KeyBinder<C, KeysPressedBinder<C>> {
    public constructor(cmd: (i ?: KeysData) => C) {
        super(cmd);
    }
}

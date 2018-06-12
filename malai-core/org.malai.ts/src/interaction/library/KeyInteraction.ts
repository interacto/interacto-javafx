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

import {KeyData} from "./KeyData";
import {FSM} from "../../src-core/fsm/FSM";
import {TSInteraction} from "../TSInteraction";
import {Optional} from "../../util/Optional";
import {KeyDataImpl} from "./KeyDataImpl";

export abstract class KeyInteraction<D extends KeyData, F extends FSM<Event>, T> extends TSInteraction<D, F, T> implements KeyData {
    public readonly keyData: KeyDataImpl;

    protected constructor(fsm: F) {
        super(fsm);
        this.keyData = new KeyDataImpl();
    }

    public reinitData(): void {
        super.reinitData();
        this.keyData.reinitData();
    }

    public setKeyData(event: KeyboardEvent): void {
        this.keyData.setKeyData(event);
    }

    public getKey(): String {
        return this.keyData.getKey();
    }

    public getTarget(): Optional<EventTarget> {
        return this.keyData.getTarget();
    }
}


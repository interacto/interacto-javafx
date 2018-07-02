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

import {FSM} from "../../src-core/fsm/FSM";
import {Optional} from "../../util/Optional";
import {TSInteraction} from "../TSInteraction";
import {KeysData} from "./KeysData";
import {KeysDataImpl} from "./KeysDataImpl";

export abstract class MultiKeyInteraction<D extends KeysData, F extends FSM<Event>> extends TSInteraction<D, F, Event> implements KeysData {
    public readonly keysData: KeysDataImpl;

    protected constructor(fsm: F) {
        super(fsm);
        this.keysData = new KeysDataImpl();
    }

    public reinitData(): void {
        super.reinitData();
        this.keysData.reinitData();
    }

    public setKeysDataTartget(event: KeyboardEvent) {
        this.keysData.setKeysDataTarget(event);
    }

    public addKeysDataKey(event: KeyboardEvent) {
        this.keysData.addKeysDataKey(event);
    }

    public removeKeysDataKey(event: KeyboardEvent) {
        this.keysData.removeKeysDataKey(event);
    }

    public getKeys(): Array<String> {
        return this.keysData.getKeys();
    }

    public getTarget(): Optional<EventTarget> {
        return this.keysData.getTarget();
    }

}

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
import {FSM} from "../../../src-core/fsm/FSM";
import {TSInteraction} from "../TSInteraction";
import {Optional} from "../../util/Optional";

export abstract class KeyInteraction<D extends KeyData, F extends FSM<Event>, T> extends TSInteraction<D, F, T> implements KeyData {
    protected key: String;

    protected target: EventTarget | undefined;

    protected constructor(fsm: F) {
        super(fsm);
    }

    public reinitData(): void {
        super.reinitData();
        this.target = undefined;
        this.key = "";
    }

    protected setKeyData(event: KeyboardEvent): void {
        this.target = event.target === null ? undefined : event.target;
        this.key =  event.key;
    }

    public getTarget(): Optional<EventTarget> {return Optional.ofNullable(this.target); }

    public getKey(): String {return this.key; }
}


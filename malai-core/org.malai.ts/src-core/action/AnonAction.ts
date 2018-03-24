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

import {ActionImpl} from "./ActionImpl";

/**
 * An anonymous action that takes an anonymous function as a parameter corresponding to the command to execute.
 * The goal of this action is to avoid the creation of an action class for a small action.
 * @author Arnaud Blouin
 * @param {() => void} function
 * @class
 * @extends ActionImpl
 */
export class AnonAction extends ActionImpl {
    private readonly exec: () => void;

    public constructor(fct: () => void) {
        super();
        this.exec = fct;
    }

    /**
     *
     * @return {boolean}
     */
    public canDo(): boolean {
        return true;
    }

    protected doActionBody(): void {
        this.exec();
    }
}

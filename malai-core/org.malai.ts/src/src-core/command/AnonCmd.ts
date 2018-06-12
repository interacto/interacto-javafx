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


import {CommandImpl} from "./CommandImpl";

/**
 * An anonymous command that takes an anonymous function as a parameter corresponding to the command to execute.
 * The goal of this command is to avoid the creation of a command class for a small command.
 * @author Arnaud Blouin
 * @param {() => void} function
 * @class
 * @extends CommandImpl
 */
export class AnonCmd extends CommandImpl {
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

    protected doCmdBody(): void {
        this.exec();
    }
}

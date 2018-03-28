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

import {CommandImpl} from "../CommandImpl";

/**
 * Create the command.
 * @class
 * @author Arnaud Blouin
 */
export class AnonymousCmd extends CommandImpl {
    /**
     * The runnable executed when the command is executed.
     */
    protected cmdBody: (() => void) | undefined;

    public constructor() {
        super();
    }

    protected doCmdBody(): void {
        if (this.cmdBody !== undefined) {
            this.cmdBody();
        }
    }

    /**
     *
     * @return {boolean}
     */
    public canDo(): boolean {
        return this.cmdBody !== undefined;
    }

    /**
     * Sets the runnable of the command.
     * @param {() => void} body The runnable executed when the command is executed.
     */
    public setCmdBody(body: () => void) {
        this.cmdBody = body;
    }

    /**
     * @return {() => void} The runnable of the command.
     */
    public getCmdBody(): (() => void) | undefined {
        return this.cmdBody;
    }
}

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

import {ActionImpl} from "../ActionImpl";

/**
 * Create the action.
 * @class
 * @author Arnaud Blouin
 */
export class AnonymousAction extends ActionImpl {
    /**
     * The runnable executed when the action is executed.
     */
    protected actionBody: () => void | undefined;

    public constructor() {
        super();
    }

    protected doActionBody(): void {
        if (this.actionBody !== undefined) {
            this.actionBody();
        }
    }

    /**
     *
     * @return {boolean}
     */
    public canDo(): boolean {
        return this.actionBody !== undefined;
    }

    /**
     * Sets the runnable of the action.
     * @param {() => void} body The runnable executed when the action is executed.
     */
    public setActionBody(body: () => void) {
        this.actionBody = body;
    }

    /**
     * @return {() => void} The runnable of the action.
     */
    public getActionBody(): () => void | undefined {
        return this.actionBody;
    }
}

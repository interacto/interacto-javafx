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

import {UndoCollector} from "../../undo/UndoCollector";
import {CommandImpl} from "../CommandImpl";

/**
 * Initialises an Undo command.
 * @since 0.2
 * @class
 * @extends CommandImpl
 * @author Arnaud BLOUIN
 */
export class Undo extends CommandImpl {
    public constructor() {
        super();
    }

    /**
     *
     * @return {boolean}
     */
    public canDo(): boolean {
        return UndoCollector.INSTANCE.getLastUndo().isPresent();
    }

    protected doCmdBody(): void {
        UndoCollector.INSTANCE.undo();
        this.done();
    }
}

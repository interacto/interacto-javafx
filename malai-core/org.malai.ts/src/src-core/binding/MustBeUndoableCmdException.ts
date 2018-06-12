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

/**
 * The default constructor of the exception.
 * @param {*} clazz The class of the command that want to be undone/redone.
 * @extends Error
 * @author Arnaud BLOUIN
 */
export class MustBeUndoableCmdException extends Error {
    public constructor(cmdProducer: Object) {
        super("The following command must be undoable: " + String(cmdProducer));
    }
}


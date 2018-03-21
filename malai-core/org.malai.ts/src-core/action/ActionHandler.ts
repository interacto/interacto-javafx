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

import {Action} from "./Action";
import {UndoHandler} from "../undo/UndoHandler";

/**
 * This interface allows to create a bridge between an action and an object that want to be aware about events on actions
 * (such as creation or deletion of an action).
 * @author Arnaud Blouin
 * @class
 */
export interface ActionHandler extends UndoHandler {
    /**
     * Notifies the handler when the given action is added to the registry.
     * @param {*} action The added action.
     */
    onActionAdded(action: Action): void;

    /**
     * Notifies the handler when the given action is cancelled.
     * @param {*} action The cancelled action.
     */
    onActionCancelled(action: Action): void;

    /**
     * Notifies the handler when the given action is executed.
     * @param {*} action The executed action.
     */
    onActionExecuted(action: Action): void;

    /**
     * Notifies the handler when the given action is done.
     * @param {*} action The action that ends.
     */
    onActionDone(action: Action): void;
}


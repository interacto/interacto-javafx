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

import {FSM} from "./FSM";

export interface State<E> {
    getName(): string;

    getFSM(): FSM<E>;

    /**
     * Checks whether the starting state of the fsm is this state.
     * In this case, the fsm is notified about the starting of the FSM.
     * @throws CancelFSMException
     */
    checkStartingState(): void;

    uninstall(): void;
}


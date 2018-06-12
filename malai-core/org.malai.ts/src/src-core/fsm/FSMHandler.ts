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

export interface FSMHandler {
    /**
     * When the FSM starts.
     * @throws CancelFSMException If the FSM must be cancelled.
     */
    fsmStarts(): void;

    /**
     * When the FSM runs to new state.
     * @throws CancelFSMException If the FSM must be cancelled.
     */
    fsmUpdates(): void;

    /**
     * When the FSM enters a terminal state.
     * @throws CancelFSMException If the FSM must be cancelled.
     */
    fsmStops(): void;

    /**
     * When the interaction enters a cancelling state.
     */
    fsmCancels(): void;
}


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

import {Optional} from "../../util/Optional";

export interface PointData {
    /**
     * @return True: the alt key is pressed.
     */
    isAltPressed(): boolean;

    /**
     * @return True: the control key is pressed.
     */
    isCtrlPressed(): boolean;

    /**
     * @return True: the shift key is pressed.
     */
    isShiftPressed(): boolean;

    /**
     * @return True: the meta key is pressed.
     */
    isMetaPressed(): boolean;

    /**
     * @return The pressed X-client position.
     */
    getSrcClientX(): number;

    /**
     * @return The pressed Y-client position.
     */
    getSrcClientY(): number;

    /**
     * @return The pressed X-screen position.
     */
    getSrcScreenX(): number;

    /**
     * @return The pressed Y-screen position.
     */
    getSrcScreenY(): number;

    /**
     * @return The button used for the pressure.
     */
    getButton(): number | undefined;

    /**
     * @return The object picked at the pressed position.
     */
    getSrcObject(): Optional<EventTarget>;

    getCurrentTarget(): Optional<EventTarget>;
}

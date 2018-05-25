/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN Gwendal DIDOT
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */

import {Optional} from "../../util/Optional";

export interface ScrollData {
    /**
     * @return The object on witch the scroll is performed.
     */
    getScrolledNode(): Optional<EventTarget>;

    /**
     * @return  The X-coordinate of the scroll position.
     */
    getPx(): number;

    /**
     * @return The Y-coordinate of the scroll position.
     */
    getPy(): number;

    /**
     * @return The total increment of the scrolling.
     */
    getIncrement(): number;
}

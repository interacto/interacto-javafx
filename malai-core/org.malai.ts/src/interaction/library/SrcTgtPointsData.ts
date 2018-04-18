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

import {PointData} from "./PointData";
import {Optional} from "../../util/Optional";

export interface SrcTgtPointsData extends PointData {
    /**
     * @return The object picked at the target location.
     */
    getTgtObject(): Optional<EventTarget>;

    /**
     * @return The last X-client position.
     */
    getTgtClientX(): number;

    /**
     * @return The last Y-client position.
     */
    getTgtClientY(): number;

    /**
     * @return The last X-screen position.
     */
    getTgtScreenX(): number;

    /**
     * @return The last Y-screen position.
     */
    getTgtScreenY(): number;
}

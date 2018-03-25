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

export function createMouseEvent(type: string, target: EventTarget): MouseEvent {
    const evt = document.createEvent("MouseEvents");
    evt.initMouseEvent(type, true, false, window, 1, 2, 2, 2,
        2, false, false, false, false, 0, target);
    return evt;
}

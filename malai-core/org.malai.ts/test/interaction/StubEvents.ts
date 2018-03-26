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
    return new MouseEvent(type, {
        view: window,
        bubbles: true,
        cancelable: false,
        detail: 1,
        screenX: 20,
        screenY: 30,
        clientX: 2,
        clientY: 3,
        ctrlKey: false,
        altKey: false,
        shiftKey: false,
        metaKey: false,
        button: 0,
        relatedTarget: target
    });
}

export function createKeyEvent(type: string, keyCode: string): KeyboardEvent {
    return new KeyboardEvent(type, {
        cancelable: false,
        bubbles: true,
        view: window,
        code: keyCode,
        repeat: false
    });
}

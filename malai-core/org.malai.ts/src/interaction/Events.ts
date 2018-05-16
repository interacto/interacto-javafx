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

export enum EventRegistrationToken {
    MouseDown = "mousedown",
    MouseUp = "mouseup",
    MouseMove = "mousemove",
    KeyDown = "keydown",
    KeyPress = "keypress",
    KeyUp = "keyup",
    Click = "click"
}

export function isButton(target: EventTarget): target is Element {
    return (<Element>target).tagName === "BUTTON";
}

export function isKeyPressEvent(event: Event): event is KeyboardEvent {
    return event instanceof KeyboardEvent && event.type === "keypress";
}

export function isMouseDownEvent(event: Event): event is MouseEvent {
    return event instanceof MouseEvent && event.type === "mousedown";
}

export enum KeyCode {
    ESCAPE = 27
}

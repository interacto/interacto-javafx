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

export enum EventRegistrationToken {
    MouseDown = "mousedown",
    MouseUp = "mouseup",
    MouseMove = "mousemove",
    KeyDown = "keydown",
    KeyUp = "keyup",
    Click = "click",
    Input = "input",
    Scroll = "scroll",
    Change = "change",
    BeforeUnload = "beforeunload"
}

export function isButton(target: EventTarget): target is Element {
    return (<Element>target).tagName === "BUTTON" && !(<Element>target).hasAttribute("type");
}

export function isCheckBox(target: EventTarget): target is Element {
    return (<Element>target).tagName === "INPUT" && (<Element>target).getAttribute("type") === "checkbox";
}

export function isColorChoice(target: EventTarget): target is Element {
    return (<Element>target).tagName === "INPUT" && (<Element>target).getAttribute("type") === "color";
}

export function isComboBox(target: EventTarget): target is Element {
    return (<Element>target).tagName === "INPUT" && (<Element>target).getAttribute("list") !== undefined;
}

export function isDatePicker(target: EventTarget): target is Element {
    return (<Element>target).tagName === "INPUT" && (<Element>target).getAttribute("type") === "date";
}

export function isSpinner(target: EventTarget): target is Element {
    return (<Element>target).tagName === "INPUT" && (<Element>target).getAttribute("type") === "number";
}

export function isHyperLink(target: EventTarget): target is Element {
    return (<Element>target).getAttribute("href") !== undefined;
}

export function isChoiceBox(target: EventTarget): target is Element {
    return (<Element>target).tagName === "SELECT";
}

export function isTextInput(target: EventTarget): target is Element {
    return (<Element>target).tagName === "TEXTAREA" || ((<Element>target).tagName === "INPUT"
        && (<Element>target).getAttribute("type") === "text");
}

export function isWindowClosed(event: Event): boolean {
    return event.target === window && event.type === "beforeunload";
}

export function isKeyDownEvent(event: Event): event is KeyboardEvent {
    return event instanceof KeyboardEvent && event.type === "keydown";
}

export function isMouseDownEvent(event: Event): event is MouseEvent {
    return event instanceof MouseEvent && event.type === "mousedown";
}

export function isMouseUpEvent(event: Event): event is MouseEvent {
    return event instanceof MouseEvent && event.type === "mouseup";
}

export function isScrollEvent(event: Event): event is UIEvent {
    return event instanceof UIEvent && event.type === "scroll";
}

export function isMenuButton(target: EventTarget): target is Element {
    //Not yet implemented in Web browser, is present to follow the
    // feature of the javaFX version of Malai
    return(<Element>target).tagName === "BUTTON" && (<Element>target).getAttribute("type") === "menu";
}

export enum KeyCode {
    ESCAPE = 27
}

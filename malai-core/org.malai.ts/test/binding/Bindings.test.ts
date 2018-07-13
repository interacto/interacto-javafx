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

import {anonCmdBinder, buttonBinder, nodeBinder} from "../../src/binding/Bindings";
import {Click} from "../../src/interaction/library/Click";
import {StubCmd} from "../command/StubCmd";
import {AnonCmd, DnD, EventRegistrationToken, MArray} from "../../src";
import {createMouseEvent} from "../interaction/StubEvents";

jest.mock("../command/StubCmd");

let button: HTMLElement;
let canvas: HTMLElement;

beforeEach(() => {
    jest.clearAllMocks();
    document.documentElement.innerHTML = "<html><div><canvas id='canvas1' /><button id='b1'>A Button</button></div></html>";
    const elt1 = document.getElementById("b1");
    if (elt1 !== null) {
        button = elt1;
    }
    const elt2 = document.getElementById("canvas1");
    if (elt2 !== null) {
        canvas = elt2;
    }
});

test("button binder ok", () => {
    buttonBinder(() => new StubCmd()).on(button).bind();
    button.click();
    expect(StubCmd.prototype.doIt).toHaveBeenCalledTimes(1);
});

test("node binder ok", () => {
    nodeBinder(new Click(), () => new StubCmd()).on(canvas).bind();
    canvas.click();
    expect(StubCmd.prototype.doIt).toHaveBeenCalledTimes(1);
});

test("Anon cmd binder ok", () => {
    const cmd = new StubCmd();
    anonCmdBinder(new Click(), () => cmd.doIt()).on(canvas).bind();
    canvas.click();
    expect(StubCmd.prototype.doIt).toHaveBeenCalledTimes(1);
});

test("node binder on multiple target", () => {
    const target = new MArray<EventTarget>();
    target.push(canvas, button);
    nodeBinder(new Click(), () => new  AnonCmd(() => {
        console.log("Test");
    })).on(target).bind();
    // canvas.click();
    button.click();
});

test("Node binder with the to() routine", () => {
    nodeBinder(new Click(), () => new StubCmd()).on(button).to(canvas).bind();
    button.click();
    canvas.click();
    expect(StubCmd.prototype.doIt).toHaveBeenCalledTimes(1);
});

test("nodeBinder with to() routine and DnD interaction", () => {
    nodeBinder(new DnD(false, false), () => new StubCmd()).on(button).to(canvas).bind();
    button.dispatchEvent(createMouseEvent(EventRegistrationToken.MouseDown, button));
    canvas.dispatchEvent(createMouseEvent(EventRegistrationToken.MouseMove, canvas));
    canvas.dispatchEvent(createMouseEvent(EventRegistrationToken.MouseUp, canvas));
    canvas.dispatchEvent(createMouseEvent(EventRegistrationToken.MouseDown, canvas));
    button.dispatchEvent(createMouseEvent(EventRegistrationToken.MouseMove, button));
    button.dispatchEvent(createMouseEvent(EventRegistrationToken.MouseUp, button));
    expect(StubCmd.prototype.doIt).toHaveBeenCalledTimes(1);
});

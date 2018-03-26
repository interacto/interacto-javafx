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

import {StubAction} from "../action/StubAction";
import {anonActionBinder, buttonBinder, nodeBinder} from "../../src/binding/Bindings";
import {Click} from "../../src/interaction/library/Click";

jest.mock("../action/StubAction");

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
    buttonBinder(() => new StubAction()).on(button).bind();
    button.click();
    expect(StubAction.prototype.doIt).toHaveBeenCalledTimes(1);
});

test("node binder ok", () => {
    nodeBinder(() => new StubAction(), new Click()).on(canvas).bind();
    canvas.click();
    expect(StubAction.prototype.doIt).toHaveBeenCalledTimes(1);
});

test("Anon action binder ok", () => {
    const action = new StubAction();
    anonActionBinder(() => action.doIt(), new Click()).on(canvas).bind();
    canvas.click();
    expect(StubAction.prototype.doIt).toHaveBeenCalledTimes(1);
});

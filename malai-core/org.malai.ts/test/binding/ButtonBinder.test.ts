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

import {ButtonBinder} from "../../src/binding/ButtonBinder";
import {StubAction} from "../action/StubAction";
import {StubFSMHandler} from "../fsm/StubFSMHandler";

jest.mock("../fsm/StubFSMHandler");
jest.mock("../action/StubAction");

let button: HTMLElement;
let binder: ButtonBinder<StubAction>;

beforeEach(() => {
    jest.clearAllMocks();
    binder = new ButtonBinder<StubAction>(() => new StubAction());
    document.documentElement.innerHTML = "<html><div><button id='b1'>A Button</button></div></html>";
    const elt = document.getElementById("b1");
    if (elt !== null) {
        button = elt;
        binder.on(button);
    }
});

test("Button binder produces a binding", () => {
    const binding = binder.bind();
    expect(binding).not.toBeNull();
});

test("Click on button triggers the interaction", () => {
    const handler = new StubFSMHandler();
    const binding = binder.bind();
    binding.getInteraction().getFsm().addHandler(handler);
    button.click();
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
});

test("Click on button produces an action", () => {
    binder.bind();
    button.click();
    expect(StubAction.prototype.doIt).toHaveBeenCalledTimes(1);
});

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
import {TSWidgetBinding} from "../../src/binding/TSWidgetBinding";
import {ButtonPressed} from "../../src/interaction/library/ButtonPressed";

jest.mock("../fsm/StubFSMHandler");
jest.mock("../action/StubAction");

let button: HTMLElement;
let binding: TSWidgetBinding<StubAction, ButtonPressed>;

beforeEach(() => {
    jest.clearAllMocks();
    const binder = new ButtonBinder(() => new StubAction());
    document.documentElement.innerHTML = "<html><div><button id='b1'>A Button</button></div></html>";
    const elt = document.getElementById("b1");
    if (elt !== null) {
        button = elt;
        binder.on(button);
    }
    binding = binder.bind();
});

test("Button binder produces a binding", () => {
    expect(binding).not.toBeNull();
});

test("Click on button triggers the interaction", () => {
    const handler = new StubFSMHandler();
    binding.getInteraction().getFsm().addHandler(handler);
    button.click();
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
});

test("Click on button produces an action", () => {
    button.click();
    expect(StubAction.prototype.doIt).toHaveBeenCalledTimes(1);
});

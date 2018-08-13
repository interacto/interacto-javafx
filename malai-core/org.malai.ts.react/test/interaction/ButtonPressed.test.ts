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

import {ButtonPressed} from "../../src/interaction/library/ButtonPressed";
import {FSMHandler} from "../../src/src-core/fsm/FSMHandler";
import {StubFSMHandler} from "../fsm/StubFSMHandler";

jest.mock("../fsm/StubFSMHandler");

let interaction: ButtonPressed;
let button: HTMLElement;
let handler: FSMHandler;

beforeEach(() => {
    jest.clearAllMocks();
    handler = new StubFSMHandler();
    interaction = new ButtonPressed();
    interaction.log(true);
    interaction.getFsm().log(true);
    interaction.getFsm().addHandler(handler);
    document.documentElement.innerHTML = "<html><div><button id='b1'>A Button</button></div></html>";
    const elt = document.getElementById("b1");
    if (elt !== null) {
        button = elt;
    }
});

test("Click event start and stop the interaction ButtonPressed", () => {
    interaction.registerToNodes([button]);
    button.click();
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
});


test("Other event don't trigger the interaction ButtonPressed", () => {
    interaction.registerToNodes([button]);
    button.dispatchEvent(new Event("input"));
    button.dispatchEvent(new Event("change"));
    expect(handler.fsmStarts).not.toHaveBeenCalled();
});

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

import {FSMHandler} from "../../src-core/fsm/FSMHandler";
import {StubFSMHandler} from "../fsm/StubFSMHandler";
import {WindowClosed} from "../../src/interaction/library/WindowClosed";

jest.mock("../fsm/StubFSMHandler");

let interaction: WindowClosed;
let close: HTMLElement;
let handler: FSMHandler;
const element = document.defaultView;

beforeEach(() => {
    jest.clearAllMocks();
    handler = new StubFSMHandler();
    interaction = new WindowClosed();
    interaction.log(true);
    interaction.getFsm().log(true);
    interaction.getFsm().addHandler(handler);
    document.documentElement.innerHTML = "<html><iframe id='clo1'></iframe></html>";
    const elt2 = document.getElementById("clo1");
    if (elt2 !== null) {
        close = elt2;
    }
});

test("Exit the window (browser) starts and stops the interaction", () => {
    interaction.registerToNodes([element]);
    element.dispatchEvent(new Event("beforeunload"));
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
});

test("Exit document window don't start the interaction", () => {
    interaction.registerToNodes([close]);
    close.dispatchEvent(new Event("beforeunload"));
    expect(handler.fsmStarts).toHaveBeenCalledTimes(0);
});

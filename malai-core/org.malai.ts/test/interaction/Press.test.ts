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

import {Press} from "../../src/interaction/library/Press";
import {FSMHandler} from "../../src/src-core/fsm/FSMHandler";
import {StubFSMHandler} from "../fsm/StubFSMHandler";
import {createMouseEvent} from "./StubEvents";
import {EventRegistrationToken} from "../../src/interaction/Events";

jest.mock("../fsm/StubFSMHandler");

let interaction: Press;
let canvas: HTMLElement;
let handler: FSMHandler;

beforeEach(() => {
    jest.clearAllMocks();
    handler = new StubFSMHandler();
    interaction = new Press();
    interaction.log(true);
    interaction.getFsm().log(true);
    interaction.getFsm().addHandler(handler);
    document.documentElement.innerHTML = "<html><div><canvas id='canvas1' /></div></html>";
    const elt = document.getElementById("canvas1");
    if (elt !== null) {
        canvas = elt;
    }
});

test("Press on the canvas starts and stops interaction Press", () => {
    interaction.registerToNodes([canvas]);
    canvas.dispatchEvent(createMouseEvent(EventRegistrationToken.MouseDown, canvas));
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
});

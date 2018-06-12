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

import {FSMHandler} from "../../src/src-core/fsm/FSMHandler";
import {StubFSMHandler} from "../fsm/StubFSMHandler";
import {DoubleClick} from "../../src/interaction/library/DoubleClick";
import {EventRegistrationToken} from "../../src/interaction/Events";
import {createMouseEvent} from "./StubEvents";

jest.mock("../fsm/StubFSMHandler");

let interaction: DoubleClick;
let canvas: HTMLElement;
let handler: FSMHandler;

beforeEach(() => {
    jest.clearAllMocks();
    jest.useFakeTimers();
    handler = new StubFSMHandler();
    interaction = new DoubleClick();
    interaction.log(true);
    interaction.getFsm().log(true);
    interaction.getFsm().addHandler(handler);
    document.documentElement.innerHTML = "<html><div><canvas id='canvas1' /></div></html>";
    const elt = document.getElementById("canvas1");
    if (elt !== null) {
        canvas = elt;
    }
});

test("Double click on a canvas starts and stops the interaction", () => {
    interaction.registerToNodes([canvas]);
    canvas.click();
    canvas.click();
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
});

test("Move between clicks cancels the double click", () => {
    interaction.registerToNodes([canvas]);
    canvas.click();
    canvas.dispatchEvent(createMouseEvent(EventRegistrationToken.MouseMove, canvas));
    canvas.click();
    expect(handler.fsmStarts).not.toHaveBeenCalled();
    expect(handler.fsmStops).not.toHaveBeenCalled();
});

test("Timout cancels the double click", () => {
    interaction.registerToNodes([canvas]);
    canvas.click();
    jest.runOnlyPendingTimers();
    canvas.click();
    expect(handler.fsmStarts).not.toHaveBeenCalled();
    expect(handler.fsmStops).not.toHaveBeenCalled();
});

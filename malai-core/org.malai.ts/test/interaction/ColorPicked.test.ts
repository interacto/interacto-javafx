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

import {FSMHandler} from "../../src/src-core/fsm/FSMHandler";
import {StubFSMHandler} from "../fsm/StubFSMHandler";
import {ColorPicked} from "../../src/interaction/library/ColorPicked";

jest.mock("../fsm/StubFSMHandler");

let interaction: ColorPicked;
let colorBox: HTMLElement;
let handler: FSMHandler;

beforeEach(() => {
    jest.clearAllMocks();
    handler = new StubFSMHandler();
    interaction = new ColorPicked();
    interaction.log(true);
    interaction.getFsm().log(true);
    interaction.getFsm().addHandler(handler);
    document.documentElement.innerHTML = "<html><div><input id='col1' type='color'></div></html>";
    const elt = document.getElementById("col1");
    if (elt !== null) {
        colorBox = elt;
    }
});

test("Click on colorbox starts and stops the interaction", () => {
    interaction.registerToNodes([colorBox]);
    colorBox.dispatchEvent(new Event("input"));
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
});

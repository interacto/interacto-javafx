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
import {DatePicked} from "../../src/interaction/library/DatePicked";

jest.mock("../fsm/StubFSMHandler");

let interaction: DatePicked;
let date: HTMLElement;
let handler: FSMHandler;

beforeEach(() => {
    jest.clearAllMocks();
    handler = new StubFSMHandler();
    interaction = new DatePicked();
    interaction.log(true);
    interaction.getFsm().log(true);
    interaction.getFsm().addHandler(handler);
    document.documentElement.innerHTML = "<html><div><input id='dt1' type='date'></div></html>";
    const elt = document.getElementById("dt1");
    if (elt !== null) {
        date = elt;
    }
});

test("Input event starts and stops the interaction DatePicked.", () => {
    interaction.registerToNodes([date]);
    date.dispatchEvent(new Event("input"));
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
});

test("Other event don't trigger the interaction.", () => {
    interaction.registerToNodes([date]);
    date.dispatchEvent(new Event("load"));
    expect(handler.fsmStarts).not.toHaveBeenCalled();
});

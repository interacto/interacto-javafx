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
import {ComboBoxSelected} from "../../src/interaction/library/ComboBoxSelected";

jest.mock("../fsm/StubFSMHandler");

let interaction: ComboBoxSelected;
let comboBox: HTMLElement;
let handler: FSMHandler;

beforeEach(() => {
    jest.clearAllMocks();
    handler = new StubFSMHandler();
    interaction = new ComboBoxSelected();
    interaction.log(true);
    interaction.getFsm().log(true);
    interaction.getFsm().addHandler(handler);
    document.documentElement.innerHTML = "<html><div><input id='comb1' list='test'><datalist id='test'>" +
        "<option value='test1'>Test1</option> </datalist> </div></html>";
    const elt = document.getElementById("comb1");
    if (elt !== null) {
        comboBox = elt;
    }
});

test("Click on combobox starts and stops the interaction", () => {
    interaction.registerToNodes([comboBox]);
    comboBox.dispatchEvent(new Event("input"));
    expect(handler.fsmStops).toHaveBeenCalledTimes(1);
    expect(handler.fsmStarts).toHaveBeenCalledTimes(1);
});

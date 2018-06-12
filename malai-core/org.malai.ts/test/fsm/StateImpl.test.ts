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

import {FSM} from "../../src/src-core/fsm/FSM";
import {StateImpl} from "../../src/src-core/fsm/StateImpl";
import {StdState} from "../../src/src-core/fsm/StdState";
import {StubEvent} from "./StubEvent";
import "jest";

let state: StateImpl<StubEvent>;
let fsm: FSM<StubEvent>;

beforeEach(() => {
    fsm = new FSM<StubEvent>();
    state = new StdState<StubEvent>(fsm, "s1");
});

test("testFSM", () => {
    expect(state.getFSM()).toEqual(fsm);
});

test("testName", () => {
    expect(state.getName()).toEqual("s1");
});

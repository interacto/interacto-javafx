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

import {StdState} from "../../src-core/fsm/StdState";
import {Transition} from "../../src-core/fsm/Transition";
import {FSM} from "../../src-core/fsm/FSM";
import {StubEvent} from "./StubEvent";
import {StubTransitionOK} from "./StubTransitionOK";
import {expect} from "chai";

let tr: Transition<StubEvent>;
let state1: StdState<StubEvent>;
let state2: StdState<StubEvent>;


beforeEach(() => {
    const fsm: FSM<StubEvent> = new FSM<StubEvent>();
    state1 = new StdState<StubEvent>(fsm, "s1");
    state2 = new StdState<StubEvent>(fsm, "s2");
    tr = new StubTransitionOK(state1, state2);
});

test("testGoodSrc", () => {
    expect(tr.src).to.equals(state1);
});

test("testGoodTgt", () => {
    expect(tr.tgt).to.equals(state2);
});

test("testSrcStateTransitionAdded", () => {
    expect(state1.getTransitions().length).to.equals(1);
    expect(state1.getTransitions()[0]).to.equals(tr);
});

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

import {StubEvent} from "./StubEvent";
import {FSM} from "../../src/src-core/fsm/FSM";
import {InitState} from "../../src/src-core/fsm/InitState";
import "jest";
import {StdState} from "../../src/src-core/fsm/StdState";

let fsm : FSM<StubEvent>;

beforeEach(() => {
    fsm = new FSM<StubEvent>();
});

test("testInitState", () => {
    expect(fsm.getStates().length).toBe(1);
    expect(fsm.getStates()[0]).toBeInstanceOf(InitState);
});

test("testInner", () => {
    expect(fsm.inner).toBeFalsy();
});

test("testStartingState", () => {
    expect(fsm.startingState).toEqual(fsm.initState);
});

test("testCurrentStateAtStart", () => {
    expect(fsm.currentState).toEqual(fsm.initState);
});

test("testAddState", () => {
    const state : StdState<StubEvent> = new StdState<StubEvent>(fsm, "s1");
    fsm.addState(state);
    expect(fsm.getStates().length).toBe(2);
});

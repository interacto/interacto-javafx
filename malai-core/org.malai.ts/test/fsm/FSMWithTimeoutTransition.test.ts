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
import {FSMHandler} from "../../src/src-core/fsm/FSMHandler";
import {StdState} from "../../src/src-core/fsm/StdState";
import {TimeoutTransition} from "../../src/src-core/fsm/TimeoutTransition";
import {TerminalState} from "../../src/src-core/fsm/TerminalState";
import {StubTransitionOK} from "./StubTransitionOK";
import {StubFSMHandler} from "./StubFSMHandler";
import {CancelFSMException} from "../../src/src-core/fsm/CancelFSMException";

jest.mock("./StubFSMHandler");

let fsm: FSM<StubEvent>;
let handler: FSMHandler;
let std: StdState<StubEvent>;
let std2: StdState<StubEvent>;
let terminal: TerminalState<StubEvent>;

beforeEach(() => {
    jest.clearAllMocks();
    jest.useFakeTimers();
    fsm = new FSM();
    handler = new StubFSMHandler();
    fsm.addHandler(handler);
    fsm.log(true);
    std = new StdState(fsm, "s1");
    std2 = new StdState(fsm, "s2");
    terminal = new TerminalState(fsm, "t1");
    new StubTransitionOK(fsm.initState, std);
    new StubTransitionOK(std, terminal);
    new TimeoutTransition(std, std2, () => 500);
    new StubTransitionOK(std2, std);
    fsm.addState(std);
    fsm.addState(std2);
    fsm.addState(terminal);
});

test("testTimeoutChangeState", () => {
    fsm.process(new StubEvent());
    jest.runOnlyPendingTimers();
    expect(setTimeout).toHaveBeenCalledTimes(1);
    expect(setTimeout).toHaveBeenLastCalledWith(expect.any(Function), 500);
    expect(fsm.currentState).toEqual(std2);
});

test("testTimeoutStoppedOnOtherTransition", () => {
    fsm.process(new StubEvent());
    fsm.process(new StubEvent());
    expect(setTimeout).toHaveBeenCalledTimes(1);
    expect(clearTimeout).toHaveBeenCalledTimes(1);
    expect(fsm.currentState).toEqual(fsm.initState);
});

test("testTimeoutChangeStateThenCancel", () => {
    handler.fsmUpdates = jest.fn().mockImplementation(() => {
        throw new CancelFSMException();
    });
    fsm.process(new StubEvent());
    jest.runOnlyPendingTimers();
    expect(fsm.currentState).toEqual(fsm.initState);
    expect(handler.fsmCancels).toHaveBeenCalledTimes(1);
});

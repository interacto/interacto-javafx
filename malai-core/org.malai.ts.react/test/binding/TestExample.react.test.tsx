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

import * as renderer from "react-test-renderer";
import {TestComponent} from "../../test.React.Component/TestComponent";
import * as React from "react";

jest.mock("../command/StubCmd");


test("test testComponent", () => {
    const component = renderer.create(
        <TestComponent><div><text>Test</text></div></TestComponent> ,
    );
    const tree = component.toJSON();
    expect(tree).toMatchSnapshot();
});

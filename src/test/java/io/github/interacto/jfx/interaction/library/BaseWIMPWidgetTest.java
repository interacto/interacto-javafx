/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.CancelFSMException;
import io.github.interacto.fsm.InitState;
import io.github.interacto.jfx.TimeoutWaiter;
import io.github.interacto.jfx.interaction.JfxInteraction;
import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseWIMPWidgetTest<W extends Node, I extends JfxInteraction<?, ?, W>> extends BaseJfXInteractionTest<I>
		implements TimeoutWaiter {
	W wimpWidget;

	abstract void triggerWidget();

	abstract W createWidget();

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		wimpWidget = createWidget();
	}

	@Test
	void testProcessEventGoodState() throws CancelFSMException {
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testProcessEventGoodStateNULL() throws CancelFSMException {
		interaction.processEvent(null);
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testProcessEventGoodDataOnStart() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(wimpWidget, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		waitForTimeoutTransitions();
	}

	@Test
	void testProcessEventGoodDataOnStop() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(wimpWidget, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		waitForTimeoutTransitions();
	}

	@Test
	void testProcessEventReinit() {
		interaction.processEvent(new ActionEvent(wimpWidget, null));
		waitForTimeoutTransitions();
		assertNull(interaction.getWidget());
		assertTrue(interaction.getFsm().getCurrentState() instanceof InitState);
	}

	@Test
	void testRegister() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		triggerWidget();
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testNoActionWhenNotRegistered() throws CancelFSMException {
		triggerWidget();
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenWrongRegistered() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(createWidget()));
		triggerWidget();
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenNullRegistered() throws CancelFSMException {
		interaction.registerToNodes(null);
		triggerWidget();
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(null));
		triggerWidget();
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testUnRegisterNode() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		interaction.unregisterFromNodes(Collections.singletonList(wimpWidget));
		triggerWidget();
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}

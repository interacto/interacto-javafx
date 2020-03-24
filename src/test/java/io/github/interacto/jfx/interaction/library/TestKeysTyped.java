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

import io.github.interacto.jfx.TimeoutWaiter;
import io.github.interacto.fsm.CancelFSMException;
import java.util.Collections;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestKeysTyped extends BaseJfXInteractionTest<KeysTyped> implements TimeoutWaiter {
	@Override
	KeysTyped createInteraction() {
		return new KeysTyped();
	}

	@Test
	public void testKeyTypedSleepExecutionOK() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	public void testKeyTypedSleepDataOK() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getData().getKeys().get(0));
			}
		});
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		waitForTimeoutTransitions();
	}

	@Test
	public void testKeyTypedSleepCleanOK() {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		waitForTimeoutTransitions();
		assertTrue(interaction.getData().getKeyCodes().isEmpty());
		assertTrue(interaction.getData().getKeys().isEmpty());
	}

	@Test
	public void testKeyTypedExecutionOK() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	public void testKeyTypedDataOK() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals("A", interaction.getData().getKeys().get(0));
			}
		});
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
	}

	@Test
	public void testTwoKeysTypedExecutionOK() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	public void testTwoKeysTypedDataOK() {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals("A", interaction.getData().getKeys().get(0));
				assertEquals("B", interaction.getData().getKeys().get(1));
			}
		});
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
	}

	@Test
	public void testTwoKeysTypedSleepExecutionOK() throws CancelFSMException {
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}

	@Test
	public void testTwoKeysTypedSleepDataOK() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getData().getKeys().get(0));
				assertEquals("B", interaction.getData().getKeys().get(1));
			}
		});
		interaction.processEvent(createKeyTypedEvent("A", KeyCode.A));
		interaction.processEvent(createKeyTypedEvent("B", KeyCode.B));
		waitForTimeoutTransitions();
	}

	@Test
	void testKeyTypedOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyTypedOnRegUnregWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createKeyTypedEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}

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
public class TestKeysScroll extends BaseJfXInteractionTest<KeysScroll> {
	@Override
	KeysScroll createInteraction() {
		return new KeysScroll();
	}

	@Test
	public void testKeyPressExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(1, interaction.getData().getKeys().size());
				assertEquals("A", interaction.getData().getKeys().get(0));
			}
		});
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
	}

	@Test
	public void testTwoKeyPressExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
	}

	@Test
	void testTwoKeyPressData() {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(2, interaction.getData().getKeys().size());
				assertEquals("A", interaction.getData().getKeys().get(0));
				assertEquals("B", interaction.getData().getKeys().get(1));
			}
		});
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
	}

	@Test
	public void testKeyPressReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
		Mockito.verify(handler, Mockito.never()).fsmStops();
	}

	@Test
	public void testTwoKeyPressReleaseDataCleaned() {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		assertTrue(interaction.getData().getKeyCodes().isEmpty());
		assertTrue(interaction.getData().getKeys().isEmpty());
	}

	@Test
	public void testTwoKeyPressReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	public void testScroll() throws CancelFSMException {
		interaction.processEvent(createScrollEvent(10, 20, 1, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	public void testKeyPressScroll() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createScrollEvent(10, 20, 1, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	public void testKeyPressScrollData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(1, interaction.getData().getIncrement(), 0.0001);
				assertEquals(10, interaction.getData().getPx(), 0.0001);
				assertEquals(20, interaction.getData().getPy(), 0.0001);
			}
		});
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createScrollEvent(10, 20, 1, 1));
	}

	@Test
	public void testKeyPressScrollRecycle() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createScrollEvent(10, 20, 1, 0));
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
		assertEquals(1, interaction.getData().getKeyCodes().size());
	}

	@Test
	public void testKeyPressPressReleaseScroll() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyPressEvent("C", KeyCode.C));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		interaction.processEvent(createScrollEvent(10, 20, 1, 0));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testKeyPressOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createKeyPressEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyTypedOnRegWidgetData() {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getData().getKeys().get(0));
			}
		});
		pane.fireEvent(createKeyPressEvent("A", KeyCode.A));
	}
}

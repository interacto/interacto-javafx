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

@ExtendWith(ApplicationExtension.class)
public class TestKeysPressed extends BaseJfXInteractionTest<KeysPressed> {
	@Override
	KeysPressed createInteraction() {
		return new KeysPressed();
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
				assertEquals(1, interaction.getKeys().size());
				assertEquals("A", interaction.getKeys().get(0));
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
				assertEquals(2, interaction.getKeys().size());
				assertEquals("A", interaction.getKeys().get(0));
				assertEquals("B", interaction.getKeys().get(1));
			}
		});
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
	}

	@Test
	public void testTwoKeyPressReleaseExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
	}

	@Test
	void testTwoKeyPressReleaseData() {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(1, interaction.getKeys().size());
				assertEquals("A", interaction.getKeys().get(0));
			}
		});
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
	}

	@Test
	public void testTwoKeyPressReleaseRecycle() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
	}

	@Test
	public void testTwoKeyPressTwoReleasesExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		interaction.processEvent(createKeyPressEvent("B", KeyCode.B));
		interaction.processEvent(createKeyReleaseEvent("A", KeyCode.A));
		interaction.processEvent(createKeyReleaseEvent("B", KeyCode.B));
		Mockito.verify(handler, Mockito.times(2)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmStops();
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
				assertEquals("A", interaction.getKeys().get(0));
			}
		});
		pane.fireEvent(createKeyPressEvent("A", KeyCode.A));
	}
}

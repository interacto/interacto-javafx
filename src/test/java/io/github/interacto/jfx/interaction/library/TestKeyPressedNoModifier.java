/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
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
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestKeyPressedNoModifier extends BaseJfXInteractionTest<KeyPressed> {
	@Override
	KeyPressed createInteraction() {
		return new KeyPressed(false);
	}

	@Test
	public void testKeyPressExecution() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testKeyPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals("A", interaction.getKey());
				assertEquals(KeyCode.A, interaction.getKeyCode());
			}
		});
		interaction.processEvent(createKeyPressEvent("A", KeyCode.A));
	}

	@Test
	public void testKeyPressExecutionKOCtrl() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("CONTROL", KeyCode.CONTROL));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	public void testKeyPressExecutionKOAlt() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("ALT", KeyCode.ALT));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	public void testKeyPressExecutionKOMeta() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("META", KeyCode.META));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	public void testKeyPressExecutionKOCmd() throws CancelFSMException {
		interaction.processEvent(createKeyPressEvent("COMMAND", KeyCode.COMMAND));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}

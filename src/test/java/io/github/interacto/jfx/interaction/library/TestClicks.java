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
import io.github.interacto.jfx.TimeoutWaiter;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
public class TestClicks extends BaseJfXInteractionTest<Clicks> implements TimeoutWaiter {
	@Override
	Clicks createInteraction() {
		return new Clicks(4);
	}

	@Test
	void testBuild() {
		interaction.getFsm().buildFSM(null);
		assertEquals(4, interaction.getFsm().getStates().size());
	}

	@Test
	void test1Click() throws CancelFSMException {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(1, interaction.getData().getPointsData().size());
				assertEquals(11, interaction.getData().getPointsData().get(0).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getPointsData().get(0).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getPointsData().get(0).getButton());
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void test2Click() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(2, interaction.getData().getPointsData().size());
				assertEquals(11, interaction.getData().getPointsData().get(0).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getPointsData().get(0).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getPointsData().get(0).getButton());
				assertEquals(12, interaction.getData().getPointsData().get(1).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(24, interaction.getData().getPointsData().get(1).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getData().getPointsData().get(1).getButton());
			}
		});
		interaction.processEvent(createMouseClickEvent(12, 24, MouseButton.SECONDARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void test3Click() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(12, 24, MouseButton.MIDDLE, null));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(3, interaction.getData().getPointsData().size());
				assertEquals(13, interaction.getData().getPointsData().get(2).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(25, interaction.getData().getPointsData().get(2).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getData().getPointsData().get(2).getButton());
			}
		});
		interaction.processEvent(createMouseClickEvent(13, 25, MouseButton.SECONDARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void test4Click() throws CancelFSMException {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(4, interaction.getData().getPointsData().size());
				assertEquals(14, interaction.getData().getPointsData().get(3).getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(26, interaction.getData().getPointsData().get(3).getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getData().getPointsData().get(3).getButton());
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(12, 24, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(13, 25, MouseButton.PRIMARY, null));
		interaction.processEvent(createMouseClickEvent(14, 26, MouseButton.SECONDARY, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(3)).fsmUpdates();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void clickTimeout1() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
	}

	@Test
	void clickTimeout2() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmCancels();
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 1})
	void testKOClick(final int nbClick) {
		assertThrows(IllegalArgumentException.class, () -> new Clicks(nbClick));
	}
}

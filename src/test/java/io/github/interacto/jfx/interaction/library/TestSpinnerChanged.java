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
import io.github.interacto.fsm.InitState;
import io.github.interacto.jfx.TimeoutWaiter;
import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestSpinnerChanged extends BaseJfXInteractionTest<SpinnerChanged> implements TimeoutWaiter {
	Spinner<?> widget;
	long timer;

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		widget = new Spinner<>();
		timer = SpinnerChangedFSM.getTimeGap();
	}

	@AfterEach
	void tearDown() {
		SpinnerChangedFSM.setTimeGap(timer);
	}

	@Override
	protected SpinnerChanged createInteraction() {
		return new SpinnerChanged();
	}

	@Nested
	class TestNominal {
		@BeforeEach
		void setUp() {
			interaction.registerToNodes(Collections.singletonList(widget));
		}

		@Test
		void testSpinnerChangedGoodState() throws CancelFSMException {
			widget.fireEvent(new ActionEvent(widget, null));
			waitForTimeoutTransitions();
			Mockito.verify(handler, Mockito.times(1)).fsmStops();
			Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		}

		@Test
		void testSpinnerChange2TimesGoodState() throws CancelFSMException {
			widget.fireEvent(new ActionEvent(widget, null));
			widget.fireEvent(new ActionEvent(widget, null));
			waitForTimeoutTransitions();
			Mockito.verify(handler, Mockito.times(1)).fsmStops();
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
			Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		}

		@Test
		void testSpinnerChangedGoodStateWithTimeGap() throws CancelFSMException {
			SpinnerChangedFSM.setTimeGap(50L);
			widget.fireEvent(new ActionEvent(widget, null));
			waitForTimeoutTransitions();
			Mockito.verify(handler, Mockito.times(1)).fsmStops();
			Mockito.verify(handler, Mockito.times(1)).fsmStarts();
			Mockito.verify(handler, Mockito.times(1)).fsmUpdates();
		}

		@Test
		void testSpinnerChangeTwoTimesWith500GoodState() throws CancelFSMException {
			widget.fireEvent(new ActionEvent(widget, null));
			waitForTimeoutTransitions();
			widget.fireEvent(new ActionEvent(widget, null));
			waitForTimeoutTransitions();
			Mockito.verify(handler, Mockito.times(2)).fsmStops();
			Mockito.verify(handler, Mockito.times(2)).fsmStarts();
			Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		}
	}

	@Test
	void testProcessEventGoodState() throws CancelFSMException {
		interaction.processEvent(new ActionEvent(widget, null));
		waitForTimeoutTransitions();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testProcessEventGoodStateNULL() throws CancelFSMException {
		interaction.processEvent(null);
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testProcessEventGoodData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				Assertions.assertEquals(widget, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(widget, null));
	}

	@Test
	void testProcessEventReinit() {
		interaction.processEvent(new ActionEvent(widget, null));
		waitForTimeoutTransitions();
		assertNull(interaction.getWidget());
		assertTrue(interaction.getFsm().getCurrentState() instanceof InitState);
	}

	@Test
	void testNoActionWhenNotRegistered() throws CancelFSMException {
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenWrongRegistered() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(new Spinner<>()));
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenNullRegistered() throws CancelFSMException {
		interaction.registerToNodes(null);
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testNoActionWhenContainsNullRegistered() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(null));
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testUnRegisterNode() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(widget));
		interaction.unregisterFromNodes(Collections.singletonList(widget));
		widget.fireEvent(new ActionEvent(widget, null));
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}

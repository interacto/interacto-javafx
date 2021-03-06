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
import io.github.interacto.fsm.FSMHandler;
import java.util.Collections;
import java.util.List;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestMouseExited extends BaseJfXInteractionTest<MouseExited> {
	@Override
	MouseExited createInteraction() {
		return new MouseExited();
	}

	@Test
	void testMouseExitedExecution() throws CancelFSMException {
		interaction.processEvent(createMouseExitEvent(11, 23, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testMouseExitedData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
			}
		});
		interaction.processEvent(createMouseExitEvent(11, 23, MouseButton.MIDDLE));
	}

	@Test
	void testMouseExitedOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseExitEvent(11, 23, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testMouseExitedOnRegWidgetData() {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
			}
		});
		pane.fireEvent(createMouseExitEvent(11, 23, MouseButton.MIDDLE));
	}

	@Test
	void testPressOnUnregWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseExitEvent(11, 23, MouseButton.MIDDLE));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testEventConsumed() throws CancelFSMException {
		final Pane pane1 = new Pane();
		final Pane pane2 = new Pane();
		pane1.getChildren().add(pane2);

		final FSMHandler handler2 = Mockito.mock(FSMHandler.class);
		final MouseExited i2 = createInteraction();
		i2.getFsm().addHandler(handler2);

		interaction.registerToNodes(List.of(pane1));
		i2.registerToNodes(List.of(pane2));
		i2.setConsumeEvents(true);
		pane2.fireEvent(createMouseExitEvent(11, 23, MouseButton.MIDDLE));

		Mockito.verify(handler2, Mockito.times(1)).fsmStops();
		Mockito.verify(handler2, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}

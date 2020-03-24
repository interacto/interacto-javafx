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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class TestClick extends BaseJfXInteractionTest<Click> {
	@Override
	Click createInteraction() {
		return new Click();
	}

	@Test
	void testClickExecution() throws CancelFSMException {
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testClickData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				assertEquals(11, interaction.getData().getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getData().getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(MouseButton.MIDDLE, interaction.getData().getButton());
			}
		});
		interaction.processEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, null));
	}

	@Test
	void testClickOnRegWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
	}

	@Test
	void testClickOnWidgetData() {
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
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
	}

	@Test
	void testClickOnUnregWidget() throws CancelFSMException {
		final Pane pane = new Pane();
		interaction.registerToNodes(Collections.singletonList(pane));
		interaction.unregisterFromNodes(Collections.singletonList(pane));
		pane.fireEvent(createMouseClickEvent(11, 23, MouseButton.MIDDLE, pane));
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}
}

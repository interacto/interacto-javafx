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
import java.util.Collections;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class TestTabSelected extends BaseWIMPWidgetTest<TabPane, TabSelected> {
	Tab tab1;
	Tab tab2;

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		tab1 = new Tab();
		tab2 = new Tab();
		wimpWidget.getTabs().addAll(tab1);
		wimpWidget.getTabs().addAll(tab2);
		wimpWidget.getSelectionModel().select(0);
	}

	@Override
	TabPane createWidget() {
		return new TabPane();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new TabEvent(wimpWidget, null));
	}

	@Override
	protected TabSelected createInteraction() {
		return new TabSelected();
	}

	@Override
	@Test
	void testProcessEventGoodState() throws CancelFSMException {
		interaction.processEvent(new TabEvent(wimpWidget, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Override
	@Test
	void testRegister() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		wimpWidget.getSelectionModel().select(1);
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}
}

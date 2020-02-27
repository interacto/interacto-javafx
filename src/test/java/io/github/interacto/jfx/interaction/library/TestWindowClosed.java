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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
public class TestWindowClosed extends BaseJfXInteractionTest<WindowClosed> {
	Window window;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		Platform.runLater(() -> {
			final Stage stage = new Stage();
			final Scene scene = new Scene(new Button());
			stage.setScene(scene);
			window = stage.getScene().getWindow();
		});
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Override
	protected WindowClosed createInteraction() {
		return new WindowClosed();
	}

	@Test
	void testWindowClosedGoodState() throws CancelFSMException {
		interaction.processEvent(createWindowEvent(window, javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}


	@Test
	void testRegisterNode() throws CancelFSMException {
		interaction.registerToWindows(Collections.singletonList(window));
		Platform.runLater(() -> window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST)));
		WaitForAsyncUtils.waitForFxEvents();
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Test
	void testUnRegisterNode() throws CancelFSMException {
		interaction.registerToWindows(Collections.singletonList(window));
		interaction.unregisterFromWindows(Collections.singletonList(window));
		Platform.runLater(() -> window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST)));
		WaitForAsyncUtils.waitForFxEvents();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testGoodStateNULL() throws CancelFSMException {
		interaction.processEvent(null);
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmStarts();
	}

	@Test
	void testClickGoodData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStops() {
				Assertions.assertEquals(window, interaction.getWidget());
			}
		});
		interaction.processEvent(new ActionEvent(window, null));
	}
}

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
import io.github.interacto.fsm.FSMHandler;
import io.github.interacto.jfx.interaction.JfxInteraction;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public abstract class BaseJfXInteractionTest<T extends JfxInteraction<?, ?, ?>> extends ApplicationTest {
	T interaction;
	FSMHandler handler;

	@BeforeEach
	void setUp() {
		handler = Mockito.mock(FSMHandler.class);
		interaction = createInteraction();
		interaction.getFsm().addHandler(handler);
	}

	abstract T createInteraction();

	Window createWindow() {
		final Scene scene = new Scene(new Button());
		Platform.runLater(() -> {
			final Stage stage = new Stage();
			stage.setScene(scene);
		});
		WaitForAsyncUtils.waitForFxEvents();
		return scene.getWindow();
	}

	void checkNoUseOfHandler() throws CancelFSMException {
		Mockito.verify(handler, Mockito.never()).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	static WindowEvent createWindowEvent(final Window window, final EventType<?> eventType) {
		return new WindowEvent(window, eventType);
	}

	static MouseEvent createMousePressEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_PRESSED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true, false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	static MouseEvent createMouseReleaseEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_RELEASED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true, false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	static MouseEvent createMouseMoveEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_MOVED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true, false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	static MouseEvent createMouseDragEvent(final double x, final double y, final MouseButton button, final Node srcObj) {
		final PickResult res = Mockito.mock(PickResult.class);
		Mockito.when(res.getIntersectedNode()).thenReturn(srcObj);
		Mockito.when(res.getIntersectedPoint()).thenReturn(new Point3D(x, y, 0d));
		return new MouseEvent(MouseEvent.MOUSE_DRAGGED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true, false, false, res);
	}

	static MouseEvent createMouseClickEvent(final double x, final double y, final MouseButton button, final Node srcObj) {
		final PickResult res = Mockito.mock(PickResult.class);
		Mockito.when(res.getIntersectedNode()).thenReturn(srcObj);
		Mockito.when(res.getIntersectedPoint()).thenReturn(new Point3D(x, y, 0d));
		return new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true, false, false, res);
	}

	static ScrollEvent createScrollEvent(final double x, final double y, final double dx, final double dy) {
		return new ScrollEvent(ScrollEvent.SCROLL, x, y, x, y, false, false, false, false, false, false, dx, dy, dx, dy,
				null, 0d, null, 0d, 0, new PickResult(null, new Point3D(x, y, 0d),
				0d, 0, new Point2D(0d, 0d)));
	}

	static KeyEvent createKeyPressEvent(final String str, final KeyCode code) {
		return new KeyEvent(new Object(), tail -> null, KeyEvent.KEY_PRESSED, str, str, code, false,
			false, false, false);
	}

	static KeyEvent createKeyReleaseEvent(final String str, final KeyCode code) {
		return new KeyEvent(new Object(), tail -> null, KeyEvent.KEY_RELEASED, str, str, code, false,
			false, false, false);
	}

	static KeyEvent createKeyTypedEvent(final String str, final KeyCode code) {
		return new KeyEvent(new Object(), tail -> null, KeyEvent.KEY_TYPED, str, str, code, false,
			false, false, false);
	}

	static class InteractionHandlerStub implements FSMHandler {
		@Override
		public void fsmStarts() {
		}

		@Override
		public void fsmUpdates() {
		}

		@Override
		public void fsmStops() {
		}

		@Override
		public void fsmCancels() {
		}
	}
}

package org.malai.javafx.interaction.library;

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
import org.malai.fsm.CancelFSMException;
import org.malai.fsm.FSMHandler;
import org.malai.javafx.interaction.JfxInteraction;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public abstract class BaseJfXInteractionTest<T extends JfxInteraction<?, ?>> extends ApplicationTest {
	T interaction;
	FSMHandler handler;
//	@Mock MouseEvent evt;

	@BeforeEach
	void setUp() {
		handler = Mockito.mock(FSMHandler.class);
		interaction = createInteraction();
		interaction.getFsm().addHandler(handler);
	}

	abstract T createInteraction();

	Window createWindow() {
		Scene scene = new Scene(new Button());
		Platform.runLater(() -> {
			Stage stage = new Stage();
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
			true,false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	static MouseEvent createMouseReleaseEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_RELEASED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	static MouseEvent createMouseMoveEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_MOVED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	static MouseEvent createMouseDragEvent(final double x, final double y, final MouseButton button, final Node srcObj) {
		final PickResult res = Mockito.mock(PickResult.class);
		Mockito.when(res.getIntersectedNode()).thenReturn(srcObj);
		Mockito.when(res.getIntersectedPoint()).thenReturn(new Point3D(x, y, 0d));
		return new MouseEvent(MouseEvent.MOUSE_DRAGGED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, res);
	}

	static MouseEvent createMouseClickEvent(final double x, final double y, final MouseButton button, final Node srcObj) {
		final PickResult res = Mockito.mock(PickResult.class);
		Mockito.when(res.getIntersectedNode()).thenReturn(srcObj);
		Mockito.when(res.getIntersectedPoint()).thenReturn(new Point3D(x, y, 0d));
		return new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, res);
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
		public void fsmStarts() throws CancelFSMException {
		}

		@Override
		public void fsmUpdates() throws CancelFSMException {
		}

		@Override
		public void fsmStops() throws CancelFSMException {
		}

		@Override
		public void fsmCancels() {
		}
	}
}

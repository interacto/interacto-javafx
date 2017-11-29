package org.malai.javafx.interaction.library;

import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.junit.jupiter.api.BeforeEach;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;

public abstract class BaseJfXInteractionTest<T extends JfxInteraction> extends ApplicationTest {
	T interaction;
	@Mock InteractionHandler handler;
	@Mock MouseEvent evt;

	@BeforeEach
	void setUp() {
		interaction = createInteraction();
		interaction.addHandler(handler);
	}

	abstract T createInteraction();

	WindowEvent createWindowEvent(final Window window, final EventType<?> eventType) {
		return new WindowEvent(window, eventType);
	}

	MouseEvent createMousePressEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_PRESSED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	MouseEvent createMouseReleaseEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_RELEASED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	MouseEvent createMouseMoveEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_MOVED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, new PickResult(null, new Point3D(x, y, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	ScrollEvent createScrollEvent(final double x, final double y, final double dx, final double dy) {
		return new ScrollEvent(ScrollEvent.SCROLL, x, y, x, y, false, false, false, false, false, false, dx, dy, dx, dy,
				null, 0d, null, 0d, 0, new PickResult(null, new Point3D(x, y, 0d),
				0d, 0, new Point2D(0d, 0d)));
	}

	KeyEvent createKeyPressEvent(final String str, final KeyCode code) {
		return new KeyEvent(new Object(), tail -> null, KeyEvent.KEY_PRESSED, str, str, code, false,
			false, false, false);
	}

	KeyEvent createKeyReleaseEvent(final String str, final KeyCode code) {
		return new KeyEvent(new Object(), tail -> null, KeyEvent.KEY_RELEASED, str, str, code, false,
			false, false, false);
	}

	static class InteractionHandlerStub implements InteractionHandler {
		@Override
		public void interactionStarts(final Interaction interaction) throws MustAbortStateMachineException {

		}

		@Override
		public void interactionUpdates(final Interaction interaction) throws MustAbortStateMachineException {

		}

		@Override
		public void interactionStops(final Interaction interaction) throws MustAbortStateMachineException {

		}

		@Override
		public void interactionAborts(final Interaction interaction) {

		}
	}
}

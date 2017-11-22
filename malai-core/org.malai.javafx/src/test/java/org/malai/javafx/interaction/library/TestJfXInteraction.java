package org.malai.javafx.interaction.library;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.junit.Before;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.stateMachine.MustAbortStateMachineException;

public abstract class TestJfXInteraction<T extends JfxInteraction> {
	protected T interaction;

	@Before
	public void setUp() throws Exception {
		interaction = createInteraction();
	}

	protected abstract T createInteraction();

	protected MouseEvent createMousePressEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_PRESSED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, new PickResult(null, new Point3D(0d, 0d, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	protected MouseEvent createMouseReleaseEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_RELEASED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, new PickResult(null, new Point3D(0d, 0d, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	protected MouseEvent createMouseMoveEvent(final double x, final double y, final MouseButton button) {
		return new MouseEvent(MouseEvent.MOUSE_MOVED, x, y, 0d, 0d, button, 1, false,
			false, false, false, false, false, false,
			true,false, false, new PickResult(null, new Point3D(0d, 0d, 0d),
			0d, 0, new Point2D(0d, 0d)));
	}

	protected KeyEvent createKeyPressEvent(final String str, final KeyCode code) {
		return new KeyEvent(new Object(), tail -> null, KeyEvent.KEY_PRESSED, str, str, code, false,
			false, false, false);
	}

	protected KeyEvent createKeyReleaseEvent(final String str, final KeyCode code) {
		return new KeyEvent(new Object(), tail -> null, KeyEvent.KEY_RELEASED, str, str, code, false,
			false, false, false);
	}

	protected static class InteractionHandlerStub implements InteractionHandler {
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

package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.Interaction;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestPress extends BaseJfXInteractionTest<Press> {
	@Override
	protected Press createInteraction() {
		return new Press();
	}

	@Test
	void TestPressOK() throws MustAbortStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.never()).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);
	}

	@Test
	void TestPressGoodData() throws MustAbortStateMachineException {
		interaction.addHandler(new InteractionHandlerStub() {
			@Override
			public void interactionStarts(final Interaction interaction) throws MustAbortStateMachineException {
				super.interactionStarts(interaction);
				assertEquals(new Point3D(10d, 20d, 0d), ((Press) interaction).getSrcPoint().get());
				assertEquals(MouseButton.SECONDARY, ((Press) interaction).getButton().get());
			}
		});
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
	}

	@Nested
	class onWidget {
		Canvas canvas;

		@BeforeEach
		void setUp() {
			canvas = new Canvas();
		}

		@Test
		void TestPressOKRegistration() throws MustAbortStateMachineException {
			interaction.registerToNodes(Collections.singletonList(canvas));
			canvas.fireEvent(createMousePressEvent(11d, 23d, MouseButton.MIDDLE));

			Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
			Mockito.verify(handler, Mockito.never()).interactionUpdates(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);
		}

		@Test
		void TestPressKONoRegistration() throws MustAbortStateMachineException {
			canvas.fireEvent(createMousePressEvent(11d, 23d, MouseButton.MIDDLE));
			checkNoUseOfHandler();
		}

		@Test
		void TestPressKONULLRegistration() throws MustAbortStateMachineException {
			interaction.registerToNodes(null);
			canvas.fireEvent(createMousePressEvent(11d, 23d, MouseButton.MIDDLE));
			checkNoUseOfHandler();
		}

		@Test
		void TestPressKOListContainsNULLRegistration() throws MustAbortStateMachineException {
			interaction.registerToNodes(Collections.singletonList(null));
			canvas.fireEvent(createMousePressEvent(11d, 23d, MouseButton.MIDDLE));
			checkNoUseOfHandler();
		}
	}

	@Nested
	class onWinwdow {
		Window window;

		@BeforeEach
		void setUp() {
			window = createWindow();
		}

		@Test
		void TestPressOKRegistration() throws MustAbortStateMachineException {
			interaction.registerToWindows(Collections.singletonList(window));
			window.fireEvent(createMousePressEvent(11d, 23d, MouseButton.MIDDLE));

			Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
			Mockito.verify(handler, Mockito.never()).interactionUpdates(interaction);
			Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
			Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);
		}

		@Test
		void TestPressKONoRegistration() throws MustAbortStateMachineException {
			window.fireEvent(createMousePressEvent(11d, 23d, MouseButton.MIDDLE));
			checkNoUseOfHandler();
		}

		@Test
		void TestPressKONULLRegistration() throws MustAbortStateMachineException {
			interaction.registerToWindows(null);
			window.fireEvent(createMousePressEvent(11d, 23d, MouseButton.MIDDLE));
			checkNoUseOfHandler();
		}

		@Test
		void TestPressKOListContainsNULLRegistration() throws MustAbortStateMachineException {
			interaction.registerToWindows(Collections.singletonList(null));
			window.fireEvent(createMousePressEvent(11d, 23d, MouseButton.MIDDLE));
			checkNoUseOfHandler();
		}
	}
}

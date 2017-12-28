package org.malai.javafx.interaction.library;

import java.util.Collections;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustCancelStateMachineException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestDoubleClick extends BaseJfXInteractionTest<DoubleClick> {
	@Override
	protected DoubleClick createInteraction() {
		return new DoubleClick();
	}

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		DoubleClick.setTimeGap(1000L);
	}

	@Test
	void testSetTimeGap() {
		DoubleClick.setTimeGap(200L);
		assertEquals(200L, DoubleClick.getTimeGap());
	}

	@Test
	void TestDoubleClickValid() throws MustCancelStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionCancels();
	}

	@Test
	void TestDoubleClickValidWithTimeGap() throws MustCancelStateMachineException {
		DoubleClick.setTimeGap(2000L);
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		sleep(1100L);
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionCancels();
	}

	@Test
	void TestDoubleClickWithMouseMotion() throws MustCancelStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);

		assertEquals(new Point3D(10d, 20d, 0d), interaction.getSrcLocalPoint());

		interaction.onPressure(createMousePressEvent(1000d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(1000d, 20d, MouseButton.PRIMARY), 0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionCancels();

	}

	@Test
	void TestDoubleClickWithLongPressure() throws MustCancelStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		sleep(1500);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);

		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionCancels();

	}

	@Test
	void TestDoubleClickWithDrag() throws MustCancelStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(1000d, 20d, MouseButton.PRIMARY), 0);

		assertEquals(new Point3D(10d, 20d, 0d), interaction.getSrcLocalPoint());

		interaction.onPressure(createMousePressEvent(1000d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(1000d, 20d, MouseButton.PRIMARY), 0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();
		Mockito.verify(handler, Mockito.never()).interactionCancels();

	}

	@Test
	void TestDoubleClickTimeOut() throws MustCancelStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		sleep(1500);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates();
		Mockito.verify(handler, Mockito.never()).interactionStops();
		Mockito.verify(handler, Mockito.times(1)).interactionCancels();
	}


	@Test
	void TestDoubleClickInvalid() throws MustCancelStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		sleep(1500);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates();
		Mockito.verify(handler, Mockito.times(1)).interactionCancels();
		Mockito.verify(handler, Mockito.never()).interactionStops();

	}

	@Test
	void TestDoubleClickRight() throws MustCancelStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		interaction.onRelease(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		sleep(1500);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts();
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates();
		Mockito.verify(handler, Mockito.never()).interactionCancels();
		Mockito.verify(handler, Mockito.times(1)).interactionStops();

	}

	@Test
	void TestDoubleClickRegisterToNodes() throws MustCancelStateMachineException {
		Canvas canvas;
		canvas = new Canvas();

		interaction.registerToNodes(Collections.singletonList(canvas));
		canvas.fireEvent(createMousePressEvent(1, 2, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).interactionStarts();

	}

	@Test
	void TestDoubleClickNoActionWhenNotRegistered() throws MustCancelStateMachineException {
		Canvas canvas;
		canvas = new Canvas();

		canvas.fireEvent(createMousePressEvent(1, 2, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.never()).interactionStarts();
	}
}

package org.malai.javafx.interaction.library;

import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.interaction.TimeoutTransition;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@ExtendWith(MockitoExtension.class)
public class TestDoubleClick extends BaseJfXInteractionTest<DoubleClick> {

	@Override
	protected DoubleClick createInteraction() {
		return new DoubleClick();
	}


	@Test
	void TestDoubleClickValid() throws MustAbortStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.PRIMARY),0);
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.PRIMARY),0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);

	}

	@Test
	void TestDoubleClickWithMouseMotion() throws MustAbortStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.PRIMARY),0);

		assertEquals(new Point3D(10d, 20d, 0d), interaction.getSrcPoint().get());

		interaction.onPressure(createMousePressEvent(1000d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(1000d,20d,MouseButton.PRIMARY),0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);

	}

	@Test
	void TestDoubleClickWithLongPressure() throws MustAbortStateMachineException {

		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		sleep(1500);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.PRIMARY),0);

		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.PRIMARY),0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);

	}

	@Test
	void TestDoubleClickWithDrag() throws MustAbortStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(1000d,20d,MouseButton.PRIMARY),0);

		assertEquals(new Point3D(10d, 20d, 0d), interaction.getSrcPoint().get());

		interaction.onPressure(createMousePressEvent(1000d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(1000d,20d,MouseButton.PRIMARY),0);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);

	}

	@Test
	void TestDoubleClickTimeOut() throws MustAbortStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.PRIMARY),0);
		sleep(1500);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionAborts(interaction);
	}


	@Test
	void TestDoubleClickInvalid() throws MustAbortStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.PRIMARY),0);
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.SECONDARY),0);
		sleep(1500);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(2)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionAborts(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);

	}

	@Test
	void TestDoubleClickRight() throws MustAbortStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.SECONDARY),0);
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.SECONDARY), 0);
		interaction.onRelease(createMousePressEvent(10d,20d,MouseButton.SECONDARY),0);
		sleep(1500);

		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.times(3)).interactionUpdates(interaction);
		Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);
		Mockito.verify(handler, Mockito.times(1)).interactionStops(interaction);

	}

	@Test
	void TestDoubleClickRegisterToNodes() throws MustAbortStateMachineException {
		Canvas canvas;
		canvas = new Canvas();

		interaction.registerToNodes(Collections.singletonList(canvas));
		canvas.fireEvent(createMousePressEvent(1, 2, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);

	}

	@Test
	void TestDoubleClickNoActionWhenNotRegistered() throws MustAbortStateMachineException {
		Canvas canvas;
		canvas = new Canvas();

		canvas.fireEvent(createMousePressEvent(1, 2, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.never()).interactionStarts(interaction);
	}

}


package org.malai.javafx.interaction.library;

import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.javafx.MockitoExtension;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestDnD extends BaseJfXInteractionTest<DnD> {
	@Override
	protected DnD createInteraction() {
		return new DnD();
	}

	@Test
	public void testPressStarts() throws MustAbortStateMachineException {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		Mockito.verify(handler, Mockito.times(1)).interactionStarts(interaction);
		Mockito.verify(handler, Mockito.never()).interactionStops(interaction);
		Mockito.verify(handler, Mockito.never()).interactionAborts(interaction);
		assertEquals(new Point3D(10d, 20d, 0d), interaction.getSrcLocalPoint());
	}
}

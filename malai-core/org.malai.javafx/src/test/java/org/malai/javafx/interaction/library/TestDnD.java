package org.malai.javafx.interaction.library;

import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.malai.interaction.InitState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDnD extends TestJfXInteraction<DnD> {
	@Override
	protected DnD createInteraction() {
		return new DnD();
	}

	@Test
	public void testNoiseBeforeDnD() {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMouseReleaseEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onMove(createMouseMoveEvent(11d, 21d, MouseButton.PRIMARY), 0);
		interaction.onMove(createMouseMoveEvent(10d, 22d, MouseButton.PRIMARY), 0);
		interaction.onMove(createMouseMoveEvent(10d, 23d, MouseButton.PRIMARY), 0);
		interaction.onPressure(createMousePressEvent(30d, 40d, MouseButton.SECONDARY), 0);
		interaction.onRelease(createMouseReleaseEvent(30d, 40d, MouseButton.SECONDARY), 0);

		assertTrue(interaction.getCurrentState() instanceof InitState);
	}

	@Test
	public void testCorrectButtonNoiseBeforeDnD() {
		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onRelease(createMouseReleaseEvent(10d, 20d, MouseButton.PRIMARY), 0);
		interaction.onMove(createMouseMoveEvent(11d, 21d, MouseButton.PRIMARY), 0);
		interaction.onMove(createMouseMoveEvent(10d, 22d, MouseButton.PRIMARY), 0);
//		interaction.onMove(createMouseMoveEvent(10d, 23d, MouseButton.PRIMARY), 0);
		interaction.onPressure(createMousePressEvent(30d, 40d, MouseButton.SECONDARY), 0);
		interaction.onRelease(createMouseReleaseEvent(30d, 40d, MouseButton.SECONDARY), 0);
		interaction.onMove(createMouseMoveEvent(11d, 21d, MouseButton.PRIMARY), 0);
		interaction.onMove(createMouseMoveEvent(10d, 22d, MouseButton.PRIMARY), 0);

		interaction.onPressure(createMousePressEvent(10d, 20d, MouseButton.PRIMARY), 0);

		assertEquals(MouseButton.PRIMARY, interaction.getButton().orElse(MouseButton.NONE));
	}
}

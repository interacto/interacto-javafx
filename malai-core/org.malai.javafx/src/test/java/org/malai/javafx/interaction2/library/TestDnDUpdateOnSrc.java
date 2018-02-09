package org.malai.javafx.interaction2.library;

import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.Test;
import org.malai.fsm.CancelFSMException;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDnDUpdateOnSrc extends BaseJfXInteractionTest<DnD> {
	@Override
	DnD createInteraction() {
		return new DnD(true, false);
	}

	@Test
	void testPressExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressData() {
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmStarts() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(11, interaction.getEndLocalPt().getX(), 0.0001d);
				assertEquals(23, interaction.getEndLocalPt().getY(), 0.0001d);
				assertEquals(MouseButton.PRIMARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
	}

	@Test
	void testPressMoveExecution() throws CancelFSMException {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.PRIMARY));
		interaction.processEvent(createMouseDragEvent(11, 23, MouseButton.PRIMARY));
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
		Mockito.verify(handler, Mockito.times(2)).fsmUpdates();
		Mockito.verify(handler, Mockito.never()).fsmStops();
		Mockito.verify(handler, Mockito.never()).fsmCancels();
	}

	@Test
	void testPressMoveData() {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.SECONDARY));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(11, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(23, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getEndLocalPt().getX(), 0.0001d);
				assertEquals(24, interaction.getEndLocalPt().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMouseDragEvent(12, 24, MouseButton.SECONDARY));
	}

	@Test
	void testPressMoveMoveData() {
		interaction.processEvent(createMousePressEvent(11, 23, MouseButton.SECONDARY));
		interaction.processEvent(createMouseDragEvent(10, 22, MouseButton.SECONDARY));
		interaction.getFsm().addHandler(new InteractionHandlerStub() {
			@Override
			public void fsmUpdates() {
				assertEquals(10, interaction.getSrcLocalPoint().getX(), 0.0001d);
				assertEquals(22, interaction.getSrcLocalPoint().getY(), 0.0001d);
				assertEquals(12, interaction.getEndLocalPt().getX(), 0.0001d);
				assertEquals(24, interaction.getEndLocalPt().getY(), 0.0001d);
				assertEquals(MouseButton.SECONDARY, interaction.getButton());
			}
		});
		interaction.processEvent(createMouseDragEvent(12, 24, MouseButton.SECONDARY));
	}
}
